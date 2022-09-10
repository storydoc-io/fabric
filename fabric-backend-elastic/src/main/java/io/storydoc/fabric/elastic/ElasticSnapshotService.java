package io.storydoc.fabric.elastic;

import io.storydoc.fabric.connection.app.ConnectionTestRequestDTO;
import io.storydoc.fabric.connection.app.ConnectionTestResponseDTO;
import io.storydoc.fabric.connection.domain.ConnectionHandler;
import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.elastic.metamodel.ElasticMetaModel;
import io.storydoc.fabric.elastic.settings.ElasticSettings;
import io.storydoc.fabric.metamodel.domain.*;
import io.storydoc.fabric.query.app.QueryRequestDTO;
import io.storydoc.fabric.query.app.QueryResponseItemDTO;
import io.storydoc.fabric.query.domain.QueryHandler;
import io.storydoc.fabric.snapshot.app.SnapshotItemDTO;
import io.storydoc.fabric.snapshot.domain.SnapshotHandler_StreamBased;
import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.snapshot.domain.SnapshotSerializer;
import io.storydoc.fabric.snapshot.infra.SnapshotStreamingJacksonWriter;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SettingDescriptorDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SystemTypeDescriptorDTO;
import io.storydoc.fabric.systemdescription.domain.SystemStructureHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ElasticSnapshotService extends StorageBase implements
        ConnectionHandler,
        MetaModelHandler<ElasticMetaModel>,
        SnapshotHandler_StreamBased<SnapshotItemDTO>,
        SystemStructureHandler,
        QueryHandler
{

    public static final String SETTING_KEY__USER_NAME = "userName";
    public static final String SETTING_KEY__PASSWORD = "password";
    public static final String SETTING_KEY__HOST_NAME = "hostName";
    public static final String SETTING_KEY__PORT = "port";
    public static final String SETTING_KEY__SCHEMA_PATTERN = "schemaPattern";

    private final MetaModelStorage metaModelStorage;

    private final ElasticsearchOperations elasticsearchOperations;

    public ElasticSnapshotService(MetaModelStorage metaModelStorage, ElasticsearchOperations elasticsearchOperations) {
        this.metaModelStorage = metaModelStorage;
        this.elasticsearchOperations = elasticsearchOperations;
    }


    // system structure

    @Override
    public String systemType() {
        return "ELASTICSEARCH";
    }

    @Override
    public StructureDTO getStructure(String systemComponentKey) {
        return toDTo(systemComponentKey , getMetaModel(systemComponentKey));
    }

    private StructureDTO toDTo(String systemComponentKey, ElasticMetaModel metaModel) {
        return StructureDTO.builder()
                .id(systemComponentKey)
                .systemType(systemType())
                .structureType("database")
                .children(metaModel.getSchemas().stream()
                        .map(this::toIndexDto)
                        .collect(Collectors.toList())
                )
                .build();
    }

    private StructureDTO toIndexDto(String schema) {
        return StructureDTO.builder()
                .id(schema)
                .systemType(systemType())
                .structureType("index")
                .attributes(Map.of("name", schema))
                .build();
    }


    @Override
    public SystemTypeDescriptorDTO getSystemTypeDescriptor() {
        return SystemTypeDescriptorDTO.builder()
                .systemType(systemType())
                .settingDescriptors(List.of(
                        SettingDescriptorDTO.builder()
                                .key(SETTING_KEY__USER_NAME)
                                .description("User Name")
                                .placeHolder("enter user name")
                                .build(),
                        SettingDescriptorDTO.builder()
                                .key(SETTING_KEY__PASSWORD)
                                .description("Password")
                                .placeHolder("enter password")
                                .build(),
                        SettingDescriptorDTO.builder()
                                .key(SETTING_KEY__HOST_NAME)
                                .description("Host Name")
                                .placeHolder("enter host name")
                                .build(),
                        SettingDescriptorDTO.builder()
                                .key(SETTING_KEY__PORT)
                                .description("Port")
                                .placeHolder("enter port")
                                .build(),
                        SettingDescriptorDTO.builder()
                                .key(SETTING_KEY__SCHEMA_PATTERN)
                                .description("Schema Pattern")
                                .placeHolder("enter schema pattern")
                                .build()

                ))
                .build();
    }


    // metamodel

    @Override
    @SneakyThrows
    public ElasticMetaModel createMetaModel(MetaModelId metaModelId, SystemComponentDTO systemComponent, Map<String, String> settings) {
        ElasticSettings elasticSettings = toSettings(settings);
        Pattern schemaPattern = Pattern.compile(elasticSettings.getSchemaPattern());

        RestHighLevelClient client = getClient(elasticSettings);
        GetMappingsResponse mappings = client.indices().getMapping(new GetMappingsRequest(), RequestOptions.DEFAULT);

        ElasticMetaModel elasticMetaModel = new ElasticMetaModel();
        elasticMetaModel.setSchemas(new ArrayList<>());

        for (Map.Entry<String, MappingMetadata> entry : mappings.mappings().entrySet()) {
            String schemaName = entry.getKey();
            if (!schemaPattern.matcher(schemaName).find()) continue;
            elasticMetaModel.getSchemas().add(schemaName);
        }
        return elasticMetaModel;
    }

    @Override
    public MetaModelSerializer<ElasticMetaModel> getMetaModelSerializer() {
        return ((metaModel, outputStream) -> objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, metaModel));
    }

    public MetaModelDeserializer<ElasticMetaModel> getMetaModelDeSerializer() {
        return ((inputStream) -> objectMapper.readValue(inputStream, ElasticMetaModel.class));
    }

    public ElasticMetaModel getMetaModel(String systemComponentKey) {
        return metaModelStorage.loadMetaModel(systemComponentKey, getMetaModelDeSerializer());
    }

    // snapshot
    //@Override
    public SnapshotItemDTO takeComponentSnapshot(SnapshotId snapshotId, SystemComponentDTO systemComponent, Map<String, String> settings) {
        try {
            ElasticSettings elasticSettings = toSettings(settings);
            RestHighLevelClient client = getClient(elasticSettings);

            ElasticMetaModel metaModel = getMetaModel(systemComponent.getKey());

            SnapshotItemDTO elasticSnapshot = SnapshotItemDTO.builder()
                    .systemType(systemType())
                    .snapshotItemType("snapshot")
                    .children(new ArrayList<>())
                    .build();

            for (String index : metaModel.getSchemas()) {
                SearchResponse response = client.search(
                        new SearchRequest()
                            .indices(index)
                            .source(new SearchSourceBuilder()
                                    .trackTotalHits(true)
                            ),
                        RequestOptions.DEFAULT);
                SnapshotItemDTO indexSnapshot = SnapshotItemDTO.builder()
                        .systemType(systemType())
                        .snapshotItemType("index")
                        .id(index)
                        .children(new ArrayList<>())
                        .attributes(Map.of("hitCount", "" + response.getHits().getTotalHits().value))
                        .build();
                elasticSnapshot.getChildren().add(indexSnapshot);

                int MAX_COUNT = 20;
                int count = 0;
                while (response.getHits().iterator().hasNext() && count < MAX_COUNT) {
                    SearchHit hit = response.getHits().iterator().next();
                    SnapshotItemDTO hitSnapshot = SnapshotItemDTO.builder()
                            .systemType(systemType())
                            .snapshotItemType("hit")
                            .id(hit.getId())
                            .attributes(Map.of("content", "" + hit.getSourceAsString()))
                            .build();
                    indexSnapshot.getChildren().add(hitSnapshot);
                    count++;
                }

            }


            return elasticSnapshot;
        } catch (Throwable e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //@Override
    public SnapshotSerializer<SnapshotItemDTO> getSerializer() {
        return ((snapshot, outputStream) -> objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, snapshot));
    }

    @Override
    @SneakyThrows
    public void streamSnapshot(SnapshotId snapshotId, SystemComponentDTO systemComponent, Map<String, String> settings, OutputStream outputStream) {

        ElasticSettings elasticSettings = toSettings(settings);
        RestHighLevelClient client = getClient(elasticSettings);

        ElasticMetaModel metaModel = getMetaModel(systemComponent.getKey());
        SnapshotStreamingJacksonWriter jacksonWriter = new SnapshotStreamingJacksonWriter(outputStream, objectMapper);

        SnapshotItemDTO elasticSnapshot = SnapshotItemDTO.builder()
                .systemType(systemType())
                .snapshotItemType("snapshot")
                .build();
        jacksonWriter.item(elasticSnapshot);

        jacksonWriter.children();
        for (String index : metaModel.getSchemas()) {
            SearchResponse response = client.search(
                    new SearchRequest()
                            .indices(index)
                            .source(new SearchSourceBuilder()
                                    .trackTotalHits(true)
                            ),
                    RequestOptions.DEFAULT);
            SnapshotItemDTO indexSnapshot = SnapshotItemDTO.builder()
                    .systemType(systemType())
                    .snapshotItemType("index")
                    .id(index)
                    .attributes(Map.of("hitCount", "" + response.getHits().getTotalHits().value))
                    .build();
            jacksonWriter.item(indexSnapshot);

            int MAX_COUNT = 20;
            int count = 0;
            jacksonWriter.children();
            while (response.getHits().iterator().hasNext()) {
                SearchHit hit = response.getHits().iterator().next();
                SnapshotItemDTO hitSnapshot = SnapshotItemDTO.builder()
                        .systemType(systemType())
                        .snapshotItemType("hit")
                        .id(hit.getId())
                        .attributes(Map.of("content", "" + hit.getSourceAsString()))
                        .build();
                jacksonWriter.item(hitSnapshot);
                jacksonWriter.itemEnd();
                count++;
            }
            jacksonWriter.childrenEnd();
            jacksonWriter.itemEnd();
        }
        jacksonWriter.childrenEnd();
        jacksonWriter.itemEnd();
        jacksonWriter.flush();
    }

    // connection


    @Override
    public ConnectionTestResponseDTO testConnection(ConnectionTestRequestDTO connectionTestRequestDTO) {
        try {
            ElasticSettings settings = toSettings(connectionTestRequestDTO.getSettings());
            RestHighLevelClient client = getClient(settings);
            return new ConnectionTestResponseDTO(true, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ConnectionTestResponseDTO(false, e.getMessage());
        }
    }

    private RestHighLevelClient getClient(ElasticSettings settings) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(settings.getUserName(), settings.getPassword()));

        RestClientBuilder.HttpClientConfigCallback httpClientConfigCallback = new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        };

        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(settings.getHostName(), settings.getPort()))
                .setHttpClientConfigCallback(httpClientConfigCallback);

        return new RestHighLevelClient(restClientBuilder);

    }

    private ElasticSettings toSettings(Map<String, String> settings) {
        return ElasticSettings.builder()
                .userName(settings.get(SETTING_KEY__USER_NAME))
                .password(settings.get(SETTING_KEY__PASSWORD))
                .hostName(settings.get(SETTING_KEY__HOST_NAME))
                .port(Integer.parseInt(settings.get(SETTING_KEY__PORT)))
                .schemaPattern(settings.get(SETTING_KEY__SCHEMA_PATTERN))
                .build();
    }

    // Query


    @Override
    @SneakyThrows
    public QueryResponseItemDTO doQuery(QueryRequestDTO requestDTO, Map<String, String> settings) {
        ElasticSettings elasticSettings = toSettings(settings);
        RestHighLevelClient client = getClient(elasticSettings);
        Request request = new Request("GET", requestDTO.getAttributes().get("endpoint"));
        if (requestDTO.getAttributes().get("jsonEntity") != null) {
            request.setJsonEntity(requestDTO.getAttributes().get("jsonEntity"));
        }
        Response response = client.getLowLevelClient().performRequest(request);
        return QueryResponseItemDTO.builder()
                .attributes(Map.of("content", EntityUtils.toString(response.getEntity())))
                .build();
    }
}

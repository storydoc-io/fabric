package io.storydoc.fabric.elastic;

import io.storydoc.fabric.elastic.metamodel.ElasticMetaModel;
import io.storydoc.fabric.elastic.metamodel.ElasticMetaModel2EntityModelMapper;
import io.storydoc.fabric.elastic.metamodel.Schema;
import io.storydoc.fabric.elastic.settings.ElasticSettings;
import io.storydoc.fabric.metamodel.domain.*;
import io.storydoc.fabric.systemdescription.app.entity.EntityDTO;
import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SettingDescriptorDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SystemTypeDescriptorDTO;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;
import io.storydoc.fabric.systemdescription.domain.SystemStructureHandler;
import lombok.SneakyThrows;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class ElasticMetaModelService extends ElasticServiceBase implements MetaModelHandler<ElasticMetaModel>, SystemStructureHandler {

    private final MetaModelStorage metaModelStorage;

    public ElasticMetaModelService(MetaModelStorage metaModelStorage) {
        this.metaModelStorage = metaModelStorage;
    }

    // metamodel

    @Override
    @SneakyThrows
    public ElasticMetaModel createMetaModel(MetaModelId metaModelId, SystemComponentCoordinate coordinate, Map<String, String> settings) {
        ElasticSettings elasticSettings = toSettings(settings);
        Pattern schemaPattern = Pattern.compile(".*" /*elasticSettings.getSchemaPattern()*/);

        RestHighLevelClient client = getClient(elasticSettings);
        GetMappingsResponse mappings = client.indices().getMapping(new GetMappingsRequest(), RequestOptions.DEFAULT);

        ElasticMetaModel elasticMetaModel = new ElasticMetaModel();
        elasticMetaModel.setSchemas(new ArrayList<>());

        for (Map.Entry<String, MappingMetadata> entry : mappings.mappings().entrySet()) {
            String schemaName = entry.getKey();
            if (!schemaPattern.matcher(schemaName).find()) continue;
            MappingMetadata mappingMetadata = entry.getValue();
            Map<String, Object> sourceMap = entry.getValue().sourceAsMap();
            String content = mappingMetadata.get().source().toString();
            elasticMetaModel.getSchemas().add(Schema.builder()
                    .name(schemaName)
                    .source(sourceMap)
                    .build());
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

    public ElasticMetaModel getMetaModel(SystemComponentCoordinate coordinate) {
        return metaModelStorage.loadMetaModel(coordinate, getMetaModelDeSerializer());
    }

    // system structure


    @Override
    public EntityDTO getAsEntity(SystemComponentCoordinate coordinate) {
        return new ElasticMetaModel2EntityModelMapper().toEntityDto(getMetaModel(coordinate));
    }

    @Override
    public StructureDTO getStructure(SystemComponentCoordinate coordinate) {
        return toDTo(coordinate.getSystemComponentKey(), getMetaModel(coordinate));
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

    private StructureDTO toIndexDto(Schema schema) {
        return StructureDTO.builder()
                .id(schema.getName())
                .systemType(systemType())
                .structureType("index")
                .attributes(Map.of("name", schema.getName()))
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
                                .build()
/*
                        ,
                        SettingDescriptorDTO.builder()
                                .key(SETTING_KEY__SCHEMA_PATTERN)
                                .description("Index Pattern")
                                .placeHolder("enter schema pattern")
                                .build()

 */

                ))
                .build();
    }




}

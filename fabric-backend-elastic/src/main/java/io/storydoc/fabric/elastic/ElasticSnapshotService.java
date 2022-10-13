package io.storydoc.fabric.elastic;

import io.storydoc.fabric.command.domain.ProgressMonitor;
import io.storydoc.fabric.elastic.metamodel.ElasticMetaModel;
import io.storydoc.fabric.elastic.settings.ElasticSettings;
import io.storydoc.fabric.snapshot.app.result.SnapshotItemDTO;
import io.storydoc.fabric.snapshot.domain.SnapshotHandler_StreamBased;
import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.snapshot.domain.SnapshotSerializer;
import io.storydoc.fabric.snapshot.infra.SnapshotStreamingJacksonWriter;
import io.storydoc.fabric.snapshot.infra.jsonmodel.Snapshot;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ElasticSnapshotService extends ElasticServiceBase implements SnapshotHandler_StreamBased<SnapshotItemDTO> {

    public static final String SNAPSHOT_ITEM_TYPE__SNAPSHOT = "snapshot";
    public static final String SNAPSHOT_ITEM_TYPE__INDEX = "index";
    public static final String SNAPSHOT_ITEM_TYPE__HIT = "hit";
    public static final String SNAPSHOT_ATTRIBUTE__CONTENT = "content";

    private final ElasticMetaModelService elasticMetaModelService;

    public ElasticSnapshotService(ElasticMetaModelService elasticMetaModelService) {
        this.elasticMetaModelService = elasticMetaModelService;
    }

    // snapshot
    //@Override
    public SnapshotItemDTO takeComponentSnapshot(SnapshotId snapshotId, SystemComponentDTO systemComponent, Map<String, String> settings) {
        try {
            ElasticSettings elasticSettings = toSettings(settings);
            RestHighLevelClient client = getClient(elasticSettings);

            ElasticMetaModel metaModel = elasticMetaModelService.getMetaModel(systemComponent.getKey());

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

        ElasticMetaModel metaModel = elasticMetaModelService.getMetaModel(systemComponent.getKey());
        SnapshotStreamingJacksonWriter jacksonWriter = new SnapshotStreamingJacksonWriter(outputStream, objectMapper);

        SnapshotItemDTO elasticSnapshot = SnapshotItemDTO.builder()
                .systemType(systemType())
                .snapshotItemType(SNAPSHOT_ITEM_TYPE__SNAPSHOT)
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
                    .snapshotItemType(SNAPSHOT_ITEM_TYPE__INDEX)
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
                        .snapshotItemType(SNAPSHOT_ITEM_TYPE__HIT)
                        .id(hit.getId())
                        .attributes(Map.of(SNAPSHOT_ATTRIBUTE__CONTENT, "" + hit.getSourceAsString()))
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

    @Override
    public void upload(Snapshot snapshot, String environmentKey, String componentKey, ProgressMonitor progressMonitor) {

        int recordCount = 20;

        progressMonitor.setPercentDone(0);
        for (int i = 0; i < recordCount; i++) {
            sleep(200);
            progressMonitor.setPercentDone(100 * (i+1) / recordCount);
        }


    }

    private void sleep(int amount) {
        try {
            TimeUnit.MILLISECONDS.sleep(amount);
        } catch (InterruptedException e) {
        }
    }


}

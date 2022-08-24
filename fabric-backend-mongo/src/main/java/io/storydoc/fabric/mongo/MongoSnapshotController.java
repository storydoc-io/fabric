package io.storydoc.fabric.mongo;

import io.storydoc.fabric.mongo.metamodel.MongoMetaModel;
import io.storydoc.fabric.mongo.navigation.MongoNavigationModel;
import io.storydoc.fabric.mongo.snapshot.MongoSnapshot;
import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.snapshot.domain.SnapshotStorage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mongo")
public class MongoSnapshotController {

    private final MongoSnapshotService mongoSnapshotService;

    private final SnapshotStorage snapshotStorage;

    public MongoSnapshotController(MongoSnapshotService mongoSnapshotService, SnapshotStorage snapshotStorage) {
        this.mongoSnapshotService = mongoSnapshotService;
        this.snapshotStorage = snapshotStorage;
    }



    @GetMapping(value = "snapshot", produces = MediaType.APPLICATION_JSON_VALUE)
    public MongoSnapshot getMongoSnapshot(SnapshotId snapshotId, String componentKey) {
        return mongoSnapshotService.getMongoSnapshot(snapshotId, componentKey);
    }

    @GetMapping(value="navigationmodel", produces = MediaType.APPLICATION_JSON_VALUE)
    public MongoNavigationModel getNavigationModel(String systemComponentKey) {
        return mongoSnapshotService.getNavigationModel(systemComponentKey);
    }

    @GetMapping(value="metamodel", produces = MediaType.APPLICATION_JSON_VALUE)
    public MongoMetaModel getMetaModel(String systemComponentKey) {
        return mongoSnapshotService.getMetaModel(systemComponentKey);
    }

}

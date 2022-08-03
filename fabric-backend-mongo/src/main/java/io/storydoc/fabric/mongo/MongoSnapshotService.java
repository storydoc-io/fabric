package io.storydoc.fabric.mongo;

import com.mongodb.client.*;
import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.mongo.navigation.MongoNavigationModel;
import io.storydoc.fabric.mongo.snapshot.CollectionSnapshot;
import io.storydoc.fabric.mongo.snapshot.MongoSnapshot;
import io.storydoc.fabric.navigation.domain.NavigationModelStorage;
import io.storydoc.fabric.snapshot.domain.*;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MongoSnapshotService extends StorageBase implements SnapshotHandler<MongoSnapshot> {

    private final SnapshotStorage snapshotStorage;

    private final NavigationModelStorage navigationModelStorage;

    private final SystemDescriptionService systemDescriptionService;

    public MongoSnapshotService(SnapshotStorage snapshotStorage, NavigationModelStorage navigationModelStorage, SystemDescriptionService systemDescriptionService) {
        this.snapshotStorage = snapshotStorage;
        this.navigationModelStorage = navigationModelStorage;
        this.systemDescriptionService = systemDescriptionService;
    }

    @Override
    public String systemType() {
        return "MONGO";
    }

    @Override
    public MongoSnapshot takeComponentSnapshot(String environmentKey, SystemComponentDTO systemComponent, SnapshotId snapshotId) {

        String systemComponentKey = systemComponent.getKey();
        String connectionUrl = getConnectionUrl(environmentKey, systemComponentKey);
        String dbName = getDBName(environmentKey, systemComponentKey);
        MongoClient mongoClient = MongoClients.create(connectionUrl);
        MongoDatabase database = mongoClient.getDatabase(dbName);


        MongoSnapshot mongoSnapshot = new MongoSnapshot();
        mongoSnapshot.setCollectionSnapshots(new ArrayList<>());

        MongoIterable<String> collectionNames = database.listCollectionNames();
        collectionNames.forEach(collectionName -> {
            CollectionSnapshot collectionSnapshot = new CollectionSnapshot();
            collectionSnapshot.setCollectionName(collectionName);

            collectionSnapshot.setDocuments(new ArrayList<>());
            MongoCollection<Document> collection = database.getCollection(collectionName);
            FindIterable<Document> documents = collection.find();
            documents.forEach(doc -> {
                collectionSnapshot.getDocuments().add(doc.toJson());
            });

            mongoSnapshot.getCollectionSnapshots().add(collectionSnapshot);

        });

        return mongoSnapshot;
    }

    private String getConnectionUrl(String environmentKey, String systemComponentKey) {
        return systemDescriptionService.getSystemDescription().getSettings()
                .get(environmentKey)
                .get(systemComponentKey)
                .get("connectionUrl");
    }

    private String getDBName(String environmentKey, String systemComponentKey) {
        return systemDescriptionService.getSystemDescription().getSettings()
                .get(environmentKey)
                .get(systemComponentKey)
                .get("dbName");
    }


    @Override
    public SnapshotSerializer<MongoSnapshot> getSerializer() {
        return ((mongoSnapshot, outputStream) -> objectMapper.writeValue(outputStream, mongoSnapshot));
    }


    public MongoSnapshot getMongoSnapshot(SnapshotId snapshotId, String componentKey) {
        SnapshotDeserializer<MongoSnapshot> deserializer = (inputStream -> objectMapper.readValue(inputStream, MongoSnapshot.class));
        return snapshotStorage.loadSnapshotComponent(snapshotId, componentKey, deserializer, systemType());

    }

    public MongoNavigationModel getNavigationModel(String systemComponentKey) {
            return navigationModelStorage.loadNavigationModel(systemComponentKey, inputStream-> objectMapper.readValue(inputStream, MongoNavigationModel.class));
    }

}

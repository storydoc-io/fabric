package io.storydoc.fabric.mongo;

import com.mongodb.client.*;
import io.storydoc.fabric.connection.app.ConnectionTestRequestDTO;
import io.storydoc.fabric.connection.app.ConnectionTestResponseDTO;
import io.storydoc.fabric.connection.domain.ConnectionHandler;
import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.metamodel.domain.*;
import io.storydoc.fabric.mongo.metamodel.MongoMetaModel;
import io.storydoc.fabric.mongo.navigation.MongoNavigationModel;
import io.storydoc.fabric.mongo.settings.MongoSettings;
import io.storydoc.fabric.mongo.snapshot.CollectionSnapshot;
import io.storydoc.fabric.mongo.snapshot.MongoSnapshot;
import io.storydoc.fabric.navigation.domain.NavigationModelStorage;
import io.storydoc.fabric.snapshot.domain.*;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
@Slf4j
public class MongoSnapshotService extends StorageBase implements SnapshotHandler<MongoSnapshot>, MetaModelHandler<MongoMetaModel>, ConnectionHandler {

    private final SnapshotStorage snapshotStorage;

    private final NavigationModelStorage navigationModelStorage;

    private final MetaModelStorage metaModelStorage;

    private final SystemDescriptionService systemDescriptionService;

    public MongoSnapshotService(SnapshotStorage snapshotStorage, NavigationModelStorage navigationModelStorage, MetaModelStorage metaModelStorage, SystemDescriptionService systemDescriptionService) {
        this.snapshotStorage = snapshotStorage;
        this.navigationModelStorage = navigationModelStorage;
        this.metaModelStorage = metaModelStorage;
        this.systemDescriptionService = systemDescriptionService;
    }

    @Override
    public String systemType() {
        return "MONGO";
    }

    @Override
    public MongoSnapshot takeComponentSnapshot(String environmentKey, SystemComponentDTO systemComponent, SnapshotId snapshotId) {

        String systemComponentKey = systemComponent.getKey();
        MongoSettings mongoSettings = getSettings(systemDescriptionService.getSystemDescription()
                .getSettings()
                .get(environmentKey)
                .get(systemComponentKey));
        MongoClient mongoClient = MongoClients.create(mongoSettings.getConnectionUrl());
        MongoDatabase database = mongoClient.getDatabase(mongoSettings.getDbName());


        MongoSnapshot mongoSnapshot = new MongoSnapshot();
        mongoSnapshot.setCollectionSnapshots(new ArrayList<>());

        MongoMetaModel metaModel = getMetaModel(systemComponentKey);

        metaModel.getCollections().forEach(collectionName -> {
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

    private MongoSettings getSettings(Map<String, String> settingsMap) {
        return MongoSettings.builder()
                .connectionUrl(settingsMap.get("connectionUrl"))
                .dbName(settingsMap.get("dbName"))
                .build();
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


    // metamodel

    @Override
    public MongoMetaModel createMetaModel(String environmentKey, SystemComponentDTO systemComponent, MetaModelId metaModelId) {
        String systemComponentKey = systemComponent.getKey();
        MongoSettings mongoSettings = getSettings(systemDescriptionService.getSystemDescription()
                .getSettings()
                .get(environmentKey)
                .get(systemComponentKey));
        MongoClient mongoClient = MongoClients.create(mongoSettings.getConnectionUrl());
        MongoDatabase database = mongoClient.getDatabase(mongoSettings.getDbName());

        MongoMetaModel metaModel = new MongoMetaModel();
        metaModel.setCollections(new ArrayList<>());

        MongoIterable<String> collectionNames = database.listCollectionNames();
        collectionNames.forEach(collectionName -> {
            metaModel.getCollections().add(collectionName);
        });
        return metaModel;
    }

    @Override
    public MetaModelSerializer<MongoMetaModel> getMetaModelSerializer() {
        return ((metaModel, outputStream) -> objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, metaModel));
    }

    public MetaModelDeserializer<MongoMetaModel> getMetaModelDeSerializer() {
        return ((inputStream) -> objectMapper.readValue(inputStream, MongoMetaModel.class));
    }


    public MongoMetaModel getMetaModel(String systemComponentKey) {
        return metaModelStorage.loadMetaModel(systemComponentKey, getMetaModelDeSerializer());
    }

    // connection

    @Override
    public ConnectionTestResponseDTO testConnection(ConnectionTestRequestDTO connectionTestRequestDTO) {
        try {
            MongoSettings mongoSettings = getSettings(connectionTestRequestDTO.getSettings());
            MongoClient mongoClient = MongoClients.create(mongoSettings.getConnectionUrl());
            MongoDatabase database = mongoClient.getDatabase(mongoSettings.getDbName());
            MongoIterable<String> collectionNames = database.listCollectionNames();
            collectionNames.forEach(collectionName -> {
                log.trace("found collection: " + collectionName);
            });

            mongoClient.getDatabase(mongoSettings.getDbName());
            return new ConnectionTestResponseDTO(true, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ConnectionTestResponseDTO(false, e.getMessage());
        }
    }

}

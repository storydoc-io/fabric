package io.storydoc.fabric.mongo;

import com.mongodb.client.*;
import io.storydoc.fabric.command.domain.ProgressMonitor;
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
import io.storydoc.fabric.snapshot.infra.jsonmodel.Snapshot;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SettingDescriptorDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SystemTypeDescriptorDTO;
import io.storydoc.fabric.systemdescription.domain.SystemStructureHandler;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MongoSnapshotService extends StorageBase implements SnapshotHandler_ModelBased<MongoSnapshot>, MetaModelHandler<MongoMetaModel>, ConnectionHandler, SystemStructureHandler {

    private final SnapshotStorage snapshotStorage;

    private final NavigationModelStorage navigationModelStorage;

    private final MetaModelStorage metaModelStorage;

    public MongoSnapshotService(SnapshotStorage snapshotStorage, NavigationModelStorage navigationModelStorage, MetaModelStorage metaModelStorage) {
        this.snapshotStorage = snapshotStorage;
        this.navigationModelStorage = navigationModelStorage;
        this.metaModelStorage = metaModelStorage;
    }

    // systemtype

    @Override
    public String systemType() {
        return "MONGO";
    }

    @Override
    public SystemTypeDescriptorDTO getSystemTypeDescriptor() {
        return SystemTypeDescriptorDTO.builder()
                .systemType(systemType())
                .settingDescriptors(List.of(
                        SettingDescriptorDTO.builder()
                                .key("connectionUrl")
                                .description("Connection URL")
                                .placeHolder("enter a connection url to the mongo db")
                                .build(),
                        SettingDescriptorDTO.builder()
                                .key("dbName")
                                .description("Mongo Database")
                                .placeHolder("enter the mongo database")
                                .build()
                ))
                .build();
    }

    @Override
    public MongoSnapshot takeComponentSnapshot(SnapshotId snapshotId, SystemComponentDTO systemComponent, Map<String, String> settings) {

        String systemComponentKey = systemComponent.getKey();
        MongoSettings mongoSettings = toMongoSettings(settings);
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

    private MongoSettings toMongoSettings(Map<String, String> settingsMap) {
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
        return navigationModelStorage.loadNavigationModel(systemComponentKey, inputStream -> objectMapper.readValue(inputStream, MongoNavigationModel.class));
    }


    // metamodel

    @Override
    public MongoMetaModel createMetaModel(MetaModelId metaModelId, SystemComponentDTO systemComponent, Map<String, String> settings) {
        MongoSettings mongoSettings = toMongoSettings(settings);
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
        try {
            return metaModelStorage.loadMetaModel(systemComponentKey, getMetaModelDeSerializer());
        } catch (Exception e) {
            return null;
        }
    }

    // connection

    @Override
    public ConnectionTestResponseDTO testConnection(ConnectionTestRequestDTO connectionTestRequestDTO) {
        try {
            MongoSettings mongoSettings = toMongoSettings(connectionTestRequestDTO.getSettings());
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

    // structure


    @Override
    public StructureDTO getStructure(String systemComponentKey) {
        return toDto(systemComponentKey, getMetaModel(systemComponentKey));
    }

    private StructureDTO toDto(String systemComponentKey, MongoMetaModel metaModel) {
        if (metaModel == null) return null;
        return StructureDTO.builder()
                .id(systemComponentKey)
                .systemType(systemType())
                .structureType("database")
                .children(metaModel.getCollections().stream()
                        .map(this::toCollectionDto)
                        .collect(Collectors.toList())
                )
                .build();
    }

    private StructureDTO toCollectionDto(String collection) {
        return StructureDTO.builder()
                .id(collection)
                .systemType(systemType())
                .structureType("collection")
                .attributes(Map.of("name", collection))
                .build();
    }

    // upload

    @Override
    public void upload(Snapshot snapshot, String environmentKey, String componentKey, ProgressMonitor progressMonitor) {


        int recordCount = 50;

        progressMonitor.setPercentDone(0);
        for (int i = 0; i < recordCount; i++) {
            sleep(300);
            progressMonitor.setPercentDone(100 * (i + 1) / recordCount);
        }


    }

    private void sleep(int amount) {
        try {
            TimeUnit.MILLISECONDS.sleep(amount);
        } catch (InterruptedException e) {
        }
    }

}

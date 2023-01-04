package io.storydoc.fabric.mongo;

import com.mongodb.client.*;
import io.storydoc.fabric.command.domain.ProgressMonitor;
import io.storydoc.fabric.connection.app.ConnectionTestRequestDTO;
import io.storydoc.fabric.connection.app.ConnectionTestResponseDTO;
import io.storydoc.fabric.connection.domain.ConnectionHandler;
import io.storydoc.fabric.metamodel.domain.*;
import io.storydoc.fabric.mongo.metamodel.Collection;
import io.storydoc.fabric.mongo.metamodel.MetaModel2EntityConverter;
import io.storydoc.fabric.mongo.metamodel.MetaModelAssembler;
import io.storydoc.fabric.mongo.metamodel.MongoMetaModel;
import io.storydoc.fabric.mongo.navigation.MongoNavigationModel;
import io.storydoc.fabric.mongo.settings.MongoSettings;
import io.storydoc.fabric.mongo.snapshot.CollectionSnapshot;
import io.storydoc.fabric.mongo.snapshot.MongoSnapshot;
import io.storydoc.fabric.navigation.domain.NavigationModelStorage;
import io.storydoc.fabric.snapshot.domain.*;
import io.storydoc.fabric.snapshot.infra.jsonmodel.Snapshot;
import io.storydoc.fabric.systemdescription.app.entity.EntityDTO;
import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SettingDescriptorDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SystemTypeDescriptorDTO;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;
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
public class MongoSnapshotService extends MongoServiceBase implements SnapshotHandler_ModelBased<MongoSnapshot>, MetaModelHandler<MongoMetaModel>, ConnectionHandler, SystemStructureHandler {

    private final SnapshotStorage snapshotStorage;

    private final NavigationModelStorage navigationModelStorage;

    private final MetaModelStorage metaModelStorage;

    public MongoSnapshotService(SnapshotStorage snapshotStorage, NavigationModelStorage navigationModelStorage, MetaModelStorage metaModelStorage) {
        this.snapshotStorage = snapshotStorage;
        this.navigationModelStorage = navigationModelStorage;
        this.metaModelStorage = metaModelStorage;
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
    public MongoSnapshot takeComponentSnapshot(SnapshotId snapshotId, SystemComponentCoordinate coordinate, Map<String, String> settings) {

        MongoSettings mongoSettings = toMongoSettings(settings);
        MongoClient mongoClient = getMongoClient(mongoSettings);
        MongoDatabase database = mongoClient.getDatabase(mongoSettings.getDbName());


        MongoSnapshot mongoSnapshot = new MongoSnapshot();
        mongoSnapshot.setCollectionSnapshots(new ArrayList<>());

        MongoMetaModel metaModel = getMetaModel(coordinate);

        metaModel.getCollections().forEach(collection -> {
            CollectionSnapshot collectionSnapshot = new CollectionSnapshot();
            collectionSnapshot.setCollectionName(collection.getName());

            collectionSnapshot.setDocuments(new ArrayList<>());
            MongoCollection<Document> mongoCollection = database.getCollection(collection.getName());
            FindIterable<Document> documents = mongoCollection.find();
            documents.forEach(doc -> {
                collectionSnapshot.getDocuments().add(doc.toJson());
            });

            mongoSnapshot.getCollectionSnapshots().add(collectionSnapshot);

        });

        return mongoSnapshot;
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
    public MongoMetaModel createMetaModel(MetaModelId metaModelId, SystemComponentCoordinate coordinate, Map<String, String> settings) {
        MongoSettings mongoSettings = toMongoSettings(settings);
        MongoClient mongoClient = getMongoClient(mongoSettings);
        String dbName = mongoSettings.getDbName();
        MongoDatabase database = mongoClient.getDatabase(dbName);

        return new MetaModelAssembler().getMetaModel(database, dbName);
    }

    @Override
    public MetaModelSerializer<MongoMetaModel> getMetaModelSerializer() {
        return ((metaModel, outputStream) -> objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, metaModel));
    }

    public MetaModelDeserializer<MongoMetaModel> getMetaModelDeSerializer() {
        return ((inputStream) -> objectMapper.readValue(inputStream, MongoMetaModel.class));
    }


    public MongoMetaModel getMetaModel(SystemComponentCoordinate coordinate) {
        try {
            return metaModelStorage.loadMetaModel(coordinate, getMetaModelDeSerializer());
        } catch (Exception e) {
            return null;
        }
    }

    // connection

    @Override
    public ConnectionTestResponseDTO testConnection(ConnectionTestRequestDTO connectionTestRequestDTO) {
        try {
            MongoSettings mongoSettings = toMongoSettings(connectionTestRequestDTO.getSettings());
            MongoClient mongoClient = getMongoClient(mongoSettings);
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
    public StructureDTO getStructure(SystemComponentCoordinate coordinate) {
        return toDto(coordinate.getSystemComponentKey(), getMetaModel(coordinate));
    }

    @Override
    public EntityDTO getAsEntity(SystemComponentCoordinate coordinate) {
        MongoMetaModel metaModel = getMetaModel(coordinate);
        return new MetaModel2EntityConverter().getEntity(metaModel);
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

    private StructureDTO toCollectionDto(Collection collection) {
        return StructureDTO.builder()
                .id(collection.getName())
                .systemType(systemType())
                .structureType("collection")
                .attributes(Map.of("name", collection.getName()))
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

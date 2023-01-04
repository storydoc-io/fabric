package io.storydoc.fabric.jdbc;

import io.storydoc.fabric.connection.app.ConnectionTestRequestDTO;
import io.storydoc.fabric.connection.app.ConnectionTestResponseDTO;
import io.storydoc.fabric.connection.domain.ConnectionHandler;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionDetails;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionManager;
import io.storydoc.fabric.jdbc.metadata.DBMetaData;
import io.storydoc.fabric.jdbc.metadata.DBMetaData2EntityModelMapper;
import io.storydoc.fabric.jdbc.metadata.DBMetaData2StructureMapper;
import io.storydoc.fabric.jdbc.metadata.DBMetaDataBuilder;
import io.storydoc.fabric.metamodel.domain.*;
import io.storydoc.fabric.systemdescription.app.entity.EntityDTO;
import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SettingDescriptorDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SystemTypeDescriptorDTO;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;
import io.storydoc.fabric.systemdescription.domain.SystemStructureHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JDBCMetaDataService extends JDBCServiceBase implements MetaModelHandler<DBMetaData>, SystemStructureHandler, ConnectionHandler {


    private final MetaModelStorage metaModelStorage;

    public JDBCMetaDataService(JDBCConnectionManager jdbcConnectionManager, MetaModelStorage metaModelStorage) {
        super(jdbcConnectionManager);
        this.metaModelStorage = metaModelStorage;
    }

    @Override
    public DBMetaData createMetaModel(MetaModelId metaModelId, SystemComponentCoordinate coordinate, Map<String, String> settings) {
        JDBCConnectionDetails connectionDetails = toSettings(settings);
        DBMetaDataBuilder dbMetaDataBuilder = new DBMetaDataBuilder();
        return dbMetaDataBuilder.getdDBMetaData(connectionDetails.getUserName(), getDataSource(connectionDetails));
    }


    @Override
    public MetaModelSerializer<DBMetaData> getMetaModelSerializer() {
        return ((metaModel, outputStream) -> objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, metaModel));

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
                                .key(SETTING_KEY__JDBC_URL)
                                .description("JDBC Url")
                                .placeHolder("enter jdbc url")
                                .build()

                ))
                .build();
    }

    public MetaModelDeserializer<DBMetaData> getMetaModelDeSerializer() {
        return ((inputStream) -> objectMapper.readValue(inputStream, DBMetaData.class));
    }


    public DBMetaData getDbMetaData(SystemComponentCoordinate coordinate) {
        return metaModelStorage.loadMetaModel(coordinate, getMetaModelDeSerializer());
    }

    @Override
    public StructureDTO getStructure(SystemComponentCoordinate coordinate) {
        return new DBMetaData2StructureMapper().toDto(getDbMetaData(coordinate));
    }

    @Override
    public EntityDTO getAsEntity(SystemComponentCoordinate coordinate) {
        return new DBMetaData2EntityModelMapper().toEntityDto(getDbMetaData(coordinate));
    }

    @Override
    public ConnectionTestResponseDTO testConnection(ConnectionTestRequestDTO connectionTestRequestDTO) {
        try {
            getDataSource(toSettings(connectionTestRequestDTO.getSettings()));
            return new ConnectionTestResponseDTO(true, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ConnectionTestResponseDTO(false, e.getMessage());
        }
    }

}

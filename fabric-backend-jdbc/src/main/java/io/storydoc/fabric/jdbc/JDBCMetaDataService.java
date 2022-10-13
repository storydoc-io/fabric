package io.storydoc.fabric.jdbc;

import io.storydoc.fabric.connection.app.ConnectionTestRequestDTO;
import io.storydoc.fabric.connection.app.ConnectionTestResponseDTO;
import io.storydoc.fabric.connection.domain.ConnectionHandler;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionDetails;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionManager;
import io.storydoc.fabric.jdbc.metadata.DBMetaData;
import io.storydoc.fabric.jdbc.metadata.DBMetaDataBuilder;
import io.storydoc.fabric.metamodel.domain.*;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SettingDescriptorDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SystemTypeDescriptorDTO;
import io.storydoc.fabric.systemdescription.domain.SystemStructureHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JDBCMetaDataService extends JDBCServiceBase implements MetaModelHandler<StructureDTO>, SystemStructureHandler, ConnectionHandler {

    private final MetaModelStorage metaModelStorage;

    public JDBCMetaDataService(JDBCConnectionManager jdbcConnectionManager, MetaModelStorage metaModelStorage) {
        super(jdbcConnectionManager);
        this.metaModelStorage = metaModelStorage;
    }

    @Override
    public StructureDTO createMetaModel(MetaModelId metaModelId, SystemComponentDTO systemComponent, Map<String, String> settings) {
        JDBCConnectionDetails connectionDetails = toSettings(settings);
        DBMetaDataBuilder dbMetaDataBuilder = new DBMetaDataBuilder();
        DBMetaData dbMetaData = dbMetaDataBuilder.getdDBMetaData(connectionDetails.getUserName(), getDataSource(connectionDetails));
        return toDto(dbMetaData);
    }

    private StructureDTO toDto(DBMetaData dbMetaData) {
        return StructureDTO.builder()
            .systemType(systemType())
            .structureType("SCHEMA")
            .id(dbMetaData.getSchemaName())
            .children(dbMetaData.getTables().stream()
                    .map(this::toTableStructureDto)
                    .collect(Collectors.toList()))
            .build();
    }

    private StructureDTO toTableStructureDto(io.storydoc.fabric.jdbc.metadata.TableMetaData table) {
        return StructureDTO.builder()
            .systemType(systemType())
            .structureType("TABLE")
            .id(table.getName())
            .children(table.getColumns().keySet().stream()
                    .map(this::toColumnStructureDto)
                    .collect(Collectors.toList())
            )
            .build();
    }

    private StructureDTO toColumnStructureDto(String columnName) {
        return StructureDTO.builder()
            .systemType(systemType())
            .structureType("COLUMN")
            .id(columnName)
            .build();
    }

    @Override
    public MetaModelSerializer<StructureDTO> getMetaModelSerializer() {
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

    public MetaModelDeserializer<StructureDTO> getMetaModelDeSerializer() {
        return ((inputStream) -> objectMapper.readValue(inputStream, StructureDTO.class));
    }


    @Override
    public StructureDTO getStructure(String systemComponentKey) {
        return metaModelStorage.loadMetaModel(systemComponentKey, getMetaModelDeSerializer());
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

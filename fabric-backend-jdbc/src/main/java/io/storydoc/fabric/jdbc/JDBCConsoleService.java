package io.storydoc.fabric.jdbc;

import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.describe.ConsoleDescriptorItemDTO;
import io.storydoc.fabric.console.app.describe.ConsoleInputType;
import io.storydoc.fabric.console.app.describe.ConsoleOutputType;
import io.storydoc.fabric.console.app.metanav.MetaNavItem;
import io.storydoc.fabric.console.app.query.Column;
import io.storydoc.fabric.console.app.query.ConsoleRequestDTO;
import io.storydoc.fabric.console.app.query.ConsoleResponseItemDTO;
import io.storydoc.fabric.console.app.query.Row;
import io.storydoc.fabric.console.domain.ConsoleHandler;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionDetails;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionManager;
import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JDBCConsoleService extends JDBCServiceBase implements ConsoleHandler {

    public static final String CONSOLE_FIELD_SQL_QUERY = "sql";

    private final JDBCMetaDataService jdbcMetaDataService;

    public JDBCConsoleService(JDBCConnectionManager jdbcConnectionManager, JDBCMetaDataService jdbcMetaDataService) {
        super(jdbcConnectionManager);
        this.jdbcMetaDataService = jdbcMetaDataService;
    }

    @Override
    public ConsoleDescriptorDTO getDescriptor() {
        return ConsoleDescriptorDTO.builder()
                .items(List.of(
                        ConsoleDescriptorItemDTO.builder()
                                .name(CONSOLE_FIELD_SQL_QUERY)
                                .inputType(ConsoleInputType.TEXT)
                                .build()
                ))
                .build();
    }

    @Override
    public ConsoleResponseItemDTO runRequest(ConsoleRequestDTO requestDTO, Map<String, String> settings) {
        try {
            String query = requestDTO.getAttributes().get(CONSOLE_FIELD_SQL_QUERY);
            JDBCConnectionDetails connectionDetails = toSettings(settings);
            DataSource dataSource = getDataSource(connectionDetails);
            Connection connection = dataSource.getConnection();
            try (Statement stmt = connection.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(query);
                return ConsoleResponseItemDTO.builder()
                    .systemType(systemType())
                    .consoleOutputType(ConsoleOutputType.TABULAR)
                    .tabularData(getRows(resultSet))
                    .tabularDataDescription(getColumns(resultSet))
                    .build();
            }
        } catch (Exception e) {
            return ConsoleResponseItemDTO.builder()
                    .systemType(systemType())
                    .consoleOutputType(ConsoleOutputType.STACKTRACE)
                    .content(e.getMessage())
                    .build();
        }
    }

    private List<Row> getRows(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        List<Row> rows = new ArrayList<>();
        while(resultSet.next()) {
            List<String> values = new ArrayList<>();
            for (int colIdx = 1; colIdx <= resultSetMetaData.getColumnCount(); colIdx++) {
                values.add(resultSet.getString(colIdx));
            }
            rows.add(Row.builder()
                .values(values)
                .build());
        }
        return rows;
    }

    @SneakyThrows
    private List<Column> getColumns(ResultSet resultSet) {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        List<Column> columns = new ArrayList<>();

        for(int colIdx = 1; colIdx <= resultSetMetaData.getColumnCount(); colIdx++) {
            columns.add(Column.builder()
                .name(resultSetMetaData.getColumnName(colIdx))
                .build());
        }

        return columns;
    }

    @Override
    public List<MetaNavItem> getMetaNav(String systemComponentKey) {
        StructureDTO structureDTO = jdbcMetaDataService.getStructure(systemComponentKey);
        return structureDTO.getChildren().stream()
                .filter(table -> !table.getId().startsWith("HT_"))
                .map(table -> toMetaNavItem(table))
                .collect(Collectors.toList());
    }

    private MetaNavItem toMetaNavItem(StructureDTO table) {
        return MetaNavItem.builder()
                .id(table.getId())
                .label(table.getId())
                .attributes(Map.of(
                        CONSOLE_FIELD_SQL_QUERY, String.format("select * from %s", table.getId())
                ))
                .build();
    }
}

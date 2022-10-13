package io.storydoc.fabric.jdbc;

import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.describe.ConsoleDescriptorItemDTO;
import io.storydoc.fabric.console.app.describe.ConsoleInputType;
import io.storydoc.fabric.console.app.describe.ConsoleOutputType;
import io.storydoc.fabric.console.app.query.Column;
import io.storydoc.fabric.console.app.query.ConsoleRequestDTO;
import io.storydoc.fabric.console.app.query.ConsoleResponseItemDTO;
import io.storydoc.fabric.console.app.query.Row;
import io.storydoc.fabric.console.domain.ConsoleHandler;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionDetails;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionManager;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class JDBCConsoleService extends JDBCServiceBase implements ConsoleHandler {

    public static final String CONSOLE_FIELD_SQL_QUERY = "sql";

    public JDBCConsoleService(JDBCConnectionManager jdbcConnectionManager) {
        super(jdbcConnectionManager);
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


}

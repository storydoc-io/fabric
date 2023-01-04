package io.storydoc.fabric.jdbc;

import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.describe.ConsoleDescriptorItemDTO;
import io.storydoc.fabric.console.app.describe.ConsoleInputType;
import io.storydoc.fabric.console.app.describe.ConsoleOutputType;
import io.storydoc.fabric.console.app.navigation.NavItem;
import io.storydoc.fabric.console.app.navigation.NavigationRequest;
import io.storydoc.fabric.console.app.query.ConsoleRequestDTO;
import io.storydoc.fabric.console.app.query.ConsoleResponseItemDTO;
import io.storydoc.fabric.console.domain.ConsoleHandler;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionDetails;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionManager;
import io.storydoc.fabric.jdbc.metadata.DBMetaData;
import io.storydoc.fabric.jdbc.request.JDBCResultSet2TabularResponseMapper;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import static io.storydoc.fabric.jdbc.JDBCConstants.CONSOLE_FIELD_SQL_QUERY;

@Component
@Slf4j
public class JDBCConsoleService extends JDBCServiceBase implements ConsoleHandler {

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
                                .placeholder("query")
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
                JDBCResultSet2TabularResponseMapper resultSetMapper = new JDBCResultSet2TabularResponseMapper();
                return ConsoleResponseItemDTO.builder()
                    .systemType(systemType())
                    .consoleOutputType(ConsoleOutputType.TABULAR)
                    .tabular(resultSetMapper.tabularResponse(resultSet))
/*
                    .navItems(getNavigation(NavigationRequest.builder()
                            .systemComponentKey(requestDTO.getSystemComponentKey())
                            .navItem(requestDTO.getNavItem())
                            .build()))

 */
                    .build();
            }
        } catch (Exception e) {
            log.info("error executing query", e);
            return ConsoleResponseItemDTO.builder()
                    .systemType(systemType())
                    .consoleOutputType(ConsoleOutputType.STACKTRACE)
                    .content(e.getMessage())
                    .build();
        }
    }


    @Override
    public List<NavItem> getNavigation(NavigationRequest navigationRequest) {
        DBMetaData dbMetaData = jdbcMetaDataService.getDbMetaData(SystemComponentCoordinate.builder()
                .environmentKey(navigationRequest.getEnvironmentKey())
                .systemComponentKey(navigationRequest.getSystemComponentKey())
                .build());
        return new NavigationCalculator(dbMetaData).getNavigation(navigationRequest);
    }

}

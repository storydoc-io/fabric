package io.storydoc.fabric.jdbc;

import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.describe.ConsoleDescriptorItemDTO;
import io.storydoc.fabric.console.app.describe.ConsoleInputType;
import io.storydoc.fabric.console.app.navigation.NavItem;
import io.storydoc.fabric.console.app.navigation.NavigationRequest;
import io.storydoc.fabric.console.domain.ConsoleHandler;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionDetails;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionManager;
import io.storydoc.fabric.jdbc.metadata.DBMetaData;
import io.storydoc.fabric.jdbc.request.JDBCResultSet2TabularResponseMapper;
import io.storydoc.fabric.query.app.PagingDTO;
import io.storydoc.fabric.query.app.QueryDTO;
import io.storydoc.fabric.query.app.ResultDTO;
import io.storydoc.fabric.query.app.ResultType;
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
    public ResultDTO runRequest(QueryDTO queryDTO, Map<String, String> settings) {
        JDBCConnectionDetails connectionDetails = toSettings(settings);
        DataSource dataSource = getDataSource(connectionDetails);
        try (Connection connection = dataSource.getConnection();) {
            String query = queryDTO.getAttributes().get(CONSOLE_FIELD_SQL_QUERY);

            String pagedQuery = query;
            String countQuery = null;
            boolean isSelectQuery = query.trim().toLowerCase().startsWith("select");

            if (isSelectQuery && queryDTO.getPaging() != null) {
                PagingDTO paging = queryDTO.getPaging();
                pagedQuery = String.format("%s OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", query, (paging.getPageNr() - 1) * paging.getPageSize(), paging.getPageSize());
                countQuery = String.format("select count(*) from (%s)", query);
                log.info("paged query: " + pagedQuery);
                log.info("count query: " + countQuery);
            }

            try (Statement stmt = connection.createStatement()) {
                long count = 0;

                if (countQuery != null) {
                    ResultSet countResult = stmt.executeQuery(countQuery);
                    countResult.next();
                    count = countResult.getLong(1);
                }

                ResultSet resultSet = stmt.executeQuery(pagedQuery);
                JDBCResultSet2TabularResponseMapper resultSetMapper = new JDBCResultSet2TabularResponseMapper();
                ResultDTO resultDTO = ResultDTO.builder()
                        .systemType(systemType())
                        .resultType(ResultType.TABULAR)
                        .tabular(resultSetMapper.tabularResponse(resultSet))
                        .build();

                if (queryDTO.getPaging() != null && isSelectQuery) {
                    PagingDTO paging = queryDTO.getPaging();
                    resultDTO.getTabular().setPagingInfo(
                            PagingDTO.builder()
                                    .pageNr(paging.getPageNr())
                                    .pageSize(paging.getPageSize())
                                    .nrOfResults(count)
                                    .build()
                    );
                }

                return resultDTO;

            }
        } catch (Exception e) {
            log.info("error executing query", e);
            return ResultDTO.builder()
                    .systemType(systemType())
                    .resultType(ResultType.STACKTRACE)
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

package io.storydoc.fabric.jdbc;

import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionDetails;
import io.storydoc.fabric.jdbc.connection.JDBCConnectionManager;

import javax.sql.DataSource;
import java.util.Map;

public class JDBCServiceBase extends StorageBase {

    public static final String SETTING_KEY__USER_NAME = "userName";
    public static final String SETTING_KEY__PASSWORD = "password";
    public static final String SETTING_KEY__JDBC_URL = "jdbcUrl";


    private final JDBCConnectionManager jdbcConnectionManager;


    public JDBCServiceBase(JDBCConnectionManager jdbcConnectionManager) {
        this.jdbcConnectionManager = jdbcConnectionManager;
    }

    public static final String JDBC_SYSTEM_TYPE = "JDBC";

    public String systemType() {
        return JDBC_SYSTEM_TYPE;
    }

    protected JDBCConnectionDetails toSettings(Map<String, String> settings) {
        return JDBCConnectionDetails.builder()
                .userName(settings.get(SETTING_KEY__USER_NAME))
                .password(settings.get(SETTING_KEY__PASSWORD))
                .jdbcUrl(settings.get(SETTING_KEY__JDBC_URL))
                .build();
    }

    protected DataSource getDataSource(JDBCConnectionDetails connectionDetails) {
        return jdbcConnectionManager.getDataSource(connectionDetails);
    }

}

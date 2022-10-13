package io.storydoc.fabric.jdbc.metadata;

import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBMetaDataBuilder {

	public DBMetaData getdDBMetaData(String schema, DataSource dataSource) {
		DatabaseMetaData jdbcMetaData = getJdbcMetaData(dataSource);
		return todDBMetaData(schema, jdbcMetaData);
	}

	private DatabaseMetaData getJdbcMetaData(DataSource dataSource) {
		try {
			return dataSource.getConnection().getMetaData();
		} catch (SQLException e) {
			throw new RuntimeException("could not get db metadata", e);
		}
	}

	@SneakyThrows
	private DBMetaData todDBMetaData(String schema, DatabaseMetaData jdbcMetaData) {
		DBMetaData dbMetaData = new DBMetaData();

		List<String> tableNames = new ArrayList<>();
		ResultSet rs = jdbcMetaData.getTables(null, schema, "%", null);
		while (rs.next()) {
			tableNames.add(rs.getString(3));
		}
		for (String tableName : tableNames) {
			dbMetaData.add(buildtTable(jdbcMetaData, tableName));
		}
		return dbMetaData;
	}

	private TableMetaData buildtTable(DatabaseMetaData jdbcMetaData, String tableName) {
		try {
			TableMetaData tableMetaData = new TableMetaData(tableName);
			buildColumns(jdbcMetaData, tableName, tableMetaData);
			tableMetaData.setPrimaryKey(buildPK(jdbcMetaData, tableName, tableMetaData));
			return tableMetaData;
		} catch (SQLException e) {
			throw new RuntimeException("could not get table metadata" + tableName, e);
		}
	}

	private void buildColumns(DatabaseMetaData jdbcMetaData, String tableName, TableMetaData tableMetaData) throws SQLException {
		ResultSet columns = jdbcMetaData.getColumns(null, null, tableName, null);
		while (columns.next()) {
			String columnName = columns.getString("COLUMN_NAME");
			int ordinalPosition = columns.getInt("ORDINAL_POSITION");
			String datatype = columns.getString("DATA_TYPE");
			tableMetaData.add(new ColumnMetaData(columnName, datatype, ordinalPosition));
		}
	}

	private PKMetaData buildPK(DatabaseMetaData jdbcMetaData, String tableName, TableMetaData tableMetaData) throws SQLException {
		ResultSet columns = jdbcMetaData.getPrimaryKeys(null, null, tableName);
		PKMetaData pk = new PKMetaData();
		while (columns.next()) {
			String columnName = columns.getString("COLUMN_NAME");
			pk.addColumn(tableMetaData.getColumn(columnName));
		}
		return pk;
	}

}

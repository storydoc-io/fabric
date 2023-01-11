package io.storydoc.fabric.jdbc.metadata;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
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


		log.info("schema's");
		List<String> schemas = new ArrayList<>();
		ResultSet rs = jdbcMetaData.getSchemas();
		while (rs.next()) {
			log.info(String.format("%s | %s", rs.getString(1), rs.getString(2)));
			schemas.add(rs.getString(2));
		}

		log.info("catalogs");
		List<String> catalogs = new ArrayList<>();
		rs = jdbcMetaData.getCatalogs();
		while (rs.next()) {
			log.info(String.format("%s ", rs.getString(1)));
			catalogs.add(rs.getString(1));
		}

		boolean useCatalogs = catalogs.contains(schema);

		rs = useCatalogs ? jdbcMetaData.getTables(schema, null, "%", null) : jdbcMetaData.getTables(null, schema, "%", null);
		List<String> tableNames = getTableNames(rs);
		List<TableMetaData> tableMetaDataList = tableNames.stream()
				.map(tableName -> buildtTable(jdbcMetaData, tableName))
				.collect(Collectors.toList());

		return DBMetaData.builder()
				.schemaName(schema)
				.tables(tableMetaDataList)
				.build();
	}

	private List<String> getTableNames(ResultSet rs) throws SQLException {
		List<String> tableNames = new ArrayList<>();
		while (rs.next()) {
			//log.info(String.format("%s | %s | %s | %s ", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			tableNames.add(rs.getString(3));
		}
		return tableNames;
	}

	private TableMetaData buildtTable(DatabaseMetaData jdbcMetaData, String tableName) {
		try {
			TableMetaData tableMetaData = TableMetaData.builder()
					.name(tableName)
					.columns(buildColumns(jdbcMetaData, tableName))
					.build();
			tableMetaData.setPrimaryKey(buildPK(jdbcMetaData, tableName, tableMetaData));
			tableMetaData.setForeignKeys(buildFKs(jdbcMetaData, tableName));
			return tableMetaData;
		} catch (SQLException e) {
			throw new RuntimeException("could not get table metadata" + tableName, e);
		}
	}

	private Map<String, ColumnMetaData> buildColumns(DatabaseMetaData jdbcMetaData, String tableName) throws SQLException {
		ResultSet jdbcColumns = jdbcMetaData.getColumns(null, null, tableName, null);
		Map<String, ColumnMetaData> columns = new HashMap<>();
		while (jdbcColumns.next()) {
			String columnName = jdbcColumns.getString("COLUMN_NAME");
			int ordinalPosition = jdbcColumns.getInt("ORDINAL_POSITION");
			String datatype = jdbcColumns.getString("DATA_TYPE");
			columns.put(columnName, ColumnMetaData.builder()
					.name(columnName)
					.ordinalPosition(ordinalPosition)
					.type(datatype)
					.build());
		}
		return columns;
	}

	private List<FKMetaData> buildFKs(DatabaseMetaData jdbcMetaData, String tableName) throws SQLException {
		ResultSet rs = jdbcMetaData.getImportedKeys(null, null, tableName);
		Map<String, FKMetaData> fkByName = new HashMap<>();
		while (rs.next()) {
			String fkName = rs.getString("FK_NAME");
			String pkName = rs.getString("PK_NAME");
			String pkTableName = rs.getString("PKTABLE_NAME");
			FKMetaData fkMetaData = fkByName.computeIfAbsent(fkName, s -> FKMetaData.builder()
				.fkName(fkName)
				.pkName(pkName)
				.pkTableName(pkTableName)
				.columns(new ArrayList<>())
				.build()
			);
			String fkColumnName  = rs.getString("FKCOLUMN_NAME");
			String pkColumnName = rs.getString("PKCOLUMN_NAME");
			fkMetaData.getColumns().add(FKColumnMetaData.builder()
				.fkColumnName(fkColumnName)
				.pkColumnName(pkColumnName)
				.build());
		}
		return new ArrayList<>(fkByName.values());
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

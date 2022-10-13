package io.storydoc.fabric.jdbc.metadata;

import java.util.ArrayList;
import java.util.List;

public class DBMetaData  {

	private String schemaName;

	private List<TableMetaData> tables = new ArrayList<>();

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public void add(TableMetaData tableMetaData) {
		tables.add(tableMetaData);
	}

	public TableMetaData get(String tableName) {
		return tables.stream()
				.filter(tableMetaData -> tableName.equals(tableMetaData.getName()))
				.findFirst()
				.orElse(null);
	}


	public List<TableMetaData> getTables() {
		return tables;
	}

}

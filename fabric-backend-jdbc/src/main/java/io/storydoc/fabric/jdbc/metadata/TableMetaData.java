package io.storydoc.fabric.jdbc.metadata;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({ "name", "columns", "primaryKey" })
public class TableMetaData  {

	private Map<String, ColumnMetaData> columns;

	private String name;

	private PKMetaData primaryKey;

	private List<FKMetaData> foreignKeys;

	public void add(ColumnMetaData columnMetaData) {
		columns.put(columnMetaData.getName(), columnMetaData);
	}

	public ColumnMetaData getColumn(String columnName) {
		return columns.get(columnName);
	}

}

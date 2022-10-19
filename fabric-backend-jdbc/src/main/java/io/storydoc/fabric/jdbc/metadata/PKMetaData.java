package io.storydoc.fabric.jdbc.metadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PKMetaData {

	private List<ColumnMetaData> columns = new ArrayList<>();

	public void addColumn(ColumnMetaData column) {
		columns.add(column);
	}

	public List<ColumnMetaData> getColumns() {
		return columns;
	}
}

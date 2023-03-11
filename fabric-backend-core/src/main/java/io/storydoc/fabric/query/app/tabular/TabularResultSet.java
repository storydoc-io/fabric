package io.storydoc.fabric.query.app.tabular;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TabularResultSet {
    private String tableName;
    private List<Row> rows;
    private List<Column> columns;
    private List<Column> pkColumns;
}

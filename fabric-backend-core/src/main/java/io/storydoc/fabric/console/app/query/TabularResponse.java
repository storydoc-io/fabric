package io.storydoc.fabric.console.app.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TabularResponse {
    private String tableName;
    private List<Row> rows;
    private List<Column> columns;
    private List<Column> pkColumns;
}

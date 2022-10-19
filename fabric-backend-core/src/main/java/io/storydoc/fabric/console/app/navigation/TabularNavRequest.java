package io.storydoc.fabric.console.app.navigation;

import io.storydoc.fabric.console.app.query.Row;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TabularNavRequest {
    private String tableName;
    private Row row;
}

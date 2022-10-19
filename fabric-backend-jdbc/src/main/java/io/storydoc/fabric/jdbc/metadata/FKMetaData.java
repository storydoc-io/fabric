package io.storydoc.fabric.jdbc.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FKMetaData {

    private String pkTableName;

    private String fkName;

    private String pkName;

    private List<FKColumnMetaData> columns;

}

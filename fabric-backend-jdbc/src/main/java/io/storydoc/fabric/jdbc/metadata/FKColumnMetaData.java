package io.storydoc.fabric.jdbc.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FKColumnMetaData {

    private String pkColumnName;

    private String fkColumnName;

}

package io.storydoc.fabric.console.app.query;

import io.storydoc.fabric.console.app.describe.ConsoleOutputType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsoleResponseItemDTO {
    private String systemType;
    private ConsoleOutputType consoleOutputType;
    private ConsoleResponseDescriptionDTO description;
    private String content;
    private List<Row> tabularData;
    private List<Column> tabularDataDescription;
}

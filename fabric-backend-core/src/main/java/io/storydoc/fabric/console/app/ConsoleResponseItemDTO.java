package io.storydoc.fabric.console.app;

import io.storydoc.fabric.console.app.describe.ConsoleOutputType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsoleResponseItemDTO {
    private String systemType;
    private ConsoleOutputType consoleOutputType;
    private String content;
}

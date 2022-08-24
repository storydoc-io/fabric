package io.storydoc.fabric.connection.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionTestRequestDTO {
    private String systemType;
    private Map<String, String> settings;
}

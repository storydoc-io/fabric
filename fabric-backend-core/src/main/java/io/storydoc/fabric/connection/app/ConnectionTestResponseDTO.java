package io.storydoc.fabric.connection.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionTestResponseDTO {
    private boolean result;
    private String message;
}

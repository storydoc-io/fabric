package io.storydoc.fabric.systemdescription.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentDTO {
    private String key;
    private String label;
}

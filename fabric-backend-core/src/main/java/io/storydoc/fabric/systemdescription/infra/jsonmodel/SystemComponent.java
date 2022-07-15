package io.storydoc.fabric.systemdescription.infra.jsonmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemComponent {
    private String key;
    private String label;
    private String systemType;
}

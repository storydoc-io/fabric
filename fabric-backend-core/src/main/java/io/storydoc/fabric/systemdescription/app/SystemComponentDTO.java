package io.storydoc.fabric.systemdescription.app;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemComponentDTO {
    private String key;
    private String label;
    private String systemType;
}

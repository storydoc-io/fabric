package io.storydoc.fabric.systemdescription.app;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemConfigSettingDTO {
    private String key;
    private String value;
}

package io.storydoc.fabric.systemdescription.infra.jsonmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfigSetting {
    private String key;
    private String value;
}

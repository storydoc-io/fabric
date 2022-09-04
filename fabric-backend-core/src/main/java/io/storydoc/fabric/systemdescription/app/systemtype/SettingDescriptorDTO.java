package io.storydoc.fabric.systemdescription.app.systemtype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingDescriptorDTO {
    private String key;
    private String description;
    private String placeHolder;
}

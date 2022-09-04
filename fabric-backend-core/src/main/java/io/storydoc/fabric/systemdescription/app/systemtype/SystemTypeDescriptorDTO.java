package io.storydoc.fabric.systemdescription.app.systemtype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemTypeDescriptorDTO {
    private String systemType;
    private List<SettingDescriptorDTO> settingDescriptors;
}

package io.storydoc.fabric.systemdescription.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemComponentCoordinate {
    private String environmentKey;
    private String systemComponentKey;
}

package io.storydoc.fabric.snapshot.app.descriptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SnapshotDescriptorItemDTO {

    private String systemType;
    private String itemType;
    private String id;
    private Map<String, String> attributes;
    private List<SnapshotDescriptorItemDTO> children;

}

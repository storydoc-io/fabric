package io.storydoc.fabric.snapshot.app.descriptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SnapshotDescriptorDTO {

    private String name;

    private String environmentKey;

    private List<SnapshotDescriptorItemDTO> componentDescriptors;

}

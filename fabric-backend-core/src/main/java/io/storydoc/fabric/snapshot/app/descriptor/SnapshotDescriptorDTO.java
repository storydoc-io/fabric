package io.storydoc.fabric.snapshot.app.descriptor;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SnapshotDescriptorDTO {

    private String environmentKey;

    private List<SystemComponentSnapshotDescriptorDTO> systemComponentSnapshotDescriptors;


}

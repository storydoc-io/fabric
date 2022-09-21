package io.storydoc.fabric.snapshot.app.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SnapshotComponentDTO {
    private String componentKey;
    private String systemType;
}

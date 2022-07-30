package io.storydoc.fabric.snapshot.infra.jsonmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SnapshotComponentSummary {
    private String systemType;
    private String componentKey;

}

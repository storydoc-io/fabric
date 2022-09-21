package io.storydoc.fabric.snapshot.app.result;

import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
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
public class SnapshotItemDTO extends SnapshotComponent {

    private String systemType;
    private String snapshotItemType;
    private String id;
    private Map<String, String> attributes;
    private List<SnapshotItemDTO> children;

}

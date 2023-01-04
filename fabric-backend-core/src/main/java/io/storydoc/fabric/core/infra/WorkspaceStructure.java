package io.storydoc.fabric.core.infra;

import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;
import io.storydoc.fabric.workspace.domain.FolderURN;
import io.storydoc.fabric.workspace.domain.ResourceUrn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkspaceStructure {

    public FolderURN getSnapshotFolder(SnapshotId snapshotId) {
        return new FolderURN(List.of(snapshotId.getId()));
    }

    public ResourceUrn getSystemDescriptionUrn() {
        return ResourceUrn.of("systemdescription.json");
    }

    public ResourceUrn getSummariesUrn() {
        return ResourceUrn.of("summaries.json");
    }

    public ResourceUrn getComponentSnapshotUrn(SnapshotId snapshotId, SystemComponentDTO systemComponent) {
        String componentKey = systemComponent.getKey();
        String systemType = systemComponent.getSystemType();
        return getComponentSnapshotUrn(snapshotId, componentKey, systemType);
    }

    public ResourceUrn getComponentSnapshotUrn(SnapshotId snapshotId, String componentKey, String systemType) {
        String fileName = String.format("%s-%s.json", componentKey, systemType);
        return getSnapshotFolder(snapshotId).resolve(ResourceUrn.of(fileName));
    }

    public ResourceUrn getSnapshotUrn(SnapshotId snapshotId) {
        String filename = snapshotId.getId() + ".json";
        return getSnapshotFolder(snapshotId).resolve(ResourceUrn.of(filename));
    }

    public ResourceUrn getNavigationModelUrn(String systemCompenentKey) {
        String filename = "navigationmodel-" + systemCompenentKey + ".json";
        return ResourceUrn.of(filename);
    }

    public ResourceUrn getMetaModelUrn(SystemComponentCoordinate coordinate) {
        String filename = String.format("metamodel-%s-%s.json", coordinate.getEnvironmentKey(), coordinate.getSystemComponentKey());
        return ResourceUrn.of(filename);
    }

    public ResourceUrn getSnippetsUrn(String systemType) {
        String filename = String.format("snippets-%s.json", systemType);
        return ResourceUrn.of(filename);
    }

}

package io.storydoc.fabric.snapshot.domain;

import java.util.Objects;

public class SnapshotDescriptorId {

    public static final String CATEGORY = "snapshot";

    private String id;

    public SnapshotDescriptorId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnapshotDescriptorId itemId = (SnapshotDescriptorId) o;
        return Objects.equals(id, itemId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static SnapshotDescriptorId fromString(String id) {
        return new SnapshotDescriptorId(id);
    }
}

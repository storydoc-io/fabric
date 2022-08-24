package io.storydoc.fabric.metamodel.domain;

import java.util.Objects;

public class MetaModelId {

    public static final String CATEGORY = "metamodel";

    private String id;

    private MetaModelId() {}

    public MetaModelId(String id) {
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
        MetaModelId itemId = (MetaModelId) o;
        return Objects.equals(id, itemId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static MetaModelId fromString(String id) {
        return new MetaModelId(id);
    }
}

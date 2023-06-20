package io.storydoc.fabric.query.domain;

import java.util.Objects;

public class QueryId {

    public static final String CATEGORY = "snapshot";

    private String id;

    private QueryId() {}

    public QueryId(String id) {
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
        QueryId itemId = (QueryId) o;
        return Objects.equals(id, itemId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static QueryId fromString(String id) {
        return new QueryId(id);
    }
}

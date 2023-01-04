package io.storydoc.fabric.testinfra;

import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;

public class DummySnapshot extends SnapshotComponent {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

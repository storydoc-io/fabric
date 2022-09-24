package io.storydoc.fabric.snapshot.domain.upload;

import io.storydoc.fabric.command.domain.CommandType;

public enum UploadCommandTypes implements CommandType {

    SNAPSHOT,
    SNAPSHOT_COMPONENT

}

package io.storydoc.fabric.workspace.app.dto;

import io.storydoc.fabric.workspace.domain.FolderURN;
import lombok.Data;

@Data
public class FolderDTO {

    private String name;

    private FolderURN urn;

}

package io.storydoc.fabric.workspace.app;

import io.storydoc.fabric.workspace.app.dto.FolderDTO;
import io.storydoc.fabric.workspace.app.dto.ResourceDTO;
import io.storydoc.fabric.workspace.domain.FolderURN;
import io.storydoc.fabric.workspace.domain.ResourceUrn;
import io.storydoc.fabric.workspace.domain.WorkspaceException;

import java.io.InputStream;
import java.util.List;

public interface WorkspaceQueryService {
    FolderDTO getRootFolder();
    List<FolderDTO> listSubFolders(FolderURN folderURN);
    List<ResourceDTO> listResources(FolderURN folderURN);
    InputStream getInputStream(ResourceUrn urn) throws WorkspaceException;
}

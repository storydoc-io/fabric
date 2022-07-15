package io.storydoc.fabric.workspace.domain;

import io.storydoc.fabric.workspace.app.dto.FolderDTO;

import java.io.IOException;
import java.util.List;

public interface FolderStorage {
    void createFolder(FolderURN folderURN);
    FolderURN addFolder(FolderURN folderURN, String folderName);
    List<FolderDTO> listSubFolders(FolderURN folderURN);
    void deleteFolder(FolderURN folderURN, boolean recursive) throws IOException;
}

package io.storydoc.fabric.workspace.domain;

import io.storydoc.fabric.workspace.app.dto.ResourceDTO;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface ResourceStorage {

    void saveResource(ResourceUrn resourceUrn, WorkspaceResourceSerializer serializer) throws WorkspaceException;

    <R extends WorkspaceResource> R loadResource(ResourceUrn resourceUrn, WorkspaceResourceDeserializer<R> deserializer) throws WorkspaceException;

    List<ResourceDTO> listResources(FolderURN folderURN);

    InputStream getInputStream(ResourceUrn urn) throws WorkspaceException;

    void delete(ResourceUrn resourceUrn) throws WorkspaceException;

    OutputStream getOutputStream(ResourceUrn resourceUrn) throws WorkspaceException;

    boolean folderExists(FolderURN folderURN);
}

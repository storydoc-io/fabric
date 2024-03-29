package io.storydoc.fabric.workspace.infra;

import io.storydoc.fabric.workspace.app.WorkspaceSettings;
import io.storydoc.fabric.workspace.app.dto.ResourceDTO;
import io.storydoc.fabric.workspace.domain.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResourceStorageImpl implements ResourceStorage {

    private final WorkspaceSettings folderSettings;

    public ResourceStorageImpl(WorkspaceSettings folderSettings) {
        this.folderSettings = folderSettings;
    }

    @Override
    public void delete(ResourceUrn resourceUrn) throws WorkspaceException {
        try {
            getFile(resourceUrn).delete();
        } catch(Exception e) {
            throw new WorkspaceException("could not delete resource " + resourceUrn, e);
        }
    }

    @Override
    public InputStream getInputStream(ResourceUrn urn) throws WorkspaceException {
        try {
            return new FileInputStream(getFile(urn));
        } catch (FileNotFoundException e) {
            throw new WorkspaceException("could not open inputstream ", e);
        }
    }

    public OutputStream getOutputStream(ResourceUrn resourceUrn) throws WorkspaceException {
        try {
            return new FileOutputStream(getFile(resourceUrn));
        } catch (FileNotFoundException e) {
            throw new WorkspaceException("could not open outputstream", e);
        }
    }

    private Path resolveFolderPath(FolderURN folderURN) {
        Path path = folderSettings.getWorkspaceFolder();
        for (String subDirName: folderURN.getPath()) {
            path= path.resolve(subDirName);
        }
        return path;
    }

    private File getFile(ResourceUrn urn) {
        Path path = resolveFolderPath(urn.getFolder()).resolve(urn.getFileName());
        return path.toFile();
    }

    @Override
    public List<ResourceDTO> listResources(FolderURN folderURN) {
        Path parentFolder = resolveFolderPath(folderURN);
        File file = parentFolder.toFile();

        FilenameFilter onlyFiles = new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isFile();
            }
        };
        String[] fileNames = file.list(onlyFiles);
        if (fileNames==null) {
            return new ArrayList<>();
        }
        return Arrays.stream(fileNames)
                .sorted()
                .map(name -> {
                    ResourceDTO dto = ResourceDTO.builder()
                        .name(name)
                        .urn(new ResourceUrn(folderURN, name))
                        .build();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveResource(ResourceUrn resourceUrn, WorkspaceResourceSerializer serializer)  throws WorkspaceException {
        try (OutputStream outputStream = getOutputStream(resourceUrn)) {
            serializer.write(outputStream);
        } catch(IOException ioe) {
            throw new WorkspaceException("could not store resource " + resourceUrn, ioe);
        }

    }

    @Override
    public <R extends WorkspaceResource> R loadResource(ResourceUrn resourceUrn, WorkspaceResourceDeserializer<R> deserializer) throws WorkspaceException{
        try (InputStream inputStream = getInputStream(resourceUrn)) {
            return deserializer.read(inputStream);
        } catch(IOException ioe) {
            throw new WorkspaceException("could not load resource " + resourceUrn, ioe);
        }
    }

    @Override
    public boolean folderExists(FolderURN folderURN){
        return resolveFolderPath(folderURN).toFile().exists();
    }
}

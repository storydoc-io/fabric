package io.storydoc.fabric.workspace.app.dto;

import io.storydoc.fabric.workspace.domain.ResourceUrn;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceDTO {
    private String name;
    private ResourceUrn urn;
}

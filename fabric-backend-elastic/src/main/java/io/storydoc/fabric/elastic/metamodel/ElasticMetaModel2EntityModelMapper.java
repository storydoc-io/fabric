package io.storydoc.fabric.elastic.metamodel;

import io.storydoc.fabric.systemdescription.app.entity.AttributeDTO;
import io.storydoc.fabric.systemdescription.app.entity.EntityDTO;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ElasticMetaModel2EntityModelMapper {

    public static final String ENTITY_TYPE__METAMODEL = "METAMODEL";
    public static final String ENTITY_TYPE__SCHEMA = "SCHEMA";

    public EntityDTO toEntityDto(ElasticMetaModel metaModel) {
        if (metaModel == null) return null;
        return EntityDTO.builder()
                .name("Elastic Model")
                .entityType(ENTITY_TYPE__METAMODEL)
                .attributes(List.of(
                        AttributeDTO.builder()
                                .name("Schemas")
                                .entries(toEntityDtoList(metaModel.getSchemas()))
                                .build()
                ))
                .build();

    }

    private List<EntityDTO> toEntityDtoList(List<Schema> schemas) {
        return schemas.stream()
                .map(this::toSchemaEntity)
                .collect(Collectors.toList());
    }

    private EntityDTO toSchemaEntity(Schema schema) {
        return EntityDTO.builder()
                .entityType(ENTITY_TYPE__SCHEMA)
                .name(schema.getName())
                .attributes(List.of(AttributeDTO.builder()
                        .name("Properties")
                        .entries(properties(schema))
                        .build()))
                .build();
    }

    private List<EntityDTO> properties(Schema schema) {
        Map<String, Object> propertiesMap = (Map<String, Object>) schema.getSource().get("properties");
        return propertiesMap.entrySet().stream()
                .map(entry -> property(entry.getKey(), (Map<String, Object>) entry.getValue()))
                .sorted(Comparator.comparing(EntityDTO::getName))
                .collect(Collectors.toList());
    }

    private EntityDTO property(String name, Map<String, Object> attributes) {
        return EntityDTO.builder()
                .name(name)
                .attributes(List.of(AttributeDTO.builder()
                        .name("Attributes")
                        .entries(attributes(attributes))
                        .build()))
                .build();

    }

    private List<EntityDTO> attributes(Map<String, Object> attributes) {
        return attributes.entrySet().stream()
                .map(entry -> EntityDTO.builder()
                        .name(String.format("%s: %s", entry.getKey(), entry.getValue()))
                        .build())
                .sorted(Comparator.comparing(EntityDTO::getName))
                .collect(Collectors.toList());
    }

}

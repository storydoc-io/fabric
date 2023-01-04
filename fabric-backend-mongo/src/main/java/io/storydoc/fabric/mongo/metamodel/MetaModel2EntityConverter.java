package io.storydoc.fabric.mongo.metamodel;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.storydoc.fabric.systemdescription.app.entity.AttributeDTO;
import io.storydoc.fabric.systemdescription.app.entity.EntityDTO;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MetaModel2EntityConverter {

    public EntityDTO getEntity(MongoMetaModel metaModel) {
        if (metaModel == null) return null;
        return EntityDTO.builder()
                .entityType("database")
                .name(metaModel.getDbName())
                .attributes(List.of(AttributeDTO.builder()
                        .name("collections")
                        .entries(getCollectionEnitities(metaModel))
                        .build()))
                .build();
    }

    private List<EntityDTO> getCollectionEnitities(MongoMetaModel metaModel) {
        return metaModel.getCollections().stream()
                .map(collection -> EntityDTO.builder()
                        .entityType("collection")
                        .name(collection.getName())
                        .attributes(List.of(AttributeDTO.builder()
                                .name("schema")
                                .entries(List.of(EntityDTO.builder()
                                        .json(true)
                                        .name(getSchemaAsFormattedJsonString(collection.getSchema()))
                                        .build()))
                                .build()))
                        .build())
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private String getSchemaAsFormattedJsonString(Map<String, Object> schema) {
        ObjectMapper objectMapper = new ObjectMapper();
        DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("   ", DefaultIndenter.SYS_LF);
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentObjectsWith(indenter);
        printer.indentArraysWith(indenter);

        return objectMapper.writer(printer).writeValueAsString(schema);
    }

}

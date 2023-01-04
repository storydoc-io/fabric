package io.storydoc.fabric.jdbc.metadata;

import io.storydoc.fabric.systemdescription.app.entity.AttributeDTO;
import io.storydoc.fabric.systemdescription.app.entity.EntityDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DBMetaData2EntityModelMapper {

    public static final String ENTITY_TYPE__SCHEMA = "SCHEMA";
    public static final String ENTITY_TYPE__TABLE = "TABLE";
    public static final String ENTITY_TYPE__COLUMN = "COLUMN";
    public static final String ENTITY_TYPE__FOREIGN_KEY = "FOREIGNKEY";
    public static final String ENTITY_TYPE__PRIMARY_KEY = "PRIMARYKEY";

    public EntityDTO toEntityDto(DBMetaData dbMetaData) {
        return EntityDTO.builder()
                .name("Schema")
                .entityType(ENTITY_TYPE__SCHEMA)
                .attributes(List.of(
                        AttributeDTO.builder()
                                .name("Tables")
                                .entries(toEntityDtoList(dbMetaData.getTables()))
                                .build()
                ))
                .build();
    }

    private List<EntityDTO> toEntityDtoList(List<TableMetaData> tables) {
        return tables.stream()
                .map(this::toTableEntity)
                .collect(Collectors.toList());


    }

    private EntityDTO toTableEntity(TableMetaData table) {
        return EntityDTO.builder()
                .entityType(ENTITY_TYPE__TABLE)
                .name(table.getName())
                .attributes(List.of(
                        AttributeDTO.builder()
                                .name("Columns")
                                .entries(toColumnEntityDtoList(table.getColumns()))
                                .build(),
                        AttributeDTO.builder()
                                .name("Primary Key")
                                .entries(toPKColumnList(table.getPrimaryKey()))
                                .build(),
                        AttributeDTO.builder()
                                .name("Foreign Keys")
                                .entries(toFKEntityList(table.getForeignKeys()))
                                .build()

                ))
                .build();
    }

    private List<EntityDTO> toPKColumnList(PKMetaData primaryKey) {
        return primaryKey.getColumns().stream()
                .map(col -> {return EntityDTO.builder()
                        .name(col.getName())
                        .build();} )
                .collect(Collectors.toList());

    }

    private List<EntityDTO> toFKEntityList(List<FKMetaData> foreignKeys) {
        return foreignKeys.stream()
                .map(fk -> toFKEntity(fk))
                .collect(Collectors.toList());
    }

    private EntityDTO toFKEntity(FKMetaData fk) {
        return EntityDTO.builder()
                .name(fk.getFkName())
                .attributes(List.of(
                        AttributeDTO.builder()
                                .name("Table")
                                .entries(List.of(
                                        EntityDTO.builder()
                                                .name(fk.getPkTableName())
                                                .build()
                                ))
                                .build(),
                        AttributeDTO.builder()
                                .name("Columns")
                                .entries(toFKColumnEntityDtoList(fk))
                                .build()

                ))
                .build();
    }

    private List<EntityDTO> toFKColumnEntityDtoList(FKMetaData fk) {
        return fk.getColumns().stream()
                .map(col -> {return EntityDTO.builder()
                        .name(col.getFkColumnName())
                        .build();} )
                .collect(Collectors.toList());


    }

    private List<EntityDTO> toColumnEntityDtoList(Map<String, ColumnMetaData> columns) {
        return columns.values().stream()
                .map(this::toColumnEntity)
                .collect(Collectors.toList());
    }

    private EntityDTO toColumnEntity(ColumnMetaData column) {
        return EntityDTO.builder()
                .name(column.getName())
                .entityType(ENTITY_TYPE__COLUMN)
                .build();
    }

}

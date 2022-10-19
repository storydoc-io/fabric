package io.storydoc.fabric.jdbc.metadata;

import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.storydoc.fabric.jdbc.JDBCServiceBase.JDBC_SYSTEM_TYPE;

public class DBMetaData2StructureMapper {

    public static final String STRUCTURE_TYPE__TABLE = "TABLE";
    public static final String STRUCTURE_TYPE__FOREIGN_KEY = "FOREIGNKEY";

    public  StructureDTO toDto(DBMetaData dbMetaData) {
        return StructureDTO.builder()
                .systemType(JDBC_SYSTEM_TYPE)
                .structureType("SCHEMA")
                .id(dbMetaData.getSchemaName())
                .children(dbMetaData.getTables().stream()
                        .map(this::toTableStructureDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private StructureDTO toTableStructureDto(io.storydoc.fabric.jdbc.metadata.TableMetaData table) {
        return StructureDTO.builder()
                .systemType(JDBC_SYSTEM_TYPE)
                .structureType(STRUCTURE_TYPE__TABLE)
                .id(table.getName())
                .children(getChildren(table)
                )
                .build();
    }

    private List<StructureDTO> getChildren(TableMetaData table) {
        Stream<StructureDTO> columns =  table.getColumns().keySet().stream()
                .map(this::toColumnStructureDto);
        Stream<StructureDTO> foreignKeys = table.getForeignKeys().stream()
                .map(this::toFKStructureDto);
        return Stream.concat(columns, foreignKeys)
                .collect(Collectors.toList());
    }

    private StructureDTO toFKStructureDto(FKMetaData fk) {
        return StructureDTO.builder()
                .systemType(JDBC_SYSTEM_TYPE)
                .structureType(STRUCTURE_TYPE__FOREIGN_KEY)
                .id(fk.getFkName())
                .build();
    }

    private StructureDTO toColumnStructureDto(String columnName) {
        return StructureDTO.builder()
                .systemType(JDBC_SYSTEM_TYPE)
                .structureType("COLUMN")
                .id(columnName)
                .build();
    }

}

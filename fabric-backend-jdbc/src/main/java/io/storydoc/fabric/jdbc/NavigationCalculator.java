package io.storydoc.fabric.jdbc;

import io.storydoc.fabric.console.app.navigation.NavItem;
import io.storydoc.fabric.console.app.navigation.NavigationRequest;
import io.storydoc.fabric.jdbc.metadata.DBMetaData;
import io.storydoc.fabric.jdbc.metadata.FKMetaData;
import io.storydoc.fabric.jdbc.metadata.TableMetaData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.storydoc.fabric.jdbc.JDBCConstants.*;

public class NavigationCalculator {

    private final DBMetaData dbMetaData;

    public NavigationCalculator(DBMetaData dbMetaData) {
        this.dbMetaData = dbMetaData;
    }

    public List<NavItem> getNavigation(NavigationRequest navigationRequest) {
        if (navigationRequest.getNavItem() == null) {
            return getRootItems();
        } else {
            return Stream
                .concat(
                        getFKItems(navigationRequest, dbMetaData),
                        getReverseFKItems(navigationRequest, dbMetaData)
                )
                .collect(Collectors.toList());
        }
    }

    private List<NavItem> getRootItems() {
        return dbMetaData.getTables().stream()
                .filter(table -> !table.getName().startsWith("HT_"))
                .map(this::createSelectAllNavItem)
                .collect(Collectors.toList());
    }

    private Stream<NavItem> getFKItems(NavigationRequest navigationRequest, DBMetaData dbMetaData) {
        TableMetaData tableDto = dbMetaData.getTables().stream()
            .filter(table -> table.getName().equals(navigationRequest.getNavItem().getId()))
            .findFirst()
            .orElseThrow();
        return tableDto.getForeignKeys().stream()
            .map(fk -> createFollowFKNavItem(fk, navigationRequest));
    }

    private Stream<NavItem> getReverseFKItems(NavigationRequest navigationRequest, DBMetaData dbMetaData) {
        return dbMetaData.getTables().stream()
            .flatMap(tableMetaData -> getReverseFKItems(tableMetaData, navigationRequest.getNavItem().getId()));
    }

    private Stream<NavItem> getReverseFKItems(TableMetaData tableMetaData, String pkTableName) {
        return tableMetaData.getForeignKeys().stream()
            .filter(fkMetaData -> fkMetaData.getPkTableName().equals(pkTableName))
            .map(fkMetaData -> createReverseFKNavItem(tableMetaData, fkMetaData));
    }

    private NavItem createReverseFKNavItem(TableMetaData table, FKMetaData fk) {
        return NavItem.builder()
            .id(fk.getFkName())
            .label(table.getName())
            .type(NAV_TYPE__FOLLOW_REVERSE_FK)
            .attributes(Map.of(
                    CONSOLE_FIELD_SQL_QUERY, String.format("select * from %s where ... ", table.getName()))
            )
            .build();
    }

    private NavItem createFollowFKNavItem(FKMetaData fk, NavigationRequest navigationRequest) {
        return NavItem.builder()
            .id(fk.getFkName())
            .label(fk.getPkTableName())
            .type(NAV_TYPE__FOLLOW_FK)
            .attributes(Map.of(
                    CONSOLE_FIELD_SQL_QUERY, String.format("select * from %s where ... ", fk.getPkName()))
            )
            .build();
    }

    private NavItem createSelectAllNavItem(TableMetaData table) {
        return NavItem.builder()
                .id(table.getName())
                .type(NAV_TYPE__SELECT_ALL)
                .label(table.getName())
                .attributes(Map.of(
                        CONSOLE_FIELD_SQL_QUERY, String.format("select * from %s", table.getName())))
                .build();
    }


}

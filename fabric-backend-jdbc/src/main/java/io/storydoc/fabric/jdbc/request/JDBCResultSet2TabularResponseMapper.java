package io.storydoc.fabric.jdbc.request;

import io.storydoc.fabric.query.app.tabular.Column;
import io.storydoc.fabric.query.app.tabular.Row;
import io.storydoc.fabric.query.app.tabular.TabularResultSet;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCResultSet2TabularResponseMapper {

    public TabularResultSet tabularResponse(ResultSet resultSet) throws SQLException {
        return TabularResultSet.builder()
                .rows(getRows(resultSet))
                .columns(getColumns(resultSet))
                .build();
    }

    private List<Row> getRows(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        List<Row> rows = new ArrayList<>();
        while(resultSet.next()) {
            List<String> values = new ArrayList<>();
            for (int colIdx = 1; colIdx <= resultSetMetaData.getColumnCount(); colIdx++) {
                values.add(resultSet.getString(colIdx));
            }
            rows.add(Row.builder()
                    .values(values)
                    .build());
        }
        return rows;
    }

    private List<Column> getColumns(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        List<Column> columns = new ArrayList<>();

        for(int colIdx = 1; colIdx <= resultSetMetaData.getColumnCount(); colIdx++) {
            columns.add(Column.builder()
                    .name(resultSetMetaData.getColumnName(colIdx))
                    .build());
        }

        return columns;
    }

}

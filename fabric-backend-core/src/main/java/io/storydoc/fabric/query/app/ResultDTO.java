package io.storydoc.fabric.query.app;

import io.storydoc.fabric.console.app.navigation.NavItem;
import io.storydoc.fabric.query.app.documents.DocumentsResultSet;
import io.storydoc.fabric.query.app.tabular.TabularResultSet;
import io.storydoc.fabric.query.app.tabular.TabularResultSetMetaDataDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {
    private String systemType;
    private ResultType resultType;
    private TabularResultSetMetaDataDTO description;
    private String content;
    private DocumentsResultSet documentsResultSet;
    private TabularResultSet tabular;
    private List<NavItem> navItems;
}

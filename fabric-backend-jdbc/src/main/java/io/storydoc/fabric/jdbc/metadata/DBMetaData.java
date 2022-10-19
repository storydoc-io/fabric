package io.storydoc.fabric.jdbc.metadata;

import io.storydoc.fabric.metamodel.infra.MetaModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DBMetaData extends MetaModel {

	private String schemaName;

	private List<TableMetaData> tables = new ArrayList<>();


}

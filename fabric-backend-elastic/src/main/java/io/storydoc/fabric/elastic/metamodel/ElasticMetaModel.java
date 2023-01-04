package io.storydoc.fabric.elastic.metamodel;

import io.storydoc.fabric.metamodel.infra.MetaModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ElasticMetaModel extends MetaModel {
    private List<Schema> schemas;
}

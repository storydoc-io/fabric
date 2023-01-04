package io.storydoc.fabric.mongo.metamodel;

import io.storydoc.fabric.metamodel.infra.MetaModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MongoMetaModel extends MetaModel {

    private String dbName;
    private List<Collection> collections;

}

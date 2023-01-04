package io.storydoc.fabric.testinfra.mock.mockdb;

import io.storydoc.fabric.metamodel.infra.MetaModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MockDBMetaModel extends MetaModel {
    private String dbName;
}

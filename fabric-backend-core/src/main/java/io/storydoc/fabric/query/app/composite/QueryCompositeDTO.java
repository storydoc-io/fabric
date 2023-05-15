package io.storydoc.fabric.query.app.composite;

import io.storydoc.fabric.query.app.QueryDTO;
import io.storydoc.fabric.query.app.ResultDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class QueryCompositeDTO {

    private String id;
    private QueryDTO query;
    private ResultDTO result;

    private List<QueryCompositeDTO> children;
}

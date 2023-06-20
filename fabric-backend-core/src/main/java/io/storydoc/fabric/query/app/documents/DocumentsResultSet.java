package io.storydoc.fabric.query.app.documents;

import io.storydoc.fabric.query.app.PagingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentsResultSet {

    private List<String> documents;
    private PagingDTO pagingInfo;

}

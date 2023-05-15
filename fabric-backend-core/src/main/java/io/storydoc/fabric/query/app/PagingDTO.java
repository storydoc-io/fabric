package io.storydoc.fabric.query.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagingDTO {
    private int pageSize;
    private int pageNr;
    private long nrOfResults;
}

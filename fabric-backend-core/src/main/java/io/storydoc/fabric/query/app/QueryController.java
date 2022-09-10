package io.storydoc.fabric.query.app;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/query")
public class QueryController {

    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @PostMapping(value="/run", produces = MediaType.APPLICATION_JSON_VALUE)
    public QueryResponseItemDTO doQuery(@RequestBody QueryRequestDTO queryRequestDTO) {
        return queryService.doQuery(queryRequestDTO);
    }

}

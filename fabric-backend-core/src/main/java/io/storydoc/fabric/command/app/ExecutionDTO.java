package io.storydoc.fabric.command.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionDTO {
    private String label;
    private ExecutionStatus status;
    private int percentDone;
    private List<ExecutionDTO> children;
}

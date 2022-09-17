package io.storydoc.fabric.console.app.describe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsoleDescriptorItemDTO {

    private ConsoleInputType inputType;
    private String name;
    private List<String> selectValues;

}

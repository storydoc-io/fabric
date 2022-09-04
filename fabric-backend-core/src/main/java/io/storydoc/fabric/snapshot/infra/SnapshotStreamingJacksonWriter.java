package io.storydoc.fabric.snapshot.infra;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.storydoc.fabric.snapshot.app.SnapshotItemDTO;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.OutputStream;

public class SnapshotStreamingJacksonWriter {

    private final OutputStream outputStream;
    private final JsonGenerator g;

    @SneakyThrows
    public SnapshotStreamingJacksonWriter(OutputStream outputStream, ObjectMapper objectMapper) {
        this.outputStream = outputStream;
        JsonFactory jfactory = new JsonFactory(objectMapper);
        g = jfactory.createGenerator(outputStream, JsonEncoding.UTF8);

    }

    @SneakyThrows
    public void item(SnapshotItemDTO snapshotItemDTO) {
        g.writeStartObject();
        g.writeStringField("systemType", snapshotItemDTO.getSystemType());
        g.writeStringField("snapshotItemType", snapshotItemDTO.getSnapshotItemType());
        if (snapshotItemDTO.getId() != null) {
            g.writeStringField("id", snapshotItemDTO.getId());
        }
        if (snapshotItemDTO.getAttributes() != null) {
            g.writeFieldName("attributes");
            g.writeObject(snapshotItemDTO.getAttributes());
        }
    }

    @SneakyThrows
    public void itemEnd() {
        g.writeEndObject();
    }

    @SneakyThrows
    public void children()  {
        g.writeFieldName("children");
        g.writeStartArray();
    }

    @SneakyThrows
    public void childrenEnd()  {
        g.writeEndArray();
    }

    @SneakyThrows
    public void flush() {
        g.flush();
    }


}

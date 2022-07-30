package io.storydoc.fabric.mongo;

import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MongoSnapshot extends SnapshotComponent {

    private List<CollectionSnapshot> collectionSnapshots;

}

package io.storydoc.fabric.mongo.navigation;

import io.storydoc.fabric.navigation.infra.NavigationModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MongoNavigationModel extends NavigationModel {

    private List<CollectionNavItem> roots;

}

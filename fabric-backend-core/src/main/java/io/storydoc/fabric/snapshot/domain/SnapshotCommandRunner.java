package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.snapshot.app.descriptor.SnapshotDescriptorDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import org.springframework.stereotype.Component;

@Component
public class SnapshotCommandRunner {

    private final SystemDescriptionService systemDescriptionService;

    public SnapshotCommandRunner(SystemDescriptionService systemDescriptionService) {
        this.systemDescriptionService = systemDescriptionService;
    }

    public void runSnapshotCommand(SnapshotDescriptorDTO snapshotDescriptorDTO) {
/*
        String envKey = snapshotDescriptorDTO.getEnvironmentKey();

        SystemDescriptionDTO systemDescriptionDTO  = systemDescriptionService.getSystemDescription();

        Fabric fabric = new Fabric();

        SnapshotCommand.Builder commandBuilder = SnapshotCommand.builder();

        for (SystemComponentDTO systemComponent : systemDescriptionDTO.getSystemComponents()) {
            String systemComponentKey = systemComponent.getKey();
            String systemType = systemComponent.getSystemType();
            String systemLabel = systemComponent.getLabel();

            if ("MONGO".equals(systemType))  {

                fabric.addHandler(new MongoHandler());


                String uri = systemDescriptionDTO.getSettings().get(envKey).get(systemComponentKey).get("uri");
                MongoComponentDescriptor mongoComponentDescriptor = new MongoComponentDescriptor(systemLabel, uri);
                commandBuilder.component(mongoComponentDescriptor);
            }
        }

        SnapshotCommand snapshotCommand = commandBuilder.build();

        fabric.createBundle(snapshotCommand);

 */
    }

}

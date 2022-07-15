package io.storydoc.fabric;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FabricApp {

    @Value("${server.port}")
    long port;

    public static void main(String[] args) {

        SpringApplication.run(FabricApp.class, args);


    }

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        System.out.println(String.format("Fabric running at http://localhost:%s",  port));
    }

}

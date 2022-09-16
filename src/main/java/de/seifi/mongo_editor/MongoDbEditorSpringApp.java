package de.seifi.mongo_editor;

import de.seifi.mongo_editor.utils.SpringBuiltInEventsListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MongoDbEditorSpringApp {

    private static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(MongoDbEditorFxApp.class);
        springApplication.addListeners(new SpringBuiltInEventsListener());

        applicationContext = springApplication.run(args);


    }

}

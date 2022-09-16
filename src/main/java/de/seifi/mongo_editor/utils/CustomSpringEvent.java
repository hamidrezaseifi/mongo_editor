package de.seifi.mongo_editor.utils;

import java.time.Clock;

import org.springframework.context.ApplicationEvent;

public class CustomSpringEvent extends ApplicationEvent {

	private String message;

    public CustomSpringEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public CustomSpringEvent(Object source, String message, Clock clock) {
        super(source, clock);
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    

}

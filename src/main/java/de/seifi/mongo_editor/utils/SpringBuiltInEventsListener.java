package de.seifi.mongo_editor.utils;

import de.seifi.mongo_editor.MongoDbEditorFxApp;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationListener;

public class SpringBuiltInEventsListener implements ApplicationListener<SpringApplicationEvent>{

	@Override
	public void onApplicationEvent(SpringApplicationEvent event) {
		System.out.println("BuiltIn ApplicationEvent received: " + event.getTimestamp() + " - " + event.getSource());
		MongoDbEditorFxApp.newSpringInitialEvent(event);
		
	}

}

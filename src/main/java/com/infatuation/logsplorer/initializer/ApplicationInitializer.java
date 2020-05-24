package com.infatuation.logsplorer.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class ApplicationInitializer
		implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("=============================  initializer ========================");

		// start the thread
		LogLoaderTask logLoaderTask = applicationContext.getBean(LogLoaderTask.class);
		taskExecutor.execute(logLoaderTask);
	}

}
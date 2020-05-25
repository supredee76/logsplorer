package com.infatuation.logsplorer.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);

	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * This class listening to application start up event and spin data loader on a separate thread
	 */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		logger.info("=============================  initializer =============================");

		// start data loader on a separate thread
		LogLoaderTask logLoaderTask = applicationContext.getBean(LogLoaderTask.class);
		taskExecutor.execute(logLoaderTask);
	}

}
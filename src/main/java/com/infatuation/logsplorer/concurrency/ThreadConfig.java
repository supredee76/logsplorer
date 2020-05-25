package com.infatuation.logsplorer.concurrency;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadConfig {

	/**
	 * Configuration thread TaskExecutor.
	 * Spring’s TaskExecutor interface is identical to the java.util.concurrent.Executor interface.
	 * The interface has a single method execute(Runnable task) that accepts a task for execution based on the semantics and configuration of the thread pool.
	 * The TaskExecutor give other Spring components an abstraction for thread pooling where needed.
	 * Components such as the ApplicationListener, we used for log data loader, rely on TaskExcutor to configure thread pool.
	 * Read more at: https://docs.spring.io/spring/docs/4.2.x/spring-framework-reference/html/scheduling.html
	 */
	@Bean
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(4);
		executor.setMaxPoolSize(4);
		executor.setThreadNamePrefix("task_executor_thread");
		executor.initialize();
		return executor;
	}
}

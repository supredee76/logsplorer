package com.infatuation.logsplorer.concurrency;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadConfig {
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

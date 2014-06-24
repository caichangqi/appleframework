package com.appleframework.core.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Cruise.Xu
 */
public class FixedTimeOperation implements Callable<Object> {

	private ExecutorService executor;
	private Callable<Object> task;
	private int timeout;

	public FixedTimeOperation(ExecutorService executor, Callable<Object> task, int timeout) {
		this.executor = executor;
		this.task = task;
		this.timeout = timeout;
	}

	@Override
	public Object call() throws ExecutionException, InterruptedException, TimeoutException {
		Future<?> future = executor.submit(task);
		return future.get(timeout, TimeUnit.MILLISECONDS);
	}
}

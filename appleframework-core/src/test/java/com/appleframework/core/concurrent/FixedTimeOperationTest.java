package com.appleframework.core.concurrent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * @author Cruise.Xu
 */
public class FixedTimeOperationTest {
	
	private ExecutorService executorService;

	@Before
	public void setUp() throws Exception {
		executorService = Executors.newSingleThreadExecutor();
	}

	@After
	public void tearDown() throws Exception {
		executorService.shutdown();
	}

	@Test(expected = TimeoutException.class)
	public void gotTimeoutException() throws Exception {
		FixedTimeOperation operation = new FixedTimeOperation(executorService,
				new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						for (int i = 0; i <= 50; i++) {
							Thread.sleep(1);
						}
						return null;
					}
				}, 10);
		operation.call();
	}

	@Test
	public void ableToFinishWithoutException() throws ExecutionException, TimeoutException, InterruptedException {
		FixedTimeOperation operation = new FixedTimeOperation(executorService,
				new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						Thread.sleep(1);
						return new Object();
					}
				}, 10000);
		operation.call();
	}
}

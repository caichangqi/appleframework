package com.appleframework.core.concurrent;

import com.appleframework.core.io.FileUtility;
import com.appleframework.core.log.LogFactory;
import com.appleframework.core.log.Logger;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * This class allows to execute parallel tasks in separate thread and wait for completion.
 * 
 * @author Cruise.Xu
 */
public class MultiTaskExecutionWaiter {
	
   private Logger logger = LogFactory.getLogger(getClass());
   
   private Collection<Callable<?>> tasks;
   private boolean useParallelRun;
   private Collection<Exception> fails = new LinkedHashSet<Exception>();

   public MultiTaskExecutionWaiter(Collection<Callable<?>> tasks, boolean useParallelRun) {
      this.tasks = tasks;
      this.useParallelRun = useParallelRun;
   }

   /**
    * await for tasks completion.
    * 
    * @throws ExecutionException
    *            if callable failed.
    */
   public void await() throws ExecutionException {
      if (useParallelRun) {
         CountDownLatch latch = new CountDownLatch(tasks.size());
         try {
            for (Callable<?> task : tasks) {
               execute(task, latch);
            }
            latch.await();
         } catch (InterruptedException e) {
            logger.error(e);
         }
      } else {
         for (Callable<?> task : tasks) {
            try {
               task.call();
            } catch (Exception e) {
               fails.add(e);
            }
         }
      }
      if (!fails.isEmpty()) {
         StringBuilder builder = new StringBuilder();
         for (Exception fail : fails) {
            builder.append(ExceptionUtils.getFullStackTrace(fail)).append(
                     FileUtility.LINE_SEPARATOR);
         }
         throw new ExecutionException(String.format("execution failed with errors [%s]",
                  builder.toString())) {
            private static final long serialVersionUID = -2985104400821797031L;
         };
      }
   }

   private void execute(final Callable<?> task, final CountDownLatch latch) {
      new Thread(new Runnable() {
         @Override
         public void run() {
            try {
               task.call();
            } catch (Exception e) {
               logger.warn(e);
               fails.add(e);
            } finally {
               logger.debug("%s finished execution", task.toString());
               latch.countDown();
            }
         }
      }).start();
   }
}

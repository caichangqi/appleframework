package com.appleframework.core.lang;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author Santhosh Kumar T
 */
public abstract class ThrowableTask<R, E extends Exception> {
   private final Class<E> exceptionClass;
   private R r;
   private Exception ex;

   public ThrowableTask(Class<E> exceptionClass) {
      this.exceptionClass = exceptionClass;
   }

   public abstract R run() throws E;

   @SuppressWarnings({ "unchecked" })
   public R getResult() throws E {
      if (exceptionClass.isInstance(ex)) {
         throw (E) ex;
      } else if (ex != null) {
         throw new NestableRuntimeException(ex);
      }
      return r;
   }

   public Exception getException() {
      return ex;
   }

   public Runnable asRunnable() {
      return new Runnable() {
         @Override
         public void run() {
            try {
               r = ThrowableTask.this.run();
            } catch (Exception ex) {
               ThrowableTask.this.ex = ex;
            }
         }
      };
   }
}

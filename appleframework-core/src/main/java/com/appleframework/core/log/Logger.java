package com.appleframework.core.log;

/**
 * Logger is wrapper interface for well known org.slf4j.Logger.
 * <p/>
 * Please note:
 * <p/>
 * 1) this interface does not define method isSomeLevelEnabled(). Implementation will handle this at
 * runtime, so you should not worry about this.
 * <p/>
 * 2) each method with var args parameters list uses String.format, so actually independent of
 * logging frameworks.
 * 
 * @author Cruise.Xu
 */
public interface Logger {
   Logger trace(String message, Object... args);

   Logger debug(String message, Object... args);

   Logger info(String message, Object... args);

   Logger warn(String message, Object... args);

   Logger error(String message, Object... args);

   Logger warn(Throwable throwable);

   Logger error(Throwable throwable);
}

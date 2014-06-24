package com.appleframework.core.log;

import static com.appleframework.core.io.FileUtility.SEPARATOR;

import java.io.Serializable;
import java.util.Arrays;
import java.util.IllegalFormatException;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * @author Cruise.Xu
 */
class LogFactoryDelegate implements Logger, Serializable {
	
   private static final long serialVersionUID = 4836199787895570019L;
   private final org.slf4j.Logger logger;

   public LogFactoryDelegate(org.slf4j.Logger logger) {
      this.logger = logger;
   }

   @Override
   public Logger trace(String message, Object... args) {
      if (logger.isTraceEnabled()) {
         logger.trace(formatMessage(message, args));
      }
      return this;
   }

   @Override
   public Logger debug(String message, Object... args) {
      if (logger.isDebugEnabled()) {
         logger.debug(formatMessage(message, args));
      }
      return this;
   }

   @Override
   public Logger info(String message, Object... args) {
      if (logger.isInfoEnabled()) {
         logger.info(formatMessage(message, args));
      }
      return this;
   }

   @Override
   public Logger warn(String message, Object... args) {
      logger.warn(formatMessage(message, args));
      return this;
   }

   @Override
   public Logger error(String message, Object... args) {
      logger.error(formatMessage(message, args));
      return this;
   }

   @Override
   public Logger warn(Throwable throwable) {
      logger.warn("Unexpected problem occurred", throwable);
      return this;
   }

   @Override
   public Logger error(Throwable throwable) {
      logger.warn("Unexpected error occurred", throwable);
      return this;
   }

   /**
    * perform actual formatting using standard String.format() method.
    * 
    * @param message
    *           log message
    * @param args
    *           var args
    * @return formatted messages
    */
   protected String formatMessage(String message, Object... args) {
      if (args != null && args.length > 0) {
         Object[] newValues = new Object[args.length];
         for (int i = 0; i < args.length; i++) {
            newValues[i] = (args[i] instanceof Throwable) ? ExceptionUtils
                     .getFullStackTrace((Throwable) args[i]) : args[i];
         }
         try {
            return String.format(message, newValues);
         } catch (IllegalFormatException ex) {
            logger.error(
                     String.format(
                              "log message [%s] is not constructed correctly for args = [%s]."
                                       + SEPARATOR
                                       + "visit java formatting http://download.oracle.com/javase/1.5.0/docs/api/java/util/Formatter.html",
                              message, Arrays.toString(args)), ex);
         }
      }
      return message;
   }
}

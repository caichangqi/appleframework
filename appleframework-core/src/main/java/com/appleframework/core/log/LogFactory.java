package com.appleframework.core.log;

import static com.appleframework.core.lang.RuntimeUtility.printDataInformation;
import static org.springframework.util.Log4jConfigurer.initLogging;

import java.io.FileNotFoundException;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.slf4j.LoggerFactory;

import com.appleframework.core.utils.Enumerator;

/**
 * Logger factory interface.
 * 
 * @author Cruise.Xu
 */
@net.jcip.annotations.ThreadSafe
public final class LogFactory {
   static {
      System.setProperty("logging.type", "log4j");
   }

   /**
    * @return list of appenders.
    */
   @SuppressWarnings({ "unchecked" })
   public static Enumerator<Appender> getAllRootAppenders() {
      @SuppressWarnings("rawtypes")
      Enumeration allAppenders = org.apache.log4j.Logger.getRootLogger().getAllAppenders();
      return new Enumerator<Appender>(allAppenders);
   }

   /**
    * @param loggerName
    *           name of the logger.
    * @return all appenders for logger.
    */
   @SuppressWarnings({ "unchecked" })
   public static Enumerator<Appender> getAllAppenders(String loggerName) {
      org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(loggerName);
      if (logger != null) {
         return new Enumerator<Appender>(logger.getAllAppenders());
      }
      return null;
   }

   /**
    * Return a logger named according to the name parameter using the statically bound
    * {@link LogFactory} instance.
    * 
    * @param name
    *           The name of the logger.
    * @return logger
    */
   public static Logger getLogger(final String name) {
      final org.slf4j.Logger logger = LoggerFactory.getLogger(name);
      return new LogFactoryDelegate(logger);
   }

   /**
    * Return a logger named corresponding to the class passed as parameter, using the statically
    * bound {@link LogFactory} instance.
    * 
    * @param clazz
    *           the returned logger will be named after clazz
    * @return logger
    */
   public static Logger getLogger(final Class<?> clazz) {
      org.slf4j.Logger logger = LoggerFactory.getLogger(clazz.getName());
      return new LogFactoryDelegate(logger);
   }

   /**
    * performs configuration from given inputStream.
    * 
    * @param location
    *           location of log4j.xml
    * @throws java.io.FileNotFoundException
    *            of file is not available.
    */
   public static void configure(String location) throws FileNotFoundException {
      initLogging(location);
      printDataInformation();
   }

   private LogFactory() {
   }
}

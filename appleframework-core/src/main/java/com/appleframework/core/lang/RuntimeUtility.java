package com.appleframework.core.lang;

import static com.appleframework.core.io.FileUtility.path;
import static org.apache.commons.lang.StringUtils.EMPTY;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.UUID;

import org.apache.commons.lang.exception.NestableRuntimeException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import com.appleframework.core.RuntimeMode;
import com.appleframework.core.io.IOPump;
import com.appleframework.core.utils.StringUtility;
import com.appleframework.core.log.LogFactory;
import com.appleframework.core.log.Logger;

/**
 * @author Santhosh Kumar T
 * @author Cruise.Xu
 */
public final class RuntimeUtility {
   private static final short MAX_GC_ITERATIONS = Short.MAX_VALUE;
   /**
    * root location of data directory.
    * <p/>
    * can be customized with DATA_DIR system property.
    */
   public static final String DATA_DIR;
   /**
    * root location of log4j data directory.
    * <p/>
    * can be customized with DATA_DIR system property.
    */
   public static final String LOG_DATA_DIR;
   /**
    * location for Transaction manager's recovery data.
    * <p/>
    * can be customized with DATA_DIR system property.
    */
   public static final String TM_DATA_DIR;
   /**
    * location for hibernate schema creation/update/drop schema scripts.
    * <p/>
    * can be customized with DATA_DIR system property.
    */
   public static final String HIBERNATE_DATA_DIR;
   /**
    * location for hibernate search index files.
    * <p/>
    * can be customized with DATA_DIR system property.
    */
   public static final String HIBERNATE_SEARCH_DATA_DIR;
   /**
    * you can find service id using this property.
    */
   public static final String VAR_SERVICE_ID = "service_id";
   /**
    * you can find runtime mode using this property(it can be prod/uat/dev/junit).
    */
   public static final String VAR_RUNTIME_MODE = "runtime_mode";
   /**
    * you can set-up base data_dir using this property.
    */
   public static final String VAR_DATA_DIR = "data_dir";
   /**
    * hazelcast configuration file location.
    */
   public static final String VAR_HAZELCAST_CONFIGURATION = "hazelcast_config";

   static {
      File currentDir = new File(".");
      try {
         String path = currentDir.getCanonicalPath();
         String dir = System.getProperty(VAR_DATA_DIR);
         //
         DATA_DIR = (dir == null ? path(path, "data") : dir);
         TM_DATA_DIR = path(DATA_DIR, "tm");
         LOG_DATA_DIR = path(DATA_DIR, "logs");
         HIBERNATE_DATA_DIR = path(DATA_DIR, "hibernate");
         HIBERNATE_SEARCH_DATA_DIR = path(DATA_DIR, "indexes");
      } catch (IOException e) {
         throw new NestableRuntimeException(e);
      }
   }

   public static void printDataInformation() {
      Logger logger = LogFactory.getLogger(RuntimeUtility.class);
      //
      logger.info("DATA_DIR = %s", DATA_DIR);
      logger.info("TM_DATA_DIR = %s", TM_DATA_DIR);
      logger.info("LOG_DATA_DIR = %s", LOG_DATA_DIR);
      logger.info("HIBERNATE_DATA_DIR = %s", HIBERNATE_DATA_DIR);
      logger.info("HIBERNATE_SEARCH_DATA_DIR = %s", HIBERNATE_SEARCH_DATA_DIR);
   }

   /**
    * This method guarantees that garbage collection is done unlike <code>{@link System#gc()}</code>
    */
   @SuppressWarnings({})
   public static void gc() {
      Object obj = new Object();
      WeakReference<Object> ref = new WeakReference<Object>(obj);
      obj = null;
      int counter = 0;
      while (ref.get() != null && (counter++ < MAX_GC_ITERATIONS)) {
         System.gc();
      }
   }

   /**
    * This method guarantees that garbage collection is done after JVM shutdown is initialized
    */
   public static void gcOnExit() {
      Runtime.getRuntime().addShutdownHook(new Thread() {
         @Override
         public void run() {
            gc();
         }
      });
   }

   /*-------------------------------------------------[ Terminal Command ]---------------------------------------------------*/

   /**
    * Runs the specified <code>command</code> in terminal (sh in unix/cmd in windows) and returns
    * the command output.
    * 
    * @param command
    *           complete command to be executed
    * @param envp
    *           array of strings, each element of which has environment variable settings in the
    *           format <i>name</i>=<i>value</i>, or <tt>null</tt> if the subprocess should inherit
    *           the environment of the current process.
    * @param workingDir
    *           the working directory of the subprocess, or <tt>null</tt> if the subprocess should
    *           inherit the working directory of the current process.
    * @return output of the command.
    * @throws IOException
    *            If an I/O error occurs
    * @see #runCommand(String)
    */
   public static String runCommand(String command, String[] envp, File workingDir)
            throws IOException {
      String cmd[];
      if (OS.get().isUnix()) {
         cmd = new String[] { "sh", "-c", command };
      } else {
         cmd = new String[] { "cmd", "/C", command };
      }
      Process p = Runtime.getRuntime().exec(cmd, envp, workingDir);
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      redirectStreams(p, output, System.err);
      try {
         p.waitFor();
      } catch (InterruptedException ex) {
         throw new NestableRuntimeException("interrupted", ex);
      }
      if (p.exitValue() != 0) {
         throw new IOException("exitValue is " + p.exitValue());
      }
      return output.toString();
   }

   /**
    * Runs the specified <code>command</code> in terminal (sh in *nix/cmd in windows) and returns
    * the command output. The subprocess created inherits environment and working directory from the
    * current process
    * 
    * @param command
    *           complete command to be executed
    * @return output of the command.
    * @throws IOException
    *            If an I/O error occurs
    * @see #runCommand(String, String[], java.io.File)
    */
   public static String runCommand(String command) throws IOException {
      return runCommand(command, null, null);
   }

   /**
    * Redirects given process's input and error streams to the specified streams. the streams
    * specified are not closed automatically.
    * <p/>
    * The streams passed can be null, if you don't want to redirect them.
    * 
    * @param process
    *           process whose streams to be redirected
    * @param output
    *           outputStream to which process's inputStream is redirected.<br>
    *           null if you don't want to redirect.
    * @param error
    *           outputStream to which process's errorStream is redirected.<br>
    *           null if you don't want to redirect.
    */
   public static void redirectStreams(Process process, OutputStream output, OutputStream error) {
      if (output != null) {
         new Thread(new IOPump(process.getInputStream(), output, false, false).asRunnable())
                  .start();
      }
      if (error != null) {
         new Thread(new IOPump(process.getErrorStream(), error, false, false).asRunnable()).start();
      }
   }

   public static Object newInstance(String name) {
      try {
         return BeanUtils.instantiate(ClassUtils.forName(name, ClassUtils.getDefaultClassLoader()));
      } catch (Exception e) {
         throw new IllegalArgumentException("unable to instantiate object", e);
      }
   }

   /**
    * @return actual runtime mode or <code>NULL</code> if runtime_mode system property is not set.
    */
   public static RuntimeMode getRuntimeMode() {
      String property = System.getProperty(VAR_RUNTIME_MODE);
      if (!StringUtility.isEmpty(property)) {
         return Enum.valueOf(RuntimeMode.class, property);
      }
      return null;
   }

   public static boolean isJunit() {
      return RuntimeMode.JUNIT == getRuntimeMode();
   }

   public static boolean isProduction() {
      return RuntimeMode.PROD == getRuntimeMode();
   }

   public static String getJmxNameExtension() {
      if (getRuntimeMode() != null && getRuntimeMode() == RuntimeMode.JUNIT) {
         return UUID.randomUUID().toString();
      }
      return EMPTY;
   }

   private RuntimeUtility() {
   }
}

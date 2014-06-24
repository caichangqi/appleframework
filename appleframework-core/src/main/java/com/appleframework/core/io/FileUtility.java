package com.appleframework.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang.SystemUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * @author Cruise.Xu
 */
public final class FileUtility {
   public static final String PATH_SEPARATOR = File.pathSeparator;
   public static final String SEPARATOR = File.separator;
   public static final String LINE_SEPARATOR = System.getProperty("line.separator");
   //
   public static final File JAVA_HOME = SystemUtils.getJavaHome();
   public static final File USER_HOME = SystemUtils.getUserHome();
   public static final File USER_DIR = SystemUtils.getUserDir();
   public static final File TMP_DIR = SystemUtils.getJavaIoTmpDir();

   /**
    * verifies that source file exists and that file is readable.
    * 
    * @param file
    *           source file
    * @throws IllegalArgumentException
    *            if source file is a directory or if not readable.
    * @throws FileNotFoundException
    *            if file does not exist
    */
   public static void ensureFileIsReadable(final File file) throws FileNotFoundException {
      if (!file.exists()) {
         throw new FileNotFoundException(String.format("source file [%s] does not exist",
                  file.toString()));
      }
      if (file.isDirectory()) {
         throw new IllegalArgumentException(String.format("source file [%s] is a directory",
                  file.toString()));
      }
      if (!file.canRead()) {
         throw new IllegalArgumentException(String.format("source file [%s] is not readable", file));
      }
   }

   public static File fromClasspathSource(final String source) throws IOException {
      final ClassPathResource resource = new ClassPathResource(source);
      return resource.getFile();
   }

   public static Resource resolve(String path) throws IOException {
      if (path != null) {
         ClassPathResource r1 = new ClassPathResource(path);
         if (r1.exists()) {
            return r1;
         }
         UrlResource r2 = new UrlResource(path);
         if (r2.exists()) {
            return r2;
         }
         throw new IOException(String.format("resource %s does not exists", path));
      }
      return null;
   }

   public static String path(String... args) {
      StringBuilder builder = new StringBuilder();
      for (String arg : args) {
         builder.append(arg.endsWith(SEPARATOR) ? arg : arg + SEPARATOR);
      }
      return builder.toString();
   }

   private FileUtility() {
   }
}

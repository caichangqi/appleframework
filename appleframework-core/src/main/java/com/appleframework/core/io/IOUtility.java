package com.appleframework.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.appleframework.core.log.LogFactory;
import com.appleframework.core.log.Logger;

/**
 * @author Santhosh Kumar T
 * @author Cruise.Xu
 */
public final class IOUtility {
   private static final Logger LOGGER = LogFactory.getLogger(IOUtility.class);
   /*-------------------------------------------------[ Standard Char sets ]---------------------------------------------------*/
   public static final Charset US_ASCII = Charset.forName("US-ASCII");
   public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
   public static final Charset UTF_8 = Charset.forName("UTF-8");
   public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
   public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
   public static final Charset UTF_16 = Charset.forName("UTF-16");

   /**
    * Reads data from <code>is</code> and writes it into <code>os</code>.<br>
    * <code>is</code> and <code>os</code> are closed if <code>closeIn</code> and
    * <code>closeOut</code> are true respectively.
    * 
    * @param is
    *           inputstream from which data is read
    * @param os
    *           outputstream into which data is written
    * @param closeIn
    *           close inputstream or not
    * @param closeOut
    *           close outputstream or not
    * @return the argument <code>os</os>
    * @throws IOException
    *            if an I/O error occurs.
    */
   public static <T extends OutputStream> T pump(InputStream is, T os, boolean closeIn,
            boolean closeOut) throws IOException {
      byte buff[] = new byte[1024];
      int len;
      Exception exception = null;
      try {
         while ((len = is.read(buff)) != -1) {
            os.write(buff, 0, len);
         }
      } catch (Exception ex) {
         exception = ex;
      } finally {
         try {
            try {
               if (closeIn) {
                  is.close();
               }
            } finally {
               if (closeOut) {
                  os.close();
               }
            }
         } catch (IOException ex) {
            if (exception != null) {
               LOGGER.error(ex);
            } else {
               exception = ex;
            }
         }
      }
      if (exception instanceof IOException) {
         throw (IOException) exception;
      } else if (exception instanceof RuntimeException) {
         throw (RuntimeException) exception;
      }
      return os;
   }

   /**
    * Reads data from <code>is</code> and writes it into an instanceof
    * {@link ByteArrayOutputStream2}.<br>
    * 
    * @param is
    *           inputstream from which data is read
    * @param closeIn
    *           close inputstream or not
    * @return the instance of {@link ByteArrayOutputStream2} into which data is written
    * @throws IOException
    *            if an I/O error occurs.
    */
   public static ByteArrayOutputStream2 pump(InputStream is, boolean closeIn) throws IOException {
      return pump(is, new ByteArrayOutputStream2(), closeIn, true);
   }

   private IOUtility() {
   }
}

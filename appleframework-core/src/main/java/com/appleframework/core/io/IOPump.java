package com.appleframework.core.io;

import com.appleframework.core.lang.ThrowableTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Santhosh Kumar T
 */
public class IOPump extends ThrowableTask<Void, IOException> {
   private InputStream is;
   private OutputStream os;
   private boolean closeIn;
   private boolean closeOut;

   public IOPump(InputStream is, OutputStream os, boolean closeIn, boolean closeOut) {
      super(IOException.class);
      this.is = is;
      this.os = os;
      this.closeIn = closeIn;
      this.closeOut = closeOut;
   }

   @Override
   public Void run() throws IOException {
      IOUtility.pump(is, os, closeIn, closeOut);
      return null;
   }
}

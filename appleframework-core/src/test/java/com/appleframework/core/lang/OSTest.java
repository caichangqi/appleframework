package com.appleframework.core.lang;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Cruise.Xu
 */
public class OSTest {
   @Test
   public void osDetected() {
      System.out.println(OS.get().name());
      if (OS.get().name().toLowerCase().startsWith("windows")) {
         assertThat(OS.get().isWindows(), is(true));
      }
   }
}

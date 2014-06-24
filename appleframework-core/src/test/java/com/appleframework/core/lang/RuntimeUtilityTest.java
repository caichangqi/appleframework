package com.appleframework.core.lang;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.lang.ref.WeakReference;

import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appleframework.core.RuntimeMode;

/**
 * @author Cruise.Xu
 */
public class RuntimeUtilityTest {
   @BeforeClass
   public static void beforeClass() {
      RuntimeUtility.printDataInformation();
      RuntimeUtility.gcOnExit();
   }

   @AfterClass
   public static void afterClass() {
      RuntimeUtility.gcOnExit();
   }

   @Test
   public void gcWorks() {
      for (int i = 0; i <= 100; i++) {
         Object obj = new Object();
         WeakReference<Object> ref = new WeakReference<Object>(obj);
         obj = null;
         RuntimeUtility.gc();
        ref.get();
      }
   }

   @Test
   public void runtimeModeAvailable() {
      System.setProperty(RuntimeUtility.VAR_RUNTIME_MODE, RuntimeMode.JUNIT.toString());
      assertThat(RuntimeUtility.getRuntimeMode(), is(RuntimeMode.JUNIT));
      assertThat(RuntimeUtility.getRuntimeMode(), is(not(RuntimeMode.PROD)));
      System.setProperty(RuntimeUtility.VAR_RUNTIME_MODE, StringUtils.EMPTY);
   }

   @Test
   public void getJmxExtensionNotEmpty() {
      assertThat(RuntimeUtility.getJmxNameExtension(), is(StringUtils.EMPTY));
      System.setProperty(RuntimeUtility.VAR_RUNTIME_MODE, RuntimeMode.JUNIT.toString());
      assertThat(RuntimeUtility.getJmxNameExtension(), is(not(StringUtils.EMPTY)));
      System.setProperty(RuntimeUtility.VAR_RUNTIME_MODE, StringUtils.EMPTY);
   }

   @Test
   public void isProductionResolved() {
      System.setProperty(RuntimeUtility.VAR_RUNTIME_MODE, RuntimeMode.JUNIT.toString());
      assertThat(RuntimeUtility.isJunit(), is(true));
      assertThat(RuntimeUtility.isProduction(), is(false));
      System.setProperty(RuntimeUtility.VAR_RUNTIME_MODE, RuntimeMode.PROD.toString());
      assertThat(RuntimeUtility.isJunit(), is(false));
      assertThat(RuntimeUtility.isProduction(), is(true));
   }
}

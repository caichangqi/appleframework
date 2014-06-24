package com.appleframework.core.utils;

import org.junit.Test;

/**
 * @author Cruise.Xu
 */
public class AssertUtilityTest {
   @Test(expected = IllegalArgumentException.class)
   public void assertNotNullThrowExceptionWhenViolated() throws Exception {
      AssertUtility.assertNotNull(null, "null passed");
   }

   @Test
   public void assertNotNullDoesNotThrowException() throws Exception {
      AssertUtility.assertNotNull(new Object(), "null passed");
   }

   @Test(expected = IllegalArgumentException.class)
   public void assertTrueThrowsExceptionWhenViolated() throws Exception {
      AssertUtility.assertTrue(false, "actually false");
   }

   @Test
   public void assertTrueDoesNotThrowException() throws Exception {
      AssertUtility.assertTrue(true, "actually false");
   }
}

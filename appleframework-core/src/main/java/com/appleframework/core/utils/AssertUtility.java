package com.appleframework.core.utils;

/**
 * @author Cruise.Xu
 */
public final class AssertUtility {
	
   public static void assertNotNull(final Object obj, final String reference) {
      if (obj == null) {
         throw new IllegalArgumentException(String.format("[Assertion failed error. Expected not null reference %s, but got <NULL>]", reference));
      }
   }

   public static void assertTrue(final boolean result, final String faultDescription) {
      if (!result) {
         throw new IllegalArgumentException(String.format("[Assertion failed error. Expected true value for %s, but got false]", faultDescription));
      }
   }

}

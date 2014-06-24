package com.appleframework.core.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

/**
 * @author Cruise.Xu
 */
public class ReflectionUtilityTest extends Iter<String> {
   @Test
   public void testGetActualGenericType() throws Exception {
      assertThat(ReflectionUtility.getActualGenericType(getClass(), 0).equals(String.class),
               is(true));
   }

   @Test
   public void testBuildMethodSignature() throws Exception {
      String s = ReflectionUtility.buildMethodSignature(ReflectionUtils.findMethod(getClass(),
               "testBuildMethodSignature"));
      assertThat(s, is("testBuildMethodSignature()"));
      s = ReflectionUtility.buildMethodSignature(ReflectionUtils.findMethod(getClass(),
               "internalMethod", new Class[] { String.class, Integer.class }));
      assertThat(s, is("internalMethod(String,Integer)"));
   }

  
   public void internalMethod(String s1, Integer i) {
   }
}

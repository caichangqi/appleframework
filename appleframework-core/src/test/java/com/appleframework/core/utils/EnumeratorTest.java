package com.appleframework.core.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Enumeration;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Cruise.Xu
 */
public class EnumeratorTest {
   Enumerator<String> enumerator;
   @SuppressWarnings("rawtypes")
   Enumeration enumeration;

   @SuppressWarnings({ "unchecked" })
   @Before
   public void before() {
      enumeration = mock(Enumeration.class);
      enumerator = new Enumerator<String>(enumeration);
   }

   @Test
   public void testHasNext() throws Exception {
      when(enumeration.hasMoreElements()).thenReturn(true);
      assertThat(enumerator.hasNext(), is(true));
   }

   @Test
   public void testNext() throws Exception {
      Object obj = new Object();
      when(enumeration.nextElement()).thenReturn(obj);
      assertThat(enumerator.next(), is(obj));
   }

   @Test(expected = UnsupportedOperationException.class)
   public void testRemove() throws Exception {
      enumerator.remove();
   }
}

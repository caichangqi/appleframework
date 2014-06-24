package com.appleframework.core.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Cruise.Xu
 */
public class StringUtilityTest {
   @Test
   public void testToString() throws Exception {
      assertThat("", is(StringUtility.toString(null)));
      assertThat("12", is(StringUtility.toString("12")));
   }

   @Test
   public void testIsEmpty() throws Exception {
      assertThat(true, is(StringUtility.isEmpty(null)));
      assertThat(false, is(StringUtility.isEmpty("1")));
   }

   @Test
   public void testIsWhitespace() throws Exception {
      assertThat(true, is(StringUtility.isWhitespace(null)));
      assertThat(false, is(StringUtility.isWhitespace("1")));
      assertThat(true, is(StringUtility.isWhitespace("")));
      assertThat(true, is(StringUtility.isWhitespace("    ")));
      assertThat(false, is(StringUtility.isWhitespace("   1    ")));
   }

   @Test
   public void testCapitalize() throws Exception {
      assertThat("", is(StringUtility.capitalize("")));
      assertThat("A", is(StringUtility.capitalize("a")));
      assertThat("A", is(StringUtility.capitalize("A")));
      assertThat("Abc", is(StringUtility.capitalize("abc")));
      assertThat("Abc", is(StringUtility.capitalize("Abc")));
   }

   @Test
   public void testGetTokens() {
      String[] tokens = StringUtility.getTokens("  1;2;3   ", ";", true);
      assertThat(tokens.length, is(3));
      assertThat(tokens[0], is("1"));
      assertThat(tokens[1], is("2"));
      assertThat(tokens[2], is("3"));
   }

   @Test
   public void testUnderscore() {
      assertThat("ab_c", is(StringUtility.underscore("abC")));
      assertThat("ab_c", is(StringUtility.underscore("AbC")));
   }
}

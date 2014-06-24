package com.appleframework.core.utils;

import org.junit.Test;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Cruise.Xu
 */
public class CollectionUtilityTest {
   @Test
   public void readPropertiesWork() throws Exception {
   }

   @Test
   public void addAllWorks() throws Exception {
      Collection<Integer> collection = new LinkedHashSet<Integer>();
      collection.add(0);
      Integer[] args = new Integer[2];
      args[0] = 1;
      args[1] = 2;
      CollectionUtility.addAll(collection, args);
      assertThat(collection.size(), is(3));
   }

   @Test
   public void removeAllWorks() throws Exception {
      Collection<Integer> collection = new LinkedHashSet<Integer>();
      collection.add(0);
      collection.add(1);
      collection.add(2);
      Integer[] args = new Integer[2];
      args[0] = 1;
      args[1] = 2;
      CollectionUtility.removeAll(collection, args);
      assertThat(collection.size(), is(1));
   }

   @Test
   public void addAtSpecificIndexWorks() throws Exception {
      List<Integer> collection = new LinkedList<Integer>();
      collection.add(0);
      collection.add(2);
      collection.add(3);
      CollectionUtility.add(collection, 1, 1);
      assertThat(collection.size(), is(4));
      assertThat(collection.get(1), is(1));
      CollectionUtility.add(collection, 10, 9);
      assertThat(collection.size(), is(5));
      assertThat(collection.get(collection.size() - 1), is(9));
   }

   @Test
   public void toBooleanArrayWorks() throws Exception {
      Collection<Boolean> c = new LinkedList<Boolean>();
      c.add(true);
      c.add(false);
      c.add(false);
      c.add(false);
      c.add(true);
      boolean[] values = CollectionUtility.toBooleanArray(c);
      assertThat(values[0], is(true));
      assertThat(values[1], is(false));
      assertThat(values[2], is(false));
      assertThat(values[3], is(false));
      assertThat(values[4], is(true));
   }

   @Test
   public void toIntArrayWorks() throws Exception {
      Collection<Integer> c = new LinkedList<Integer>();
      c.add(1);
      c.add(2);
      c.add(3);
      c.add(4);
      c.add(5);
      int[] values = CollectionUtility.toIntArray(c);
      assertThat(values[0], is(1));
      assertThat(values[1], is(2));
      assertThat(values[2], is(3));
      assertThat(values[3], is(4));
      assertThat(values[4], is(5));
   }

   @Test
   public void toLongArray() throws Exception {
      Collection<Long> c = new LinkedList<Long>();
      c.add(1L);
      c.add(2L);
      c.add(3L);
      c.add(4L);
      c.add(5L);
      long[] values = CollectionUtility.toLongArray(c);
      assertThat(values[0], is(1L));
      assertThat(values[1], is(2L));
      assertThat(values[2], is(3L));
      assertThat(values[3], is(4L));
      assertThat(values[4], is(5L));
   }

   @Test
   public void toFloatArrayWorks() throws Exception {
      Collection<Float> c = new LinkedList<Float>();
      c.add(1.5F);
      c.add(2.5F);
      c.add(3.5F);
      c.add(4.5F);
      c.add(5.5F);
      float[] values = CollectionUtility.toFloatArray(c);
      assertThat(values[0], is(1.5F));
      assertThat(values[1], is(2.5F));
      assertThat(values[2], is(3.5F));
      assertThat(values[3], is(4.5F));
      assertThat(values[4], is(5.5F));
   }

   @Test
   public void toDoubleArrayWorks() throws Exception {
      Collection<Double> c = new LinkedList<Double>();
      c.add(1.5);
      c.add(2.5);
      c.add(3.5);
      c.add(4.5);
      c.add(5.5);
      double[] values = CollectionUtility.toDoubleArray(c);
      assertThat(values[0], is(1.5));
      assertThat(values[1], is(2.5));
      assertThat(values[2], is(3.5));
      assertThat(values[3], is(4.5));
      assertThat(values[4], is(5.5));
   }

   @Test
   public void toByteArrayWorks() throws Exception {
      Collection<Byte> c = new LinkedList<Byte>();
      c.add((byte) 1);
      c.add((byte) 2);
      c.add((byte) 3);
      c.add((byte) 4);
      c.add((byte) 5);
      byte[] values = CollectionUtility.toByteArray(c);
      assertThat(values[0], is((byte) 1));
      assertThat(values[1], is((byte) 2));
      assertThat(values[2], is((byte) 3));
      assertThat(values[3], is((byte) 4));
      assertThat(values[4], is((byte) 5));
   }

   @Test
   public void toShortArrayWorks() throws Exception {
      Collection<Short> c = new LinkedList<Short>();
      c.add((short) 1);
      c.add((short) 2);
      c.add((short) 3);
      c.add((short) 4);
      c.add((short) 5);
      short[] values = CollectionUtility.toShortArray(c);
      assertThat(values[0], is((short) 1));
      assertThat(values[1], is((short) 2));
      assertThat(values[2], is((short) 3));
      assertThat(values[3], is((short) 4));
      assertThat(values[4], is((short) 5));
   }
}

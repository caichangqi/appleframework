package com.appleframework.core.utils;

import org.junit.Ignore;

import java.util.Iterator;

/**
 * @author Cruise.Xu
 */
@Ignore
public class Iter<E> implements Iterator<E> {
   @Override
   public boolean hasNext() {
      return false;
   }

   @Override
   public E next() {
      return null;
   }

   @Override
   public void remove() {
   }
}

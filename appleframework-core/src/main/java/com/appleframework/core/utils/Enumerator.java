package com.appleframework.core.utils;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * bridge between iterator and enumeration.
 * 
 * @author Cruise.Xu
 */
public class Enumerator<E> implements Iterator<E> {
	
   private Enumeration<E> enumeration;

   public Enumerator(Enumeration<E> enumeration) {
      this.enumeration = enumeration;
   }

   @Override
   public boolean hasNext() {
      return enumeration.hasMoreElements();
   }

   @Override
   public E next() {
      return enumeration.nextElement();
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException();
   }
   
}

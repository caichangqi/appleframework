package com.appleframework.core.lang;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * This class represents a sequence of bytes.
 * 
 * @author Santhosh Kumar T
 */
public class ByteSequence {
   private byte buff[];
   private int offset;
   private int length;

   /**
    * Allocates a new {@code ByteSequence} so that it represents the sequence of bytes currently
    * contained in the byte array argument. The contents of the byte array are not copied;
    * 
    * @param buff
    *           value of the bytesequence
    */
   public ByteSequence(byte[] buff) {
      set(buff, 0, buff.length);
   }

   /**
    * Allocates a new {@code ByteSequence} that contains characters from a subarray of the byte
    * array argument. The {@code offset} argument is the index of the first byte of the subarray and
    * the {@code length} argument specifies the length of the subarray. The contents of the subarray
    * are not copied;
    * 
    * @param buff
    *           Array that is the source of bytes
    * @param offset
    *           The initial offset
    * @param length
    *           The length
    * @throws IndexOutOfBoundsException
    *            If the {@code offset} and {@code length} arguments index bytes outside the bounds
    *            of the {@code buff} array
    */
   public ByteSequence(byte[] buff, int offset, int length) {
      set(buff, offset, length);
   }

   /**
    * replaces the internal byte buffer with the given byte array.
    * 
    * @param buff
    *           Array that is the source of bytes
    * @param offset
    *           The initial offset
    * @param length
    *           The length
    */
   public final void set(byte[] buff, int offset, int length) {
      if (offset < 0) {
         throw new IndexOutOfBoundsException("ByteSequence index out of range: " + offset);
      }
      if (length < 0) {
         throw new IndexOutOfBoundsException("ByteSequence index out of range: " + length);
      }
      if (offset > buff.length - length) {
         throw new StringIndexOutOfBoundsException("ByteSequence index out of range: "
                  + (offset + length));
      }
      this.buff = Arrays.copyOf(buff, buff.length);
      this.offset = offset;
      this.length = length;
   }

   public byte byteAt(int index) {
      if (index < 0 || index >= length) {
         throw new IndexOutOfBoundsException(index + " is not in range [0, " + length + ")");
      } else {
         return buff[offset + index];
      }
   }

   /**
    * @return the byte buffer used by this instance
    */
   public byte[] buffer() {
      return Arrays.copyOf(buff, buff.length);
   }

   /**
    * @return the index of first byte in byte buffer
    */
   public int offset() {
      return offset;
   }

   public int length() {
      return length;
   }

   public ByteArrayInputStream asInputStream() {
      return new ByteArrayInputStream(buffer(), offset(), length());
   }

   public String toString(String charset) throws UnsupportedEncodingException {
      return new String(buff, offset, length, charset);
   }

   @Override
   public String toString() {
      return new String(buff, offset, length);
   }
}

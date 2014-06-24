package com.appleframework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifying this annotation means that class is just value object without any logic.
 * <p/>
 * Properties of such class allowed to be public to save developer's time.
 * 
 * @author Cruise.Xu
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ValueObject {
}

package com.appleframework.core.io;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.appleframework.core.io.FileUtility.*;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Cruise.Xu
 */
public class FileUtilityTest {
   private File resource;

   @Before
   public void setUp() throws IOException {
      resource = fromClasspathSource("META-INF/spring/jvmcluster$jmx-common.xml");
   }

   @Test
   public void fileIsReadable() throws Exception {
      ensureFileIsReadable(resource);
   }


   @Test
   public void pathConstructedCorrectly() {
      assertThat(path("db1", "db2"), is(format("db1%sdb2%s", SEPARATOR, SEPARATOR)));
      assertThat(path(format("db1%s", SEPARATOR), "db2"),
               is(format("db1%sdb2%s", SEPARATOR, SEPARATOR)));
      assertThat(path(format("db1%s", SEPARATOR), format("db2", SEPARATOR)),
               is(format("db1%sdb2%s", SEPARATOR, SEPARATOR)));
   }
}

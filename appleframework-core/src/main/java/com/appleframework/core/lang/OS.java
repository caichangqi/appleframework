package com.appleframework.core.lang;

import java.util.Arrays;

/**
 * This enum contains values for each type of OS.
 * <p/>
 * To get the current OS:
 * 
 * <pre>
 * OS myos = OS.{@link #get()};
 * System.out.println(myos);
 * </pre>
 * 
 * To check whether your OS is windows or unix;
 * 
 * <pre>
 * OS myos = OS.get();
 * System.out.println("isWindows: "+myos.{@link #isWindows()});
 * System.out.println("isUnix: "+myos.{@link #isUnix()});
 * </pre>
 * <p/>
 * When your OS is not recognized, {@code OS.get()} returns OS.{@link #OTHER}
 * <p/>
 * There is another usefult method which might be required rarely;
 * 
 * <pre>
 * String osName = System.getProperty("os.name");
 * OS os = OS.{@link #get(String) get}(osName);
 * </pre>
 * 
 * This might be handy, if your app is distributed and and want to find out the os of remote JVM
 * process
 * 
 * @author Santhosh Kumar T
 * @author Cruise.Xu
 */
public enum OS {
   WINDOWS_NT(
            "Windows NT"),
   WINDOWS_95(
            "Windows 95"),
   WINDOWS_98(
            "Windows 98"),
   WINDOWS_2000(
            "Windows 2000"),
   WINDOWS_VISTA(
            "Windows Vista"),
   WINDOWS_7(
            "Windows 7"),
   //
   SOLARIS(
            "Solaris"),
   LINUX(
            "Linux"),
   HP_UX(
            "HP-UX"),
   IBM_AIX(
            "AIX"),
   SGI_IRIX(
            "Irix"),
   SUN_OS(
            "SunOS"),
   COMPAQ_TRU64_UNIX(
            "Digital UNIX"),
   MAC(
            "Mac OS X",
            "Darwin"),
   FREEBSD(
            "freebsd"),
   //
   OS2(
            "OS/2"),
   COMPAQ_OPEN_VMS(
            "OpenVMS"),
   OTHER(
            "");
   private String names[];

   private OS(String... names) {
      this.names = names;
   }

   /**
    * @return names of operation system
    */
   public String[] getNames() {
      return Arrays.copyOf(names, names.length);
   }

   /**
    * @return true if this OS belongs to windows family
    */
   public boolean isWindows() {
      return ordinal() <= WINDOWS_7.ordinal();
   }

   /**
    * @return true if this OS belongs to *nix family
    */
   public boolean isUnix() {
      return ordinal() > WINDOWS_7.ordinal() && ordinal() < OS2.ordinal();
   }

   /*-------------------------------------------------[ Static Methods ]---------------------------------------------------*/

   /**
    * @param operationSytem
    *           name of OS as returned by <code>System.getProperty("os.name")</code>
    * @return OS for the specified {@code osName}
    */
   public static OS get(String operationSytem) {
      String osName = operationSytem;
      osName = osName.toLowerCase();
      for (OS os : values()) {
         for (String name : os.names) {
            if (osName.contains(name.toLowerCase())) {
               return os;
            }
         }
      }
      return null;
   }

   private static OS current;

   /**
    * @return OS on which this JVM is running
    */
   public synchronized static OS get() {
      if (current == null) {
         current = get(System.getProperty("os.name"));
      }
      return current;
   }
}

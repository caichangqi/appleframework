package com.appleframework.core.lock;

import java.util.concurrent.locks.Lock;

/**
 * @author Cruise.Xu
 */
public interface IDistributedLockProvider {
   /**
    * mark that work already done(this would mean that next time there is no need to execute the
    * same job again)
    */
   void markWorkDone();

   /**
    * @return true if class that used this distributed task already performed required work, else
    *         false.
    */
   boolean isWorkDone();

   Lock lockFor(String key);
}

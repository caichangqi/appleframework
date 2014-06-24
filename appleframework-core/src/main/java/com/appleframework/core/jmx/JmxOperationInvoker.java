package com.appleframework.core.jmx;

import com.appleframework.core.log.LogFactory;
import com.appleframework.core.log.Logger;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

import static com.appleframework.core.utils.AssertUtility.assertNotNull;

/**
 * This class allows you to invoke remote jmx operation. This class marked as abstract and
 * implementers will override doInvoke method, all other stuff(opening connection, creating proxy,
 * closing connection) is covered by this class.
 * 
 * @author Cruise.Xu
 */
public abstract class JmxOperationInvoker<T> {
   protected Logger logger = LogFactory.getLogger(getClass());
   private final String url;
   private final String objectName;
   private final Class<T> mBeanInterface;

   public JmxOperationInvoker(String url, String objectName, Class<T> mBeanInterface) {
      assertNotNull(url, "you should specify jmx connection url");
      assertNotNull(objectName, "you should specify object name");
      assertNotNull(mBeanInterface, "you should interface to be proxied");
      //
      this.url = url;
      this.objectName = objectName;
      this.mBeanInterface = mBeanInterface;
   }

   /**
    * client should call this method to invoke remote operation.
    * 
    * @return true if mBean with name {@link this#objectName} i registered, else false.
    * @throws IOException
    *            if the connector client or the connection cannot be made because of a communication
    *            problem.
    * @throws MalformedObjectNameException
    *            string passed as a objectName parameter does not have the right format
    */
   @SuppressWarnings({ "unchecked" })
   public boolean invoke() throws IOException, MalformedObjectNameException {
      boolean mbeanRegistered = true;
      logger.info("connecting to remote JMX server using url = %s", url);
      JMXServiceURL jmxServiceURL = new JMXServiceURL(url);
      JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceURL);
      MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();
      ObjectName mbeanName = new ObjectName(objectName);
      try {
         if (mBeanServerConnection.isRegistered(mbeanName)) {
            Object proxy = JMX
                     .newMBeanProxy(mBeanServerConnection, mbeanName, mBeanInterface, true);
            doInvoke((T) proxy);
         } else {
            logger.warn("mbean %s is not registered", mbeanName);
            mbeanRegistered = false;
         }
      } finally {
         try {
            jmxConnector.close();
         } catch (Exception e) {
            logger.warn("unable to close jmx connection %s", e);
         }
      }
      return mbeanRegistered;
   }

   /**
    * perform actual actions.
    * 
    * @param proxy
    *           mbeanInterface proxy
    */
   protected abstract void doInvoke(T proxy);
}

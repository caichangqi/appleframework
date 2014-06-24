package com.appleframework.starter.container;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.appleframework.config.EnvConfigurer;
import com.appleframework.starter.container.spring.SpringContainer;

/**
 * Main. (API, Static, ThreadSafe)
 * 
 * @author cruise.xu
 */
public class SpringContainerStarter {

    public static final String SHUTDOWN_HOOK_KEY = "shutdown.hook";
    
    private static Logger logger = Logger.getLogger(SpringContainerStarter.class);
    
    private static volatile boolean running = true;

    public static void main(String[] args) {
        try {
        	for (int i = 0; i < args.length; i++) {
				String envArgs = args[i];
				if(envArgs.indexOf("env=") > -1) {
					String[] envs = envArgs.split("=");
					EnvConfigurer.env = envs[1];
					logger.warn("配置项：env=" + EnvConfigurer.env);
				}
			}
            
            final List<Container> containers = new ArrayList<Container>();
            Container springContainer = new SpringContainer();
            containers.add(springContainer);
            
            logger.info("Use container type(" + Arrays.toString(args) + ") to run serivce.");
            
            if ("true".equals(System.getProperty(SHUTDOWN_HOOK_KEY))) {
	            Runtime.getRuntime().addShutdownHook(new Thread() {
	                public void run() {
	                    for (Container container : containers) {
	                        try {
	                            container.stop();
	                            logger.info("Service " + container.getClass().getSimpleName() + " stopped!");
	                        } catch (Throwable t) {
	                            logger.error(t.getMessage(), t);
	                        }
	                        synchronized (SpringContainerStarter.class) {
	                            running = false;
	                            SpringContainerStarter.class.notify();
	                        }
	                    }
	                }
	            });
            }
            
            for (Container container : containers) {
                container.start();
                logger.warn("服务 " + container.getClass().getSimpleName() + " 启动!");
            }
            
            logger.warn(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " 所有服务启动成功!");
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }
        synchronized (SpringContainerStarter.class) {
            while (running) {
                try {
                    SpringContainerStarter.class.wait();
                } catch (Throwable e) {
                	logger.error(e.getMessage(), e);
                }
            }
        }
    }
    
}
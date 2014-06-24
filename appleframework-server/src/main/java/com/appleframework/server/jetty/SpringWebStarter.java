package com.appleframework.server.jetty;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.appleframework.config.EnvConfigurer;

/**
 * springMVC+Jetty的容器
 *
 * @author Cruise.Xu
 */
public class SpringWebStarter {

	private static Logger logger = Logger.getLogger(SpringWebStarter.class);

    public static void main(String[] args) {
    	for (int i = 0; i < args.length; i++) {
			String envArgs = args[i];
			if(envArgs.indexOf("env=") > -1) {
				String[] envs = envArgs.split("=");
				EnvConfigurer.env = envs[1];
				logger.warn("配置项：env=" + EnvConfigurer.env);
			}
		}
        ApplicationContext applicationContext = 
        		new ClassPathXmlApplicationContext("classpath*:META-INF/spring/spring-web-starter.xml");
        WebAppContext webAppContext = applicationContext.getBean("webAppContext", WebAppContext.class);
        logger.info("start jetty web context context= " + webAppContext.getContextPath() + ";" +
        		"resource base=" + webAppContext.getResourceBase());
        try {
            Server server = applicationContext.getBean("jettyServer", Server.class);
            server.start();
            server.join();
            logger.warn("启动成功");
        } catch (Exception e) {
            throw new IllegalStateException("Failed to start jetty server on " + ":" + ", cause: " + e.getMessage(), e);
        }
    }


}
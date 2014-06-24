package com.appleframework.mq.impl;

import java.io.Serializable;
import java.util.List;

import javax.jms.Destination;

import org.apache.log4j.Logger;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

import com.appleframework.mq.JmsMessageGroupSender;
import com.appleframework.mq.creator.JmsByteMessageCreator;
import com.appleframework.mq.creator.JmsObjectMessageCreator;
import com.appleframework.mq.creator.JmsTextMessageCreator;

/**
 * @author xusm
 * 
 */
public class MessageGroupSender implements JmsMessageGroupSender {

	private static Logger logger = Logger.getLogger(MessageGroupSender.class);

	private List<Destination> destinations;
	private List<JmsTemplate> jmsTemplates;

	public void sendObject(Serializable serializable) throws JmsException {

		for (int i = 0; i < destinations.size(); i++) {
			Destination destination = destinations.get(i);
			try {
				this.jmsTemplates.get(i).send(destination, new JmsObjectMessageCreator(serializable));
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	public void sendByte(Serializable serializable) throws JmsException {
		for (int i = 0; i < destinations.size(); i++) {
			Destination destination = destinations.get(i);
			try {
				this.jmsTemplates.get(i).send(destination, new JmsByteMessageCreator(serializable));
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	public void sendText(String message) throws JmsException {
		for (int i = 0; i < destinations.size(); i++) {
			Destination destination = destinations.get(i);
			try {
				this.jmsTemplates.get(i).send(destination, new JmsTextMessageCreator(message));
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	public void setDestinations(List<Destination> destinations) {
		this.destinations = destinations;
	}

	public void setJmsTemplates(List<JmsTemplate> jmsTemplates) {
		this.jmsTemplates = jmsTemplates;
	}

}

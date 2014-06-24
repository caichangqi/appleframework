package com.appleframework.mq.impl;

import java.io.Serializable;

import javax.jms.Destination;

import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

import com.appleframework.mq.JmsMessageSender;
import com.appleframework.mq.creator.JmsByteMessageCreator;
import com.appleframework.mq.creator.JmsObjectMessageCreator;

/**
 * @author xusm
 * 
 */
public class MessageSender implements JmsMessageSender {

	private JmsTemplate jmsTemplate;
	private Destination destination;

	public void sendObject(Serializable serializable) throws JmsException {
		this.jmsTemplate.send(destination, new JmsObjectMessageCreator(serializable));
	}
	
	public void sendByte(Serializable serializable) throws JmsException {
		this.jmsTemplate.send(destination, new JmsByteMessageCreator(serializable));
	}
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

}

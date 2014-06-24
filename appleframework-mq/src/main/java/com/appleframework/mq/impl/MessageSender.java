package com.glsx.cloudframework.mq.impl;

import java.io.Serializable;

import javax.jms.Destination;

import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

import com.glsx.cloudframework.mq.JmsMessageSender;
import com.glsx.cloudframework.mq.creator.JmsByteMessageCreator;
import com.glsx.cloudframework.mq.creator.JmsObjectMessageCreator;
import com.glsx.cloudframework.mq.creator.JmsTextMessageCreator;

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
	
	public void sendText(String message) throws JmsException {
		this.jmsTemplate.send(destination, new JmsTextMessageCreator(message));
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

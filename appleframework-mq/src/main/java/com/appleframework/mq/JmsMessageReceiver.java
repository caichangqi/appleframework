package com.appleframework.mq;

import javax.jms.Message;
import javax.jms.MessageListener;


/**
 * @author xusm
 * 
 */
public abstract class JmsMessageReceiver implements MessageListener {
	
	protected Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void onMessage(Message message) {
		this.message = message;
		processMessage();
	}
	
	public abstract void processMessage();

}

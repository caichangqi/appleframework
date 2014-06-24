package com.appleframework.mq;

import java.io.Serializable;

import org.springframework.jms.JmsException;

/**
 * 队列消息发送接口
 * @author xusm
 *
 */
public interface JmsMessageSender {
	
	/**
	 * 发送消息
	 * @param code
	 */
	public void sendObject(Serializable message) throws JmsException;
		
	/**
	 * 发送消息
	 * @param code
	 */
	public void sendByte(Serializable message) throws JmsException;

	
}
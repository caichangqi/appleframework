package com.appleframework.mq;

import java.io.Serializable;

import org.springframework.jms.JmsException;

/**
 * 发送接口
 * @author xusm
 *
 */
public interface JmsMessageGroupSender {
	
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
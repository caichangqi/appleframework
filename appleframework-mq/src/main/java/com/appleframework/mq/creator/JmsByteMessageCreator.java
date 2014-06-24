package com.appleframework.mq.creator;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

import com.appleframework.mq.util.ByteUtils;

public class JmsByteMessageCreator implements MessageCreator {
	
	private Serializable serializable;

	public JmsByteMessageCreator(Serializable serializable) {
		this.serializable = serializable;
	}
	
	public Message createMessage(Session session) throws JMSException {
		ObjectMessage objectMessage = session.createObjectMessage();
		try {
			byte[] objMessage = ByteUtils.toBytes(serializable);// 字节数组输出流转成字节数组
			objectMessage.setObject(objMessage);// 将字节数组填充到消息中作为消息主体
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectMessage;
	}
}

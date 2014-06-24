package com.appleframework.web.bean;

public class Message {

	public enum Type {
		success,
		warn,
		error
	}

	private Type type; //消息类型
	private String content = "操作成功"; //消息提示
	private String url; //操作跳转页面

	public Message() {

	}
	
	public Message(Type type, String url) {
		this.type = type;
		this.url = url;
	}

	public Message(Type type, String content, String url) {
		this.type = type;
		this.content = content;
		this.url = url;
	}
	
	public Message(Type type, String content, Object... args) {
		this.type = type;
		this.content = content;
	}

	public static Message success(String content, Object... args) {
		return new Message(Type.success, content, args);
	}

	public static Message warn(String content, Object... args) {
		return new Message(Type.warn, content, args);
	}

	public static Message error(String content, Object... args) {
		return new Message(Type.error, content, args);
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return content;
	}

}
package com.appleframework.core.beans;

import com.appleframework.core.annotation.ValueObject;

/**
 * @author Cruise.Xu
 */
@ValueObject
public class BindingAddress {

	private final String address;
	private final int port;
	private final boolean useMulticastProtocol;

	public BindingAddress(String address, int port) {
		this.address = address;
		this.port = port;
		useMulticastProtocol = true;
	}

	public BindingAddress(String address, int port, boolean useMulticastProtocol) {
		this.address = address;
		this.port = port;
		this.useMulticastProtocol = useMulticastProtocol;
	}

	public String address() {
		return address;
	}

	public int port() {
		return port;
	}

	public boolean useMulticastProtocol() {
		return useMulticastProtocol;
	}
}

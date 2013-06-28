package com.adnanonline.ict.net;

public class InternetConnectionState {
	private boolean state = false;

	public InternetConnectionState(boolean state) {
		this.state = state;
	}

	public boolean getState() {
		return this.state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}
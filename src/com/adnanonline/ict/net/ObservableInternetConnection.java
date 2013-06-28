package com.adnanonline.ict.net;

import java.util.*;

public class ObservableInternetConnection extends Observable implements Runnable {
	private static final long CHECK_INTERVAL = 1000; // 30 sec
	private InternetConnectionState state;
        public volatile static boolean keepChecking = true;
	public void run() {
		this.state = InternetConnection.isAvailable();

		this.updateConnectionStatus();
	}

	public void updateConnectionStatus() {
	while(true)	{
            while(keepChecking) {
			InternetConnectionState newState = InternetConnection.isAvailable();
//			if(newState.getState() != this.state.getState()) {
				this.state = newState;
				setChanged();
				notifyObservers(this.state);
//			}
			try {
				Thread.sleep(ObservableInternetConnection.CHECK_INTERVAL);
			} catch(InterruptedException e) {
			}
		}
	}
        }
}
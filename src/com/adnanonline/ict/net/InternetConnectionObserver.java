package com.adnanonline.ict.net;
import com.adnanonline.ict.net.*;
import java.util.Observable;
import java.util.Observer;
public class InternetConnectionObserver implements Observer {
	public InternetConnectionObserver(ObservableInternetConnection internetConnection) {
		internetConnection.addObserver(this);
		new Thread(internetConnection).start();
	}

	public void update(Observable internetConnection, Object state) {
            System.out.println("notified");
		InternetConnectionState connectionState = (InternetConnectionState)state;
		if(connectionState.getState() == true) {
                    ObservableInternetConnection.keepChecking=false;
                    //                    AlarmPlayer alarm = new AlarmPlayer(null);
//                    alarm.start();
			System.out.println("active internet connection detected");
                        
                        
		} else {
			System.out.println("lost internet connection");
		}
	}
}
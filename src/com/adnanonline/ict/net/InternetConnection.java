/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adnanonline.ict.net;

import java.net.*;
import java.io.*;

public class InternetConnection {
	/*	use at least one reliable host, in most cases you will want
 		to use the one you're working with later	*/
	private static final String CHECK_URL = "http://www.t-online.de/";
	public static InternetConnectionState isAvailable() {
		try {
			URL url = new URL(InternetConnection.CHECK_URL);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			Object objData = urlConnection.getContent();
		} catch(UnknownHostException exception) {
			return new InternetConnectionState(false);
		} catch(IOException exception) {
			return new InternetConnectionState(false);
		}
		return new InternetConnectionState(true);
	}
}
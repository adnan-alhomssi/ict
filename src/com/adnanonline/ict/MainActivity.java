package com.adnanonline.ict;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.adnanonline.ict.net.*;
import java.net.*;
import java.text.BreakIterator;
import java.io.*;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	private boolean checking=false;
	private boolean done=false;
	private boolean reseted=false;
	private ProgressBar progbar;
	private Button checkToggle;
	private TextView statusText;
	private long startTime=0;
	private long stopTime=0;
	private static final String CHECK_URL = "http://google.com/";
	private String PREFERENCE_FIRST_RUN = "PREFERENCE_FIRST_RUN";
	private Context appContext ;
	private Activity currActivity;
	private SharedPreferences settings;
	private MediaPlayer mp;
	// Get instance of Vibrator from current Context
	private Vibrator vibrator;
	private Integer mId = 951;
	// This example will cause the phone to vibrate "SOS" in Morse Code
	// In Morse Code, "s" = "dot-dot-dot", "o" = "dash-dash-dash"
	// There are pauses to separate dots/dashes, letters, and words
	// The following numbers represent millisecond lengths
	int dot = 200;      // Length of a Morse Code "dot" in milliseconds
	int dash = 500;     // Length of a Morse Code "dash" in milliseconds
	int short_gap = 200;    // Length of Gap Between dots/dashes
	int medium_gap = 500;   // Length of Gap Between Letters
	int long_gap = 1000;    // Length of Gap Between Words
	long[] pattern = {
	    0,  // Start immediately
	    dot, short_gap, dot, short_gap, dot,    // s
	    medium_gap,
	    dash, short_gap, dash, short_gap, dash, // o
	    medium_gap,
	    dot, short_gap, dot, short_gap, dot,    // s
	    long_gap
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progbar = (ProgressBar) findViewById(R.id.checkingProgress);
		checkToggle = (Button) findViewById(R.id.checkToggle);
		statusText = (TextView) findViewById(R.id.statusText);
		appContext = getApplicationContext();
		currActivity = this;
		settings = PreferenceManager.getDefaultSharedPreferences(appContext);
		vibrator = (Vibrator) getSystemService(appContext.VIBRATOR_SERVICE);
		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
		boolean firstRun = p.getBoolean(PREFERENCE_FIRST_RUN, true);
		if(firstRun)
		{
			System.out.println("First run");
			settings.edit().putBoolean("pref_key_alarm_enable", true).commit();
			settings.edit().putString("pref_key_alarm_tone", "content://settings/system/notification_sound").commit();
		}
		p.edit().putBoolean(PREFERENCE_FIRST_RUN, false).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_settings:
        startActivity(new Intent(this, SettingsActivity.class));
        return true;
        case R.id.menu_about:
        startActivity(new Intent(this, AboutActivity.class));
        return true;
        case R.id.menu_help:
            startActivity(new Intent(this, HelpActivity.class));
        return true;
        default:
        return super.onOptionsItemSelected(item);
        }
    }
	public void resetButton(View v){
		if(done==true)
		{
			checkToggle.setBackgroundDrawable(getResources().getDrawable(R.drawable.check));
			if(settings.getBoolean("pref_key_alarm_enable", false)){
			mp.stop();
			}
			vibrator.cancel();
			startTime=0;
			done=false;
		return;
		}
	}
	public void startChecking(View v){
		if(done==true)
		{
			checkToggle.setBackgroundDrawable(getResources().getDrawable(R.drawable.check));
			if(settings.getBoolean("pref_key_alarm_enable", false)){
				mp.stop();
			}
		    vibrator.cancel();
			startTime=0;
			done=false;
		return;
		}
		if(checking==false)
		{
			progbar.setVisibility(ProgressBar.VISIBLE);
			statusText.setVisibility(TextView.VISIBLE);
			statusText.setText(getResources().getText(R.string.checking));
			checkToggle.setBackgroundDrawable(getResources().getDrawable(R.drawable.stop));
			checking=true;
			startTime = System.currentTimeMillis();
			// Do something long
		    Runnable runnable = new Runnable() {
		      @Override
		      public void run() {
		          try {
		        	  while(checking){
		        		  try {
		        				URL url = new URL(CHECK_URL);
		        				HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
		        				Object objData = urlConnection.getContent();
		        				System.out.println("internet available");
		        				progbar.post(new Runnable() {
		        	                public void run() {
		        	                	if(checking==true){
		        	                    progbar.setVisibility(ProgressBar.INVISIBLE);
		        	                    checkToggle.setBackgroundDrawable(getResources().getDrawable(R.drawable.congrats));
		        	                    stopTime = System.currentTimeMillis();
		        	                    long passed = (stopTime - startTime)/1000;
		        	                    String passed_string;
	        	                    	passed_string = String.format("%d:%02d", passed / 60, passed % 60);
		        	                    statusText.setText("Time waited:	 \n "+passed_string);
				        				startTime=0;
				        				if(settings.getBoolean("pref_key_alarm_enable", false)){
				        				if(
				        						settings.getString("pref_key_alarm_tone",null).equals("content://settings/system/notification_sound") ||
				        						settings.getString("pref_key_alarm_tone",null)=="")
				        				{
				        					System.out.println("Default alert");
				        					mp = MediaPlayer.create(appContext,R.raw.alert);
					        			}
				        				else
				        				{
				        					System.out.println("Custom alert");
				        					Uri alertUri = Uri.parse(settings.getString("pref_key_alarm_tone",null));
				        					mp = MediaPlayer.create(appContext,alertUri );
				        					System.out.println("test"+settings.getString("pref_key_alarm_tone","nullee"));
					        			}
			        					mp.start();
				        				}
				        				if(settings.getBoolean("pref_key_vibrate_enable", false))
				        				{
				        					vibrator.vibrate(pattern,-1);
				        				}
				        				if(settings.getBoolean("pref_key_notification_enable", false))
				        				{
				        				NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				        				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(appContext);

				        				mBuilder.setContentTitle("Back Online")
				        				        .setContentText("Working internet connection has just been detected")
				        				        .setSmallIcon(R.drawable.ic_launcher)
				        				        .setAutoCancel(true);

				        				PendingIntent in = PendingIntent.getActivity(getApplicationContext(), 0, getIntent(), 0);
				        				mBuilder.setContentIntent(in);

				        				mNotificationManager.notify(0, mBuilder.build());
		        	                	}	
		        	                	}
				        				checking=false;
				        				done=true;
		        	                }
		        	            });
		        			} catch(UnknownHostException exception) {
		        				System.out.println("no internet");
		        			} catch(IOException exception) {
		        				System.out.println("no internet");
		        			}
		        	  }
	            	  Thread.sleep(2000);
		          } catch (InterruptedException e) {
		            e.printStackTrace();
		          }
		          
		      }
		    };
		    new Thread(runnable).start();
		}
		else
		{
			progbar.setVisibility(ProgressBar.INVISIBLE);
			checkToggle.setBackgroundDrawable(getResources().getDrawable(R.drawable.check));
			checking=false;
			stopTime = System.currentTimeMillis();
			long passed = (stopTime - startTime)/1000;
            String passed_string;
        	passed_string = String.format("%d:%02d", passed / 60, passed % 60);
            statusText.setText("Time waited:	 \n "+passed_string);
			startTime=0;
			vibrator.cancel();
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)  
    {
         if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
         {
        	 showExitDialog(this, null, null);
            return true;
         }
        return super.onKeyDown(keyCode, event);
    }
	public void showExitDialog(Activity activity, String title, CharSequence message) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	    if (title != null)
	        builder.setTitle(title);
	    if(message != null)
	    builder.setMessage(message);
	    builder.setPositiveButton("Exit", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
	        	finish();
			}
	    });
	            builder.setNegativeButton("Minimize", new AlertDialog.OnClickListener() {
	    			@Override
	    			public void onClick(DialogInterface dialog, int which) {
	    				currActivity.moveTaskToBack(true);
	    			}
	    	    });
	    builder.show();
	}
}

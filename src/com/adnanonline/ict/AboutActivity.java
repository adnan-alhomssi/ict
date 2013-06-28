package com.adnanonline.ict;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;


public class AboutActivity extends Activity {
	private TextView aboutText;
	private ToggleButton languageToggle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		aboutText = (TextView) findViewById(R.id.aboutText);
		languageToggle = (ToggleButton) findViewById(R.id.languageToggle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_about, menu);
		return true;
	}
	public void changeLanguage(View v){
		// Is the toggle on?
	    boolean on = languageToggle.isChecked();
	    
	    if (on) {
	        // set Arabic
			aboutText.setText(getResources().getString(R.string.aboutAr));
	    } else {
	        // set English
			aboutText.setText(getResources().getString(R.string.aboutEn));
	    }
	}
	

}

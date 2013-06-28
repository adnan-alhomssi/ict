package com.adnanonline.ict;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class HelpActivity extends Activity {
	private TextView helpText;
	private ToggleButton languageToggle;
	private Boolean currLang=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		helpText = (TextView) findViewById(R.id.helpText);
		languageToggle = (ToggleButton) findViewById(R.id.languageToggle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_help, menu);
		return true;
	}
	public void changeLanguage(View v){
		// Is the toggle on?
	    boolean on = languageToggle.isChecked();
	    
	    if (on) {
	        // set Arabic
			helpText.setText(getResources().getString(R.string.helpAr));
	    } else {
	        // set English
			helpText.setText(getResources().getString(R.string.helpEn));
	    }
	}
}

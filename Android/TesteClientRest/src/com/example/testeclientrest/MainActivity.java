package com.example.testeclientrest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	TextView txtVwNmLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtVwNmLogin = (TextView) findViewById(R.id.txtViewNmLogin);
		
		Intent intent = getIntent();
		txtVwNmLogin.setText(intent.getCharSequenceExtra("nmLogin"));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

}

package com.globaltec.bino;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import com.example.testeclientrest.R;
import com.globaltec.bino.UTILS.httpUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {

	TextView txtVwNmLogin;
	Button btnEnviaGPS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtVwNmLogin = (TextView) findViewById(R.id.txtViewNmLogin);
		
		Intent intent = getIntent();
		txtVwNmLogin.setText(intent.getCharSequenceExtra("nmLogin"));
		
		btnEnviaGPS = (Button) findViewById(R.id.btnEnviaGPS);
		btnEnviaGPS.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//new conectaWS().execute("http://fleetcontrol-globaltec.rhcloud.com/fleetcontrol/webresources/mensagem?title=leandro4&lat=" + location.getLatitude() + "&lng=" + location.getLongitude());
			}
			
		});
		
		// inicia o LocationManager
		LocationManager locationManager = getLocationManager();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
	}
	
	private LocationManager getLocationManager()	{
		LocationManager	locationManager	= (LocationManager)	getSystemService(Context.LOCATION_SERVICE);
		return locationManager;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onDestroy()	{
		super.onDestroy();
		getLocationManager().removeUpdates(this);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		getLocationManager().removeUpdates(this);
	}

	// INICIO IMPLEMENTACAO DO LocationListener
	@Override
	public void onLocationChanged(Location location) {
		new conectaWS().execute("http://fleetcontrol-globaltec.rhcloud.com/fleetcontrol/webresources/mensagem?title=leandro4&lat=" + location.getLatitude() + "&lng=" + location.getLongitude());		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	// FIM DA IMPLEMENTACAO DO LocationListener

	private class conectaWS extends AsyncTask<String, Void, String> {

		private final String ClassName = "conectaWS";
		
		@Override
		protected String doInBackground(String... url) {
			try {
				return httpUtil.get(url[0]);
			} 
			catch (SocketTimeoutException e) {
				Log.e(ClassName, "Tempo excedido. ", e);
				return null;
			} 
			catch (MalformedURLException e) {
				Log.e(ClassName, "URL invalida. ", e);
				return null;
			} 
			catch (IOException e) {
				Log.e(ClassName, "Erro de E/S. ", e);
				return null;
			}
			catch (Exception e) {
				Log.e(ClassName, "Erro. ", e);
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
	    	
			result = result.trim();
	    	
	    	
			super.onPostExecute(result);
		}
		
	}
}

package com.example.testeclientrest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import com.example.testeclientrest.UTILS.httpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	EditText edTxtNmLogin;
	EditText edTxtSenha;
	Button btnEntrar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	
		edTxtNmLogin = (EditText) findViewById(R.id.editTextNmLogin);
		edTxtSenha = (EditText) findViewById(R.id.editTextSenha);
		
		btnEntrar = (Button) findViewById(R.id.btnEntrar);
		btnEntrar.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.putExtra("nmLogin", edTxtNmLogin.getText());
				intent.putExtra("senha", edTxtSenha.getText());
				
				startActivity(intent);
			}
		});
		
		//new conectaWS().execute(params);
	}

	private class conectaWS extends AsyncTask<String, Void, String> {

		private final String ClassName = "conectaWS";
		
		@Override
		protected String doInBackground(String... url) {
			try {
				return httpUtil.get(url[0]);
			} catch (SocketTimeoutException e) {
				Log.e(ClassName, "Tempo excedido. ", e);
			} catch (MalformedURLException e) {
				Log.e(ClassName, "URL invalida. ", e);
			} catch (IOException e) {
				Log.e(ClassName, "Erro de E/S. ", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
	}
}

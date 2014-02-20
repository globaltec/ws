package com.example.testeclientrest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	}

}

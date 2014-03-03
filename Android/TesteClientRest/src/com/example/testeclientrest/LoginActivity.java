package com.example.testeclientrest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import com.example.testeclientrest.DAL.TesteClientRestContract;
import com.example.testeclientrest.DAO.ConfiguracaoDAO;
import com.example.testeclientrest.DAO.UsuarioDAO;
import com.example.testeclientrest.MODEL.Configuracao;
import com.example.testeclientrest.MODEL.Usuario;
import com.example.testeclientrest.UTILS.httpUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	final String activityName = "LoginActivity";
	Configuracao configuracao;
	ConfiguracaoDAO configuracaoDAO;
	Usuario usuario;
	UsuarioDAO usuarioDAO;
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
				try {
					// verifica se usuario e senha estão preenchidos
					if(edTxtNmLogin.getText().toString().length() > 0) {
						if (edTxtSenha.getText().toString().length() > 0) {
							// usuario e senha estão preenchidos, então atribui-os ao objeto usuario
							usuario = new Usuario();
							usuario.setNmLogin(edTxtNmLogin.getText().toString());
							usuario.setSenha(edTxtSenha.getText().toString());
							try {
								// verifica se usuario e senha existem no banco local
								usuarioDAO = new UsuarioDAO(getApplicationContext());
								if(usuarioDAO.autentica(usuario).contentEquals("OK")) {
									// usuario autenticado no banco local
									Intent intent = new Intent(getApplicationContext(), MainActivity.class);
									intent.putExtra("nmLogin", usuario.getNmLogin());
									intent.putExtra("senha", usuario.getSenha());
									
									startActivity(intent);
								}
								else {
									// usuario não existe no banco local. Tenta autenticação via rede
									// verifica se há conexão disponível
									ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
									NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
									if (networkInfo != null && networkInfo.isConnected()) {
									    // existe conexão, tenta autenticar utilizando-se do webservice
										
										// busca url de conexão no banco
										configuracaoDAO = new ConfiguracaoDAO(getApplicationContext());
										configuracao = new Configuracao();
										configuracao = configuracaoDAO.buscaConfiguracao();
										
										String url = configuracao.getBaseUrl() + configuracao.getContentUrl();
										url = url.replaceAll("#nm_login", edTxtNmLogin.getText().toString());
										url = url.replaceAll("#senha", edTxtSenha.getText().toString());
										
										new conectaWS().execute(url);
										
										
									}
									else {
										Toast toast = Toast.makeText(getApplicationContext(), "Sem conexão de rede. Não é possível autenticar.", Toast.LENGTH_SHORT);
								    	toast.setGravity(Gravity.TOP, 0, 30);
								    	toast.show();
									}
								}
							}
							catch(SQLException e) {
								Log.e(activityName,"Erro no metodo usuarioDAO.autentica", e);
							}
						}
						else {
							Toast toast = Toast.makeText(getApplicationContext(), "Senha não pode estar vazia. ", Toast.LENGTH_SHORT);
					    	toast.setGravity(Gravity.TOP, 0, 30);
					    	toast.show();
						}
					}
					else {
						Toast toast = Toast.makeText(getApplicationContext(), "Usuário não pode estar vazio. ", Toast.LENGTH_SHORT);
				    	toast.setGravity(Gravity.TOP, 0, 30);
				    	toast.show();
					}
				}
				catch(Exception e) {
					Log.e(activityName, "Erro btnEntrar.setOnClickListener onclick.", e);
				}
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
			//Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
	    	//toast.setGravity(Gravity.TOP, 0, 30);
	    	//toast.show();
	    	
			result = result.trim();
	    	if(result.contentEquals("OK")) {
		    	Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.putExtra("nmLogin", edTxtNmLogin.getText());
				intent.putExtra("senha", edTxtSenha.getText());
				startActivity(intent);
	    	}
	    	else {
	    		Toast toast = Toast.makeText(getApplicationContext(), "Usuário e senha incorretos.", Toast.LENGTH_SHORT);
		    	toast.setGravity(Gravity.TOP, 0, 0);
		    	toast.show();
	    	}
	    	
			super.onPostExecute(result);
		}
		
	}
}

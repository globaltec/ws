package com.example.testeclientrest.UTILS;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class httpUtil {

	public static String get(String Purl) throws MalformedURLException, IOException, SocketTimeoutException {
		InputStream inputStream = null;
		//int resposta;
		
		try {
			URL url = new URL(Purl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setReadTimeout(10000 /* milliseconds */);
	        con.setConnectTimeout(15000 /* milliseconds */);
	        con.setRequestMethod("GET");
	        con.setDoInput(true);
	        // realiza a conexao
	        con.connect();
	        // pega o codigo de resposta
	        //resposta = con.getResponseCode();
	        inputStream = con.getInputStream();
	        // converte inputStream em string
	        return convertInputSteamIntoString(inputStream);
		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		
	}
	
	private static String convertInputSteamIntoString(InputStream inputStream) throws IOException {
		char[] buffer;
		int length = 0;
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		// calcula o tamanho
		//while (inputStream.read() != -1) {
		//	length ++;
		//}
		length = 10;
		
		buffer = new char[length + 1];
		
		reader.read(buffer);
		
		return new String(buffer);
		
	}
}

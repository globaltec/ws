package com.example.testeclientrest.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.testeclientrest.DAL.ConexaoBanco;
import com.example.testeclientrest.DAL.TesteClientRestContract;
import com.example.testeclientrest.MODEL.Configuracao;

public class ConfiguracaoDAO {
	private ConexaoBanco conexaoBanco;
	private SQLiteDatabase db;
	
	public ConfiguracaoDAO(Context context) throws Exception, SQLException {
		conexaoBanco = new ConexaoBanco(context);
	}
	
	public void incluir(String Pbase_url, String Pcontent_url, String Pposts_url) throws Exception, SQLException {
		// se banco estiver aberto, fecha-o
		if (db != null && db.isOpen()) {
			db.close();
		}
		// abre banco para leitura
		db = conexaoBanco.getWritableDatabase();
		
		ContentValues insertIntoValues = new ContentValues();
		insertIntoValues.put(TesteClientRestContract.ConfiguracaoCONS.COLUMN_BASE_URL, Pbase_url);
		insertIntoValues.put(TesteClientRestContract.ConfiguracaoCONS.COLUMN_CONTENT_URL, Pcontent_url);
		insertIntoValues.put(TesteClientRestContract.ConfiguracaoCONS.COLUMN_POSTS_URL, Pposts_url);
		
		// insere valores no banco
		db.insertOrThrow(
		  TesteClientRestContract.ConfiguracaoCONS.TABLE_NAME,
		  "null",
		  insertIntoValues
		);
		
		db.close();
	}
	
	private void alterar(String Pbase_url, String Pcontent_url, String Pposts_url) throws Exception, SQLException {
		// se banco estiver aberto, fecha-o
		if (db != null && db.isOpen()) {
			db.close();
		}
		// abre banco para leitura
		db = conexaoBanco.getReadableDatabase();
		
		// atribui valores a serem alterados
		ContentValues updateSetValues = new ContentValues();
		updateSetValues.put(TesteClientRestContract.ConfiguracaoCONS.COLUMN_BASE_URL, Pbase_url);
		updateSetValues.put(TesteClientRestContract.ConfiguracaoCONS.COLUMN_CONTENT_URL, Pcontent_url);
		updateSetValues.put(TesteClientRestContract.ConfiguracaoCONS.COLUMN_POSTS_URL, Pposts_url);
		
		// altera valores no banco
		db.update(
		  TesteClientRestContract.UsuarioCONS.TABLE_NAME, 
	      updateSetValues, 
	      null, 
	      null
	    );
		
		db.close();
	}
	
	public Configuracao buscaConfiguracao() {
		Configuracao configuracao = new Configuracao();
		
		// especifica cabeçalho do select
		String selectHeader[] = {
				TesteClientRestContract.ConfiguracaoCONS.COLUMN_BASE_URL,
				TesteClientRestContract.ConfiguracaoCONS.COLUMN_CONTENT_URL,
				TesteClientRestContract.ConfiguracaoCONS.COLUMN_POSTS_URL
		};
		
		// se banco estiver aberto, fecha-o
		if (db != null && db.isOpen()) {
			db.close();
		}
		db = conexaoBanco.getReadableDatabase();
		
		// faz consulta no banco
		Cursor c = db.query(
				TesteClientRestContract.ConfiguracaoCONS.TABLE_NAME,  // the table to query
			    selectHeader,                                    // the columns to return
			    null,                                            // the columns for the WHERE clause
			    null,                                            // the values for the WHERE clause
			    null,                                            // group the rows
			    null,                                            // filter by row groups
			    null                                             // the sort order
		);
		
		if(c != null && c.moveToFirst()) {
			// pega id_usuario retornado
			configuracao.setBaseUrl(c.getString(c.getColumnIndex(TesteClientRestContract.ConfiguracaoCONS.COLUMN_BASE_URL)));
			configuracao.setContentUrl(c.getString(c.getColumnIndex(TesteClientRestContract.ConfiguracaoCONS.COLUMN_CONTENT_URL)));
			configuracao.setPostsUrl(c.getString(c.getColumnIndex(TesteClientRestContract.ConfiguracaoCONS.COLUMN_POSTS_URL)));
			
			c.close();
			
		}
		else {
			configuracao.setBaseUrl(null);
			configuracao.setContentUrl(null);
			configuracao.setPostsUrl(null);
		}
		
		db.close();
		return configuracao;
	}
	
	public void salvar(String Pbase_url, String Pcontent_url, String Pposts_url) throws Exception, SQLException {
		alterar(Pbase_url, Pcontent_url, Pposts_url);
	}
	
	public void salvarBaseURL(String Pbase_url) throws Exception, SQLException {
		Configuracao configuracao = new Configuracao();
		
		// busca valores já salvos
		configuracao = buscaConfiguracao();
		// salva valores na tabela CONFIGURACAO
		alterar(Pbase_url, configuracao.getContentUrl(), configuracao.getPostsUrl());
	}
	
	public void salvarContentURL(String Pcontent_url) throws Exception, SQLException {
		Configuracao configuracao = new Configuracao();
		
		// busca valores já salvos
		configuracao = buscaConfiguracao();
		// salva valores na tabela CONFIGURACAO
		alterar(configuracao.getBaseUrl(), Pcontent_url, configuracao.getPostsUrl());
	}
	
	public void salvarPostsURL(String Pposts_url) throws Exception, SQLException {
		Configuracao configuracao = new Configuracao();
		
		// busca valores já salvos
		configuracao = buscaConfiguracao();
		// salva valores na tabela CONFIGURACAO
		alterar(configuracao.getBaseUrl(), configuracao.getContentUrl(), Pposts_url);
	}
}


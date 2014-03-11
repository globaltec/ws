package com.globaltec.bino.DAL;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ConexaoBanco extends SQLiteOpenHelper {

	private final String ClassName = "ConexaoBanco";
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TesteClientRest.db";

    public ConexaoBanco(Context context) throws Exception, SQLException {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQL_CREATE_ENTRIES);
    	try {
    	  criaTabelas(db);
    	  cadastroInicial(db);
    	}
    	catch(SQLException e1) {
    		Log.e(ClassName, "Erro de sql. ", e1);
    	}
    	catch(Exception e2) {
    		Log.e(ClassName, "Erro geral. ", e2);
    	}
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	try {
    		apagaTabelas(db);
    		criaTabelas(db);
      	    cadastroInicial(db);
	    }
		catch(SQLException e1) {
			Log.e(ClassName, "Erro de sql. ", e1);
		}
		catch(Exception e2) {
			Log.e(ClassName, "Erro geral. ", e2);
		}
    }
    
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade(db, oldVersion, newVersion);
    	// TODO criar procedimentos para fazer downgrade do banco
    	// se preciso fazer backup dos dados para depois subí-los novamente
    }
    
    private void criaTabelas(SQLiteDatabase db) throws SQLException, Exception {
    	// habilita as foreign keys
    	db.execSQL("PRAGMA foreign_keys = ON");
    	
    	// tabela USUARIO
    	db.execSQL(
    			"CREATE TABLE " + TesteClientRestContract.UsuarioCONS.TABLE_NAME + " (" +
    			TesteClientRestContract.UsuarioCONS.COLUMN_ID_USUARIO + " INTEGER PRIMARY KEY, " +
    			TesteClientRestContract.UsuarioCONS.COLUMN_NM_LOGIN + " TEXT NOT NULL UNIQUE, " +
    			TesteClientRestContract.UsuarioCONS.COLUMN_SENHA + " TEXT, " +
    			TesteClientRestContract.UsuarioCONS.COLUMN_DT_INCLUSAO + " TEXT NOT NULL, " +
    			TesteClientRestContract.UsuarioCONS.COLUMN_DT_ALTERACAO + " TEXT )"
    	);
    	// tabela CONFIGURACAO
    	db.execSQL(
    			"CREATE TABLE " + TesteClientRestContract.ConfiguracaoCONS.TABLE_NAME + " (" +
    	        TesteClientRestContract.ConfiguracaoCONS.COLUMN_BASE_URL + " TEXT, " +
    			TesteClientRestContract.ConfiguracaoCONS.COLUMN_CONTENT_URL + " TEXT, " +
    	        TesteClientRestContract.ConfiguracaoCONS.COLUMN_POSTS_URL + " TEXT )" 
    	);
    }
    
    private void apagaTabelas(SQLiteDatabase db) throws SQLException, Exception {
    	db.execSQL("DROP TABLE IF EXISTS " + TesteClientRestContract.UsuarioCONS.TABLE_NAME);
    	db.execSQL("DROP TABLE IF EXISTS " + TesteClientRestContract.ConfiguracaoCONS.TABLE_NAME);
    }
    
    private void cadastroInicial(SQLiteDatabase db) throws SQLException, Exception {
    	
    	String dt_inicio;
    	String dt_fim;
    	String dt_inclusao;
    	long id_usuario;
    	
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	SimpleDateFormat sdf_ano= new SimpleDateFormat("yyyy");
    	
    	
    	// USUARIO
    	
    	// pega a data de inclusao
		dt_inclusao = "to_date('dd/mm/yyyy hh24:mi:ss', '" + sdf.format(new Date()) + "')";    	
		// atribui valores a serem inseridos
		ContentValues insertIntoValues = new ContentValues();
		insertIntoValues.put(TesteClientRestContract.UsuarioCONS.COLUMN_NM_LOGIN, "leandro.demartini@gmail.com");
		insertIntoValues.put(TesteClientRestContract.UsuarioCONS.COLUMN_SENHA, "teste");
		insertIntoValues.put(TesteClientRestContract.UsuarioCONS.COLUMN_DT_INCLUSAO, dt_inclusao);
		// insere valores no banco
		id_usuario = db.insertOrThrow(
		  TesteClientRestContract.UsuarioCONS.TABLE_NAME,
		  "null",
		  insertIntoValues
		);
		
    	// CONFIGURACAO
		insertIntoValues = new ContentValues();
		insertIntoValues.put(TesteClientRestContract.ConfiguracaoCONS.COLUMN_BASE_URL, "http://fleet-caoctaviano.rhcloud.com");
		insertIntoValues.put(TesteClientRestContract.ConfiguracaoCONS.COLUMN_CONTENT_URL, "/fleet/webresources/usuario?usr=#nm_login&pwd=#senha");
		insertIntoValues.put(TesteClientRestContract.ConfiguracaoCONS.COLUMN_POSTS_URL, "");
		// insere valores no banco
		db.insertOrThrow(
		  TesteClientRestContract.ConfiguracaoCONS.TABLE_NAME,
		  "null",
		  insertIntoValues
		);
    }
    
}

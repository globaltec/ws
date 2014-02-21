package com.example.testeclientrest.DAO;


import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.testeclientrest.DAL.ConexaoBanco;
import com.example.testeclientrest.DAL.ConexaoBanco;
import com.example.testeclientrest.DAL.TesteClientRestContract;
import com.example.testeclientrest.MODEL.Usuario;

public class UsuarioDAO {

	private ConexaoBanco conexaoBanco;
	private SQLiteDatabase db;
	
	public UsuarioDAO(Context context) throws Exception, SQLException {
		conexaoBanco = new ConexaoBanco(context);
	}
	
	private long incluir(String Pnm_login, String Psenha) throws Exception, SQLException {
		long id_usuario;
		String dt_inclusao;
		
		// se banco estiver aberto, fecha-o
		if (db != null && db.isOpen()) {
			db.close();
		}
		// abre banco para leitura
		db = conexaoBanco.getWritableDatabase();
		
		// pega a data de inclusao
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		dt_inclusao = "to_date('dd/mm/yyyy hh24:mi:ss', '" + sdf.format(new Date()) + "')";
		    	
		// atribui valores a serem inseridos
		ContentValues insertIntoValues = new ContentValues();
		insertIntoValues.put(TesteClientRestContract.UsuarioCONS.COLUMN_NM_LOGIN, Pnm_login);
		insertIntoValues.put(TesteClientRestContract.UsuarioCONS.COLUMN_SENHA, Psenha);
		insertIntoValues.put(TesteClientRestContract.UsuarioCONS.COLUMN_DT_INCLUSAO, dt_inclusao);
		
		// insere valores no banco
		id_usuario = db.insertOrThrow(
		  TesteClientRestContract.UsuarioCONS.TABLE_NAME,
		  "null",
		  insertIntoValues
		);
		
		db.close();
		
		return id_usuario;
	}
	
	private void excluir(long id_usuario) throws Exception, SQLException {
		// se banco estiver aberto, fecha-o
		if (db != null && db.isOpen()) {
			db.close();
		}
		// abre banco para leitura
		db = conexaoBanco.getWritableDatabase();
		
		// cláusulas where
		String deleteWhereClauses = TesteClientRestContract.UsuarioCONS.COLUMN_NM_LOGIN + " = ?";
		
		// valures a atribuir nas clausulas where
		String[] deleteWhereValues = {
				String.valueOf(id_usuario)
		};
		
		// apaga valores do banco
		db.delete(
		  TesteClientRestContract.UsuarioCONS.TABLE_NAME, 
		  deleteWhereClauses,
		  deleteWhereValues
		);
		
		db.close();
	}
	
	private void alterar(long id_usuario, String Psenha) throws Exception, SQLException {
		// se banco estiver aberto, fecha-o
		if (db != null && db.isOpen()) {
			db.close();
		}
		// abre banco para leitura
		db = conexaoBanco.getReadableDatabase();
		
		// pega a data da alteracao
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dt_alteracao = "to_date('dd/mm/yyyy hh24:mi:ss', '" + sdf.format(new Date()) + "')";
		
		// atribui valores a serem alterados
		ContentValues updateSetValues = new ContentValues();
		updateSetValues.put(TesteClientRestContract.UsuarioCONS.COLUMN_SENHA, Psenha);
		updateSetValues.put(TesteClientRestContract.UsuarioCONS.COLUMN_DT_ALTERACAO, dt_alteracao);
		
		// cláusulas where
		String updateWhereClauses = TesteClientRestContract.UsuarioCONS.COLUMN_ID_USUARIO + " = ?";
		
		// valures a atribuir nas clausulas where
		String[] updateWhereValues = {
				String.valueOf(id_usuario)
		};
		
		// altera valores no banco
		db.update(
		  TesteClientRestContract.UsuarioCONS.TABLE_NAME, 
	      updateSetValues, 
	      updateWhereClauses, 
	      updateWhereValues
	    );
		
	}
	
	public void salvar(Usuario usuario) throws Exception, SQLException {
		long id_usuario;
		// verifica se usuário já não está cadastrado
		
		// especifica cabeçalho do select
		String selectHeader[] = {
				TesteClientRestContract.UsuarioCONS.COLUMN_ID_USUARIO
		};
		
		// especifica cláusula where
		String selectWhereClauses = TesteClientRestContract.UsuarioCONS.COLUMN_NM_LOGIN + " = ?";
		
		// valores a atribuir nas clausulas where
		String selectWhereValues[] = {
			usuario.getNmLogin()	
		};
		
		// se banco estiver aberto, fecha-o
		if (db != null && db.isOpen()) {
			db.close();
		}
		db = conexaoBanco.getReadableDatabase();
		// faz consulta no banco
		Cursor c = db.query(
				TesteClientRestContract.UsuarioCONS.TABLE_NAME,  // the table to query
			    selectHeader,                              // the columns to return
			    selectWhereClauses,                        // the columns for the WHERE clause
			    selectWhereValues,                         // the values for the WHERE clause
			    null,                                      // group the rows
			    null,                                      // filter by row groups
			    null                                       // the sort order
		);
		db.close();
		
		if(c != null && c.moveToFirst()) { // se usuario existe, altera
			// pega id_usuario retornado
			id_usuario = c.getLong(c.getColumnIndex(TesteClientRestContract.UsuarioCONS.COLUMN_ID_USUARIO));
			
			alterar(id_usuario, usuario.getSenha());
			
			c.close();
		}
		else { // se usuario nao existe, insere
			incluir(usuario.getNmLogin(), usuario.getSenha());
		}
	}
	
	public void apagar(Usuario usuario) throws Exception, SQLException {
		long id_usuario;
		// verifica se usuário já não está cadastrado
		
		// especifica cabeçalho do select
		String selectHeader[] = {
				TesteClientRestContract.UsuarioCONS.COLUMN_ID_USUARIO
		};
		
		// especifica cláusula where
		String selectWhereClauses = TesteClientRestContract.UsuarioCONS.COLUMN_NM_LOGIN + " = ?";
		
		// valores a atribuir nas clausulas where
		String selectWhereValues[] = {
			usuario.getNmLogin()	
		};
		
		// se banco estiver aberto, fecha-o
		if (db != null && db.isOpen()) {
			db.close();
		}
		db = conexaoBanco.getReadableDatabase();
		
		// faz consulta no banco
		Cursor c = db.query(
				TesteClientRestContract.UsuarioCONS.TABLE_NAME,  // the table to query
			    selectHeader,                              // the columns to return
			    selectWhereClauses,                        // the columns for the WHERE clause
			    selectWhereValues,                         // the values for the WHERE clause
			    null,                                      // group the rows
			    null,                                      // filter by row groups
			    null                                       // the sort order
		);
		db.close();
		
		if(c != null && c.moveToFirst()) { // se usuario existe, apaga
			// pega id_usuario retornado
			id_usuario = c.getLong(c.getColumnIndex(TesteClientRestContract.UsuarioCONS.COLUMN_ID_USUARIO));
			
			excluir(id_usuario);
			
			c.close();
		}
	}
}

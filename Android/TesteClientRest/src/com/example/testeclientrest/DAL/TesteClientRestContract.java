package com.example.testeclientrest.DAL;

public class TesteClientRestContract {

	public TesteClientRestContract() {
		
	}
	
	// constantes da tabela USUARIO
	public static abstract class UsuarioCONS {
	    public static final String TABLE_NAME = "USUARIO";
	    public static final String COLUMN_ID_USUARIO = "id_usuario";
	    public static final String COLUMN_NM_LOGIN = "nm_login";
	    public static final String COLUMN_SENHA = "senha";
	    public static final String COLUMN_DT_INCLUSAO = "dt_inclusao";
	    public static final String COLUMN_DT_ALTERACAO = "dt_alteracao";
	}
	
	// constantes da tabela CONFIGURACAO
	public static abstract class ConfiguracaoCONS {
		public static final String TABLE_NAME = "CONFIGURACAO";
		public static final String COLUMN_BASE_URL = "base_url";
		public static final String COLUMN_CONTENT_URL = "content_url";
		public static final String COLUMN_POSTS_URL = "posts_url";
	}
}

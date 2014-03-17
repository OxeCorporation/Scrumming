package br.com.scrumming.web.infra;

public final class ConstantesService {
	private ConstantesService(){}
	
	public final class Usuario{
		private Usuario(){}
		
		public static final String OBTER_USUARIO_LOGIN = "/service/usuario/login/{login}/{senha}";
		public static final String SALVAR_USUARIO = "/service/usuario/usu";
	}
}

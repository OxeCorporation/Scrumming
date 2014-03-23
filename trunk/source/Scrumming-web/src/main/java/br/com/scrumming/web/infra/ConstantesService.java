package br.com.scrumming.web.infra;

public final class ConstantesService {
	private ConstantesService(){}
	
	public final class Usuario{
		private Usuario(){}
		
		public static final String OBTER_USUARIO_LOGIN = "/usuario/login/{login}/{senha}";
		public static final String SALVAR_USUARIO = "/usuario/usu";
	}
	
	public final class Tarefa{
		
		public static final String URL_SALVAR = "http://localhost:8080/Scrumming/service/tarefa/{tarefa}";
		public static final String URL_CONSULTAR_POR_ITEM_BACKLOG = "http://localhost:8080/Scrumming/service/tarefa/list/{itemBacklogID}";
		public static final String URL_CONSULTAR = "http://localhost:8080/Scrumming/service/tarefa/{tarefaID}";
		public static final String URL_REMOVER = "http://localhost:8080/Scrumming/service/tarefa/{tarefa}";
	}
	
	public final class Sprint {
		public static final String URL_SALVAR = "/sprint/save";
		public static final String URL_CONSULTAR_SPRINT_DTO = "/sprint/{sprintID}";
		public static final String URL_CONSULTAR_POR_PROJETO = "/sprint/list/{projetoID}";
		public static final String URL_CONSULTAR_ITEMS_TAREFAS = "/sprint/itensTarefas";
		public static final String URL_FECHAR_SPRINT = "/sprint/close";
	}
	
	public final class ItemBacklog{
		public static final String URL_SALVAR = "/itemBacklog/{itemBacklog}";
		public static final String URL_CONSULTAR_POR_PROJETO = "/itemBacklog/list/{projetoID}";
		public static final String URL_CANCELAR = "/itemBacklog/{item}";
		public static final String URL_CONSLTAR_POR_ID = "/itemBacklog/{itemID}";
	}
	
	public final class Projeto{
		public static final String URL_SALVAR_PROJETO = "/projeto/{projetoDTO}";
		public static final String URL_DELETE_PROJETO = "/projeto/deleteProjeto/{projetoID}";
		public static final String URL_CANCELAR_PROJETO = "/projeto/{projetoID}";
		public static final String URL_CONSULTAR_POR_EMPRESA = "/projeto/list/{empresaID}";
	}
	public final class UsuarioEmpresa{
		public static final String URL_CONSULTAR_EMPRESAS_POR_USUARIO = "/usuario_empresa/usuario/{usuarioID}";
		public static final String URL_CONSULTAR_USUARIOS_POR_EMPRESA = "/usuario_empresa/empresa/{empresaID}";
	}
}

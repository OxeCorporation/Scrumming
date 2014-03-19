package br.com.scrumming.web.infra;

public final class ConstantesService {
	private ConstantesService(){}
	
	public final class Usuario{
		private Usuario(){}
		
		public static final String OBTER_USUARIO_LOGIN = "/service/usuario/login/{login}/{senha}";
		public static final String SALVAR_USUARIO = "/service/usuario/usu";
	}
	
	public final class Tarefa{
		
		public static final String URL_SALVAR = "http://localhost:8080/Scrumming/service/tarefa/{tarefa}";
		public static final String URL_CONSULTAR_POR_ITEM_BACKLOG = "http://localhost:8080/Scrumming/service/tarefa/list/{itemBacklogID}";
		public static final String URL_CONSULTAR = "http://localhost:8080/Scrumming/service/tarefa/{tarefaID}";
		public static final String URL_REMOVER = "http://localhost:8080/Scrumming/service/tarefa/{tarefa}";
	}
	
	public final class Sprint {
		public static final String URL_SALVAR = "http://localhost:8080/Scrumming/service/sprint/save";
		public static final String URL_CONSULTAR_SPRINT_DTO = "http://localhost:8080/Scrumming/service/sprint/{sprintID}";
		public static final String URL_CONSULTAR_POR_PROJETO = "http://localhost:8080/Scrumming/service/sprint/list/{projetoID}";
		public static final String URL_CONSULTAR_ITEMS_TAREFAS = "http://localhost:8080/Scrumming/service/sprint/itensTarefas";
		public static final String URL_FECHAR_SPRINT = "http://localhost:8080/Scrumming/service/sprint/close";
	}
	
	public final class ItemBacklog{
		public static final String URL_SALVAR = "http://localhost:8080/Scrumming/service/itemBacklog/{itemBacklog}";
		public static final String URL_CONSULTAR_POR_PROJETO = "http://localhost:8080/Scrumming/service/itemBacklog/list/{projetoID}";
		public static final String URL_CANCELAR = "http://localhost:8080/Scrumming/service/itemBacklog/{item}";
		public static final String URL_CONSLTAR_POR_ID = "http://localhost:8080/Scrumming/service/itemBacklog/{itemID}";
	}
}

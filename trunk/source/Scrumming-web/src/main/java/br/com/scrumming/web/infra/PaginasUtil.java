package br.com.scrumming.web.infra;

public final class PaginasUtil {
	
    public final class Geral {
        public static final String LOGIN_PAGE = "/paginas/login.xhtml";
        public static final String BENVINDO_PAGE = "/paginas/bemvindo.xhtml";
    }
    public final class Usuario{
    	public static final String CADASTRO_USUARIO_PAGE = "/paginas/usuario/cadastroUsuarioPage.xhtml";
    }
    
    public final class ItemBacklog{
    	public static final String ITEM_BACKLOG_PAGE = "/paginas/itembacklog/itembacklog.xhtml";
    	public static final String ITEM_BACKLOG_DETAIL_PAGE= "/paginas/itembacklog/itembacklogdetailPage.xhtml";
    }
    
    public final class Sprint {
    	public static final String SPRINT_PAGE = "/paginas/sprint/sprint.xhtml";
    	public static final String SPRINT_DETAIL_PAGE = "/paginas/sprint/sprintdetail.xhtml";
    	public static final String SPRINT_CADASTRO_PAGE = "/paginas/sprint/sprintcadastro.xhtml";
    }
    
    public final class Projeto{
    	public static final String PROJETO_PAGE = "/paginas/projeto/projeto.xhtml";
    	public static final String PROJETO_DETAIL_PAGE = "/paginas/projeto/projetodetail.xhtml";
    	public static final String PROJETO_CADASTRO_PAGE = "/paginas/projeto/projetocadastro.xhtml";
    }
    
    public final class Tarefa{
    	public static final String SAVE_PAGE = "/paginas/tarefa/tarefaSave.xhtml";
    }
}

package br.com.scrumming.web.infra;


public final class ConstantesService {
	private ConstantesService(){}
	
	public final class Usuario{
		private Usuario(){}
		public static final String OBTER_USUARIO_LOGIN = "/usuario/login/{login}/{senha}";
		public static final String SALVAR_USUARIO = "/usuario/usu";
		public static final String SALVAR_USUARIO_EMP = "/usuario/usu/{empresaID}";
		public static final String DESATIVAR_USUARIO = "/usuario/desativar/{usuarioID}/{empresaID}";
		public static final String ATIVAR_USUARIO = "/usuario/ativar/{usuarioID}/{empresaID}";
		public static final String URL_CONSULTAR = "/usuario/id/{usuarioID}";
	}
	
	public final class Tarefa{
		private Tarefa(){}
		public static final String URL_SALVAR = "/tarefa/save/{itemBacklogManagerID}";
		public static final String URL_CONSULTAR_POR_ITEM_BACKLOG = "/tarefa/list/{itemBacklogID}";
		public static final String URL_CONSULTAR_POR_ITEM_BACKLOG_IH_SITUACAO = "/tarefa/list/{itemBacklogID}/{situacao}";
		public static final String URL_CONSULTAR_POR_ITEM_BACKLOG_IH_USUARIO_LOGADO = "/tarefa/lista/{itemBacklogID}/{usuarioLogadoID}";
		public static final String URL_CONSULTAR = "/tarefa/{tarefaID}";
		public static final String URL_REMOVER = "/tarefa/remove/";
		public static final String URL_ATRIBUIR_PARA = "/tarefa/atribuirpara/{itemBacklogID}/{usuarioID}";
		public static final String URI_INSERT_OR_UPDATE = "/tarefa/insertOrUpdate";
		public static final String URI_ATUALIZAR_TAREFA = "/tarefa/update/{tarefaID}/{situacaoTarefaEnum}";
	}
	
	public final class Sprint {
		public static final String URL_SALVAR = "/sprint/save";
		public static final String URL_CONSULTAR_SPRINT_DTO = "/sprint/{sprintID}";
		public static final String URL_CONSULTAR_POR_PROJETO = "/sprint/list/{projetoID}";
		public static final String URL_CONSULTAR_ITENS_DISPONIVEIS = "/sprint/list/disponiveis/{projetoID}";
		public static final String URL_CONSULTAR_SPRINT_BACKLOG = "/sprint/sprintBacklog/list/{sprintID}/{usuarioLogadoID}";
		public static final String URL_FECHAR_SPRINT = "/sprint/close";
		public static final String URI_CONSULTAR_TEREFAS = "/sprint/sprintBacklog/tarefas/{sprintID}";
		public static final String URL_TOTAL_DE_HORAS_ESTIMADAS = "/sprint/totalDeHorasEstimadas/{sprintID}";
		public static final String URL_CONSULTAR_SPRINT = "/sprint/selecionada/{sprintID}";
		public static final String URL_TOTAL_DE_HORAS_RESTANTES_POR_DATA = "/sprint/totalDeHorasRestantesPorData/{sprintID}/{data}";
	}
	
	public final class ItemBacklog{
		public static final String URL_SALVAR = "/itemBacklog/save";
		public static final String URL_CONSULTAR_POR_PROJETO = "/itemBacklog/list/{projetoID}";
		public static final String URL_CANCELAR = "/itemBacklog/cancel";
		public static final String URL_CONSLTAR_POR_ID = "/itemBacklog/{itemID}";
	}
	
	public final class Projeto{
		public static final String URL_SALVAR_PROJETO = "/projeto/save";
		public static final String URL_CONCLUIR_PROJETO = "/projeto/concluir";
		public static final String URL_DELETE_PROJETO = "/projeto/deleteProjeto/{projetoID}";
		public static final String URL_CANCELAR_PROJETO = "/projeto/{projetoID}";
		public static final String URL_CONSULTAR_PROJETO_DTO = "/projeto/projetodto/{projetoID}";
		public static final String URL_CONSULTAR_POR_EMPRESA = "/projeto/list/{empresaID}";
		public static final String URL_CONSULTAR_ATIVOS_POR_EMPRESA = "/projeto/list/ativos/{empresaID}";
		public static final String URL_CONSULTAR_CONCLUIDOS_POR_EMPRESA = "/projeto/list/concluidos/{empresaID}";
		public static final String URL_CONSULTAR_POR_USURIO_EMPRESA = "/usuario_empresa/empresa/{empresaID}";
		public static final String URL_CONSULTAR_POR_USURIO_EMPRESA_NOTPROJETO = "/projeto/listusuario/{projetoID}/{empresaID}";

	}
	public final class UsuarioEmpresa{
		public static final String URL_CONSULTAR_EMPRESAS_POR_USUARIO = "/usuario_empresa/usuario/{usuarioID}";
		public static final String URL_CONSULTAR_USUARIOS_POR_EMPRESA = "/usuario_empresa/empresa/{empresaID}";
		public static final String URL_CONSULTAR_USUARIOS_ATIVOS_DO_PROJETO = "/usuario_empresa/list/{projetoID}/{empresaID}";
	}
	public final class Empresa{
		public static final String SALVAR_EMPRESA = "/empresa/salvar";
		public static final String ATUALIZAR_EMPRESA = "/empresa/atualizar";
		public static final String LISTAR_TODAS_EMPRESAS = "/empresa/listar";
		public static final String CONSULTAR_POR_NOME = "/empresa/{nome}";
		public static final String CONSULTAR_POR_CODIGO = "/empresa/{empresaID}";
	}
	
	public final class DailyScrum {
		public static final String URL_SALVAR = "/dailyscrum/save";
		public static final String URL_CONSULTAR_POR_SPRINT = "/dailyscrum/list/{sprintID}";
		public static final String URL_CONSULTAR_PROXIMO_DAILYSCRUM = "/dailyscrum/{projetoID}";
		public static final String URL_EXCLUIR_DAILY_SCRUM = "/dailyscrum/excluir";
	}
	
	public final class Team{
		private Team(){}
		public static final String URL_CONSULTAR_USUARIO_PROJETO = "/team/list/{projetoID}";
		public static final String CONSULTAR_PROJETO_POR_USUARIO_DA_EMPRESA = "/team/listProjetos/{usuarioID}/{empresaID}";
		public static final String CONSULTAR_TIME_PROJETO = "/team/timeProjeto/{codigoProjeto}/{codigoEmpresa}/{codigoUsuario}";
	}
	
	public final class TarefaReporte{
		private TarefaReporte(){}
		public static final String URL_REPORTAR_HORA = "/tarefa_reporte/{sprintID}/{itemID}";
	}
	
	public final class TarefaFavorita{
		private TarefaFavorita(){}
		public static final String URL_FAVORITAR_TAREFA = "/tarefa_favorita/atualizar";
	}

	public final class Config {
		private Config(){}
		public static final String URL_VERIFICAR_PERMISSAO = "config/verificar_permissao/{time}/{configEnum}";
	}
}
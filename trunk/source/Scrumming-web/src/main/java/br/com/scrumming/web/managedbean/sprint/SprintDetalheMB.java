package br.com.scrumming.web.managedbean.sprint;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.DailyScrum;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;
import br.com.scrumming.web.clientService.DailyScrumClientService;
import br.com.scrumming.web.clientService.ItemBacklogClientService;
import br.com.scrumming.web.clientService.SprintClientService;
import br.com.scrumming.web.clientService.TarefaClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class SprintDetalheMB extends AbstractBean {

	private static final long serialVersionUID = 1L;
	private DailyScrumClientService dailyClienteService;	
	private List<ItemBacklog> itens;
	@FlashScoped
	private Sprint sprintSelecionada;
	@FlashScoped
	private Projeto projetoSelecionado;
	@FlashScoped
	private ItemBacklog itemSelecionado;
	private SprintClientService sprintClienteService;
	@FlashScoped
	private Tarefa tarefa;
	@FlashScoped
	private Tarefa tarefaSelecionada;
	private TarefaClientService tarefaClientService;
	private ItemBacklogClientService itemClienteService;
	@ManagedProperty(value="#{sessaoMB.usuario}")
	private Usuario usuarioLogado;
	private DailyScrum dailyScrum;
	private List<DailyScrum> dailies;
	private boolean showCalendar;
	private boolean showModal;
	
	@Override
	public void inicializar() {
		showModal = false;
		if (dailyScrum == null) {
			dailyScrum = new DailyScrum();
		}
		if (tarefa == null) {
			tarefa = new Tarefa();
		}
		if (tarefaSelecionada == null) {
			tarefaSelecionada = new Tarefa();
		}
		sprintClienteService = new SprintClientService();
		itemClienteService = new ItemBacklogClientService();
		dailyClienteService = new DailyScrumClientService();
		tarefaClientService = new TarefaClientService();
		itens = sprintClienteService.consultarSprintBacklog(sprintSelecionada.getCodigo());
		dailies = dailyClienteService.consultarDailyScrumPorSprints(sprintSelecionada.getCodigo());
	}
	
	/*FUNÇÕES REFERENTES AO ITEMBACKLOG*/
	public void entregarItem() {
		itemSelecionado.setSituacaoBacklog(SituacaoItemBacklogEnum.FEITO);
		itemClienteService.salvarItemBacklog(itemSelecionado);
		itens = sprintClienteService.consultarSprintBacklog(sprintSelecionada.getCodigo());
	}
	
	/*FUNÇÕES REFERENTES AO DAILY SCRUM*/
	public void salvarDailyScrum() {
		dailyClienteService.salvarDailyScrum(dailyScrum);
		atualizarLista();
	}
	
	public String novoDaily(){
		showModal = true;
		showCalendar = true;
    	dailyScrum = new DailyScrum();
    	return "";
    }
	
	public void alterarDailyScrum() {
		showModal = true;
		showCalendar = false;
		atualizarLista();
	}
	
	/**
	 * Exclui um DailyScrum selecionado.
	 * @return
	 */
	public String excluirDaily() {
		dailyClienteService.excluirDailyScrum(dailyScrum);
    	atualizarLista();
    	mensagemSucesso();
        return "";
    }
	
	private void atualizarLista() {
		dailies = dailyClienteService.consultarDailyScrumPorSprints(sprintSelecionada.getCodigo());
	}
	
	private void mensagemSucesso() {
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
	}
	
	/* FUNÇÕES REFERENTES À TAREFA*/
	public void preparaParaInserirTarefa() {
		tarefa = new Tarefa();
	}
	
	public void preparaParaAlterarTarefa() {
		tarefa = tarefaSelecionada;
	}
	
	private void atualizarListaDeTarefas() {
		if (itemSelecionado != null) {
			itemSelecionado.setTarefas(tarefaClientService.consultarTarefasPorItemBacklog(itemSelecionado.getCodigo()));
		}
	}
		
	public String salvarTarefa() {
		tarefaClientService.salvarTarefa(tarefa, itemSelecionado.getCodigo());
		limparObjetoTarefa();
		atualizarListaDeTarefas();
    	FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
    	return "";
	}
	
	private void limparObjetoTarefa() {
		tarefa = null;		
	}

	public String removerTarefa(){
		tarefaClientService.removerTarefa(tarefaSelecionada);
		atualizarListaDeTarefas();
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
		return "";
	}
	
	public String atribuirTarefaParaMim(){
		tarefaClientService.atribuirTarefaPara(tarefaSelecionada, usuarioLogado.getCodigo());
		atualizarListaDeTarefas();
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
		return "";
	}
	
	/* Métodos para redirecionamento das páginas */
	public String sprintCadastroPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_CADASTRO_PAGE);
	}

	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}
	
	public String itembacklogDetalhePage() {
    	return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_DETAIL_PAGE);
    }
	
	public String itemBacklogCadastroPage() {
		return redirecionar(PaginasUtil.ItemBacklog.CADASTRAR_ITEM_BACKLOG);
	}

	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}
	
	public String sprintDetalhePage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_DETAIL_PAGE);
	}

	/* Getters anda Setters */
	public Sprint getSprintSelecionada() {
		return sprintSelecionada;
	}

	public void setSprintSelecionada(Sprint sprintSelecionada) {
		this.sprintSelecionada = sprintSelecionada;
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}

	public ItemBacklog getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(ItemBacklog itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public List<ItemBacklog> getItens() {
		return itens;
	}

	public void setItens(List<ItemBacklog> itens) {
		this.itens = itens;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Tarefa getTarefaSelecionada() {
		return tarefaSelecionada;
	}

	public void setTarefaSelecionada(Tarefa tarefaSelecionada) {
		this.tarefaSelecionada = tarefaSelecionada;
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<DailyScrum> getDailies() {
		return dailies;
	}

	public void setDailies(List<DailyScrum> dailies) {
		this.dailies = dailies;
	}

	public DailyScrum getDailyScrum() {
		return dailyScrum;
	}

	public void setDailyScrum(DailyScrum dailyScrum) {
		this.dailyScrum = dailyScrum;
	}

	public boolean isShowCalendar() {
		return showCalendar;
	}

	public boolean isShowModal() {
		return showModal;
	}
}
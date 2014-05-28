package br.com.scrumming.web.managedbean.sprint;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

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
import br.com.scrumming.web.managedbean.tarefa.TarefaFavoritaMB;

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
	@FlashScoped
	private DailyScrum dailyScrum;
	@FlashScoped
	private DailyScrum dailyScrumSelecionado;
	private List<DailyScrum> dailies;
	private boolean saveDaily;
	private boolean showModal;
	private String modalHeight;
	private String datePattern;
	private boolean uniqueDaily;
	private Long totalDeHorasEstimadasDaSprint;
	private CartesianChartModel graficoDeLinha;

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
		dailyScrum.setUniqueDaily(true);
		sprintClienteService = new SprintClientService();
		itemClienteService = new ItemBacklogClientService();
		dailyClienteService = new DailyScrumClientService();
		tarefaClientService = new TarefaClientService();
		atualizarListaDeItens();
		dailies = dailyClienteService.consultarDailyScrumPorSprints(sprintSelecionada.getCodigo());
		inicializarGrafico();
	}
	
	private void inicializarGrafico() {
		setTotalDeHorasEstimadasDaSprint(sprintClienteService.totalDeHorasEstimadasDaSprint(sprintSelecionada.getCodigo()));		
		graficoDeLinha = criarGraficoBurnDownDaSprint();	
	}
	
	private CartesianChartModel criarGraficoBurnDownDaSprint() {
		CartesianChartModel model = new CartesianChartModel();
 
        ChartSeries estimado = new ChartSeries();
        estimado.setLabel("Estimado");
        
        ChartSeries atual = new ChartSeries();
        atual.setLabel("Atual");
        
        DateTime data = sprintSelecionada.getDataInicio();
        int dias = Days.daysBetween(sprintSelecionada.getDataInicio(), sprintSelecionada.getDataFim()).getDays();
        double horasEstimadas = 0;
        double horasRestantes = 0;
        double horasParaSubtrair = 0;
        Long totalDeHorasRestantesDaSprintPorData;
        
        if (getTotalDeHorasEstimadasDaSprint() != null) {
        	horasEstimadas = getTotalDeHorasEstimadasDaSprint();
            horasRestantes = getTotalDeHorasEstimadasDaSprint();
        }
        
        for (int i = 0; i < dias; i++) {
        	if (i == 0) {
        		estimado.set(data.toString("dd ") + data.monthOfYear().getAsShortText(), horasEstimadas);
        		atual.set(data.toString("dd ") + data.monthOfYear().getAsShortText(), horasEstimadas);
        	} else {
        		if (getTotalDeHorasEstimadasDaSprint() != null) {
        			horasParaSubtrair = ((double) getTotalDeHorasEstimadasDaSprint() / (dias-1));
        		}
        		horasEstimadas = horasEstimadas - horasParaSubtrair;
        		totalDeHorasRestantesDaSprintPorData = sprintClienteService.totalDeHorasRestantesDaSprintPorData(sprintSelecionada.getCodigo(), data.plusDays(i).toString("yyyy-MM-dd"));
        		if (totalDeHorasRestantesDaSprintPorData != null) {
        			horasRestantes = totalDeHorasRestantesDaSprintPorData;
        		}
        		estimado.set(data.plusDays(i).toString("dd ") + data.monthOfYear().getAsShortText(), horasEstimadas);
        		atual.set(data.plusDays(i).toString("dd ") + data.monthOfYear().getAsShortText(), horasRestantes);
        	}
		}        
 
        model.addSeries(estimado);
        model.addSeries(atual);
         
        return model;
    }
		
	/*FUNÇÕES REFERENTES AO ITEMBACKLOG*/
	public void entregarItem() {
		itemSelecionado.setSituacaoBacklog(SituacaoItemBacklogEnum.ENTREGUE);
		itemClienteService.salvarItemBacklog(itemSelecionado);
		itens = sprintClienteService.consultarSprintBacklog(sprintSelecionada.getCodigo(), usuarioLogado.getCodigo());
	}
	
	/*FUNÇÕES REFERENTES AO DAILY SCRUM*/
	public void salvarDailyScrum() {
		dailyScrum.setSprint(sprintSelecionada);
		dailyClienteService.salvarDailyScrum(dailyScrum);
		limparDailyScrum();
		atualizarLista();
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
	}
	
	public String novoDaily() {
		limparDailyScrum();
		saveDaily = true;
		showModal = true;
		return "";
    }
	
	public void alterarDailyScrum() {
		saveDaily = false;
		showModal = true;
		dailyScrum.setUniqueDaily(false);		
		dailyScrum = dailyScrumSelecionado;
	}
	
	private void limparDailyScrum() {
		dailyScrum = new DailyScrum();
	}
	
	/**
	 * Exclui um DailyScrum selecionado.
	 * @return
	 */
	public String excluirDaily() {
		dailyClienteService.excluirDailyScrum(dailyScrum);
    	atualizarLista();
    	return "";
    }
	
	private void atualizarLista() {
		dailies = dailyClienteService.consultarDailyScrumPorSprints(sprintSelecionada.getCodigo());
	}
	
	/* FUNÇÕES REFERENTES À TAREFA*/
	public void preparaParaInserirTarefa() {
		tarefa = new Tarefa();
	}
	
	public void preparaParaAlterarTarefa() {
		tarefa = tarefaSelecionada;
	}
	
	private void atualizarListaDeItens(){
		if (sprintSelecionada != null) {
			itens = sprintClienteService.consultarSprintBacklog(sprintSelecionada.getCodigo(), usuarioLogado.getCodigo());
		}
	}
	
	private void atualizarListaDeTarefas() {
		if (itemSelecionado != null) {
			itemSelecionado.setTarefas(tarefaClientService.consultarPorItemBacklogIhUsuarioLogado(itemSelecionado.getCodigo(), getUsuarioLogado().getCodigo()));
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
		if (tarefaSelecionada.getItemBacklog() == null) {
			tarefaSelecionada.setItemBacklog(itemSelecionado);
		}
		tarefaClientService.atribuirTarefaPara(tarefaSelecionada, usuarioLogado.getCodigo());		
		atualizarListaDeTarefas();
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
		return "";
	}
	
	public void favoritarTarefa() {
		TarefaFavoritaMB tarefaFavoritaMB = new TarefaFavoritaMB();
		tarefaFavoritaMB.setTarefaSelecionada(tarefaSelecionada);
		tarefaFavoritaMB.setUsuarioLogado(usuarioLogado);
		tarefaFavoritaMB.favoritarTarefa();
		atualizarListaDeTarefas();
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

	public String boardPage(){
		return redirecionar(PaginasUtil.Board.BOARD_PAGE);
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

	public boolean isSaveDaily() {
		return saveDaily;
	}

	public boolean isShowModal() {
		return showModal;
	}

	public DailyScrum getDailyScrumSelecionado() {
		return dailyScrumSelecionado;
	}

	public void setDailyScrumSelecionado(DailyScrum dailyScrumSelecionado) {
		this.dailyScrumSelecionado = dailyScrumSelecionado;
	}
	
	public boolean isUniqueDaily() {
		uniqueDaily = dailyScrum.isUniqueDaily();
		if (uniqueDaily == true) {
			modalHeight = "280";
			datePattern = "dd/MM/yyyy HH:mm";
		} else {
			modalHeight = "280";
			datePattern = "HH:mm";
		}
		if (saveDaily == false) {
			modalHeight = "250";
		}
		return uniqueDaily;
	}

	public String getModalHeight() {
		return modalHeight;
	}

	public void setModalHeight(String modalHeight) {
		this.modalHeight = modalHeight;
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	public Long getTotalDeHorasEstimadasDaSprint() {
		return totalDeHorasEstimadasDaSprint;
	}

	public void setTotalDeHorasEstimadasDaSprint(
			Long totalDeHorasEstimadasDaSprint) {
		this.totalDeHorasEstimadasDaSprint = totalDeHorasEstimadasDaSprint;
	}

	public CartesianChartModel getGraficoDeLinha() {
		return graficoDeLinha;
	}

	public void setGraficoDeLinha(CartesianChartModel graficoDeLinha) {
		this.graficoDeLinha = graficoDeLinha;
	}
}

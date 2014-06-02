package br.com.scrumming.web.managedbean.board;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;

import org.apache.myfaces.event.SetPropertyActionListener;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;
import br.com.scrumming.web.clientService.SprintClientService;
import br.com.scrumming.web.clientService.TarefaClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class BoardBean extends AbstractBean {

	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;

	private Dashboard dashboard;
	private DashboardModel dashboardModel;
	private List<Tarefa> tarefas;
	private static final String TASK_PREFIX = "task_id_";
	private SprintClientService sprintService;
	private TarefaClientService tarefaClientService;
	private Tarefa tarefaSelecionada;
	@FlashScoped
	private Sprint sprintSelecionada;
	@FlashScoped
	private Projeto projetoSelecionado;
	@FlashScoped
	private ItemBacklog itemSelecionado;
	@ManagedProperty(value="#{sessaoMB.usuario}")
	private Usuario usuarioLogado;

	@Override
	protected void inicializar() {
		tarefaClientService = new TarefaClientService();
		sprintService = new SprintClientService();
		tarefas = sprintService.consultarTarefasPorSprint(sprintSelecionada
				.getCodigo());
//		 tarefas = criarFaceTarefa();
		carregarBoard();
	}

	public void carregarBoard() {
		dashboard = new Dashboard();

		

		dashboardModel = new DefaultDashboardModel();

		DashboardColumn toDo = new DefaultDashboardColumn();
		DashboardColumn inProgress = new DefaultDashboardColumn();
		DashboardColumn impediment = new DefaultDashboardColumn();
		DashboardColumn done = new DefaultDashboardColumn();

		dashboardModel.addColumn(toDo);
		dashboardModel.addColumn(inProgress);
		dashboardModel.addColumn(impediment);
		dashboardModel.addColumn(done);
		dashboard.setModel(dashboardModel);

		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ExpressionFactory expressionFactory = application.getExpressionFactory();
		ELContext elContext = context.getELContext();
		
		for (Tarefa tarefa : tarefas) {
			Panel panel = new Panel();
			panel.setId(TASK_PREFIX + tarefa.getCodigo().toString());
			panel.setHeader("Tarefa "+tarefa.getCodigo());
			panel.setStyleClass("painel");
			dashboard.getChildren().add(panel);
			
			DashboardColumn column = dashboardModel.getColumn(tarefa
					.getSituacao().ordinal());
			column.addWidget(panel.getId());
			
			HtmlPanelGrid panelGrid = new HtmlPanelGrid();
			panelGrid.setId("grid_"+tarefa.getCodigo());
			panelGrid.setColumns(1);
			panelGrid.setWidth("100%");
			panel.getChildren().add(panelGrid);
			
			
			HtmlOutputText htmlOutputText = new HtmlOutputText();
			htmlOutputText.setValue(tarefa.getNome());
			panelGrid.getChildren().add(htmlOutputText);
			
			CommandButton button = new CommandButton();
			button.setIcon("ui-icon-info");
			button.setImmediate(true);
			button.setOncomplete("boardModal.show()");
			button.setUpdate("boardModal");
			button.setTitle("Detalhe da Tarefa");
			button.setStyle("float: right;");
			ValueExpression target= expressionFactory.createValueExpression(elContext, "#{boardBean.tarefaSelecionada}", Object.class);
			ValueExpression value= expressionFactory.createValueExpression(tarefa, Tarefa.class);
			button.addActionListener(new SetPropertyActionListener(target, value));
			panelGrid.getChildren().add(button);
		}
	}
	
	public void handleReorder(DashboardReorderEvent event) {
		String widgetId = event.getWidgetId();
		int columnIndex = event.getColumnIndex();
		Tarefa task = findTarefa(Integer.valueOf(widgetId.substring(TASK_PREFIX.length())));
		
		try {
			tarefaClientService.validarDadosAntesDeAtualizarStatus(task, usuarioLogado.getCodigo());
			atualizarTarefa(task,(SituacaoTarefaEnum.values()[columnIndex]));
			
		} catch (Exception e) {
			
			DashboardColumn fromColumn = dashboardModel.getColumn(task.getSituacao().ordinal());
			DashboardColumn toColumn = dashboardModel.getColumn(columnIndex);
			int index = 0;
			//int index = event.getItemIndex();
			dashboardModel.transferWidget(toColumn, fromColumn , widgetId, index);
			
			throw e;
		}
		
		
	}

	private void atualizarTarefa(Tarefa task, SituacaoTarefaEnum situacaoTarefaEnum) {
		tarefaClientService.atualizarStatus(task.getCodigo(), situacaoTarefaEnum, usuarioLogado.getCodigo());
	}

	public Tarefa findTarefa(Integer id) {
		for (Tarefa tarefa : tarefas) {
			if (id.equals(tarefa.getCodigo())) {
				return tarefa;
			}
		}
		return null;
	}
	
	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}
	
	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}

	public DashboardModel getDashboardModel() {
		return dashboardModel;
	}

	public void setDashboardModel(DashboardModel dashboardModel) {
		this.dashboardModel = dashboardModel;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public SprintClientService getSprintService() {
		return sprintService;
	}

	public void setSprintService(SprintClientService sprintService) {
		this.sprintService = sprintService;
	}

	public Sprint getSprintSelecionada() {
		return sprintSelecionada;
	}

	public void setSprintSelecionada(Sprint sprintSelecionada) {
		this.sprintSelecionada = sprintSelecionada;
	}

	public Dashboard getDashboard() {
		return dashboard;
	}

	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}

	public TarefaClientService getTarefaClientService() {
		return tarefaClientService;
	}

	public void setTarefaClientService(TarefaClientService tarefaClientService) {
		this.tarefaClientService = tarefaClientService;
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
}
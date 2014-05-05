package br.com.scrumming.web.managedbean.board;

import java.util.Arrays;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.apache.myfaces.event.SetPropertyActionListener;
import org.joda.time.DateTime;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;
import br.com.scrumming.web.clientService.SprintClientService;
import br.com.scrumming.web.clientService.TarefaClientService;
import br.com.scrumming.web.infra.FlashScoped;
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
			panel.setHeader(tarefa.getNome());
			panel.setStyleClass("painel");
			dashboard.getChildren().add(panel);

			DashboardColumn column = dashboardModel.getColumn(tarefa
					.getSituacao().ordinal());
			column.addWidget(panel.getId());
			
			HtmlOutputText htmlOutputText = new HtmlOutputText();
			htmlOutputText.setValue(tarefa.getDescricao());
			panel.getChildren().add(htmlOutputText);
			
			CommandButton button = new CommandButton();
			button.setValue("Detalhe");
			button.setImmediate(true);
			button.setOncomplete("boardModal.show()");
			button.setUpdate("boardModal");
			ValueExpression target= expressionFactory.createValueExpression(elContext, "#{boardBean.tarefaSelecionada}", Object.class);
			ValueExpression value= expressionFactory.createValueExpression(tarefa, Tarefa.class);
			button.addActionListener(new SetPropertyActionListener(target, value));
			panel.getChildren().add(button);
		}
	}
	
	public void handleReorder(DashboardReorderEvent event) {
		String widgetId = event.getWidgetId();
		int columnIndex = event.getColumnIndex();
		Tarefa task = findTarefa(Integer.valueOf(widgetId.substring(TASK_PREFIX
				.length())));
		atualizarTarefa(task,(SituacaoTarefaEnum.values()[columnIndex]));
	}

	private void atualizarTarefa(Tarefa task, SituacaoTarefaEnum situacaoTarefaEnum) {
		tarefaClientService.atualizarStatus(task.getCodigo(), situacaoTarefaEnum);
	}

	public Tarefa findTarefa(Integer id) {
		for (Tarefa tarefa : tarefas) {
			if (id.equals(tarefa.getCodigo())) {
				return tarefa;
			}
		}
		return null;
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
}
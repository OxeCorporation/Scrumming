package br.com.scrumming.web.managedbean.board;

import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;

import org.joda.time.DateTime;
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
		}
	}

	// TODO remover apos criação do serviço
	private List<Tarefa> criarFaceTarefa() {
		Tarefa tarefa1 = new Tarefa();
		tarefa1.setCodigo(1);
		tarefa1.setNome("Tarefa 1");
		tarefa1.setSituacao(SituacaoTarefaEnum.PARA_FAZER);
		tarefa1.setDescricao("Tarefa 1 bla bla");
		tarefa1.setDataAtribuicao(DateTime.now());
		tarefa1.setTempoEstimado(4);

		Tarefa tarefa2 = new Tarefa();
		tarefa2.setNome("Tarefa 2");
		tarefa2.setCodigo(2);
		tarefa2.setSituacao(SituacaoTarefaEnum.PARA_FAZER);
		tarefa2.setDescricao("Tarefa 2 bla bla");
		tarefa2.setDataAtribuicao(DateTime.now());
		tarefa2.setTempoEstimado(5);

		Tarefa tarefa3 = new Tarefa();
		tarefa3.setNome("Tarefa 3");
		tarefa3.setCodigo(3);
		tarefa3.setSituacao(SituacaoTarefaEnum.FAZENDO);
		tarefa3.setDescricao("Tarefa 3 bla bla");
		tarefa3.setDataAtribuicao(DateTime.now());
		tarefa3.setTempoEstimado(7);

		Tarefa tarefa4 = new Tarefa();
		tarefa4.setNome("Tarefa 4");
		tarefa4.setCodigo(4);
		tarefa4.setSituacao(SituacaoTarefaEnum.FAZENDO);
		tarefa4.setDescricao("Tarefa 4 bla bla");
		tarefa4.setDataAtribuicao(DateTime.now());
		tarefa4.setTempoEstimado(8);

		Tarefa tarefa5 = new Tarefa();
		tarefa5.setNome("Tarefa 5");
		tarefa5.setCodigo(5);
		tarefa5.setSituacao(SituacaoTarefaEnum.FEITO);
		tarefa5.setDescricao("Tarefa 5 bla bla");
		tarefa5.setDataAtribuicao(DateTime.now());
		tarefa5.setTempoEstimado(2);

		Tarefa tarefa6 = new Tarefa();
		tarefa6.setNome("Tarefa 6");
		tarefa6.setCodigo(6);
		tarefa6.setSituacao(SituacaoTarefaEnum.EM_IMPEDIMENTO);
		tarefa6.setDescricao("Tarefa 6 bla bla");
		tarefa6.setDataAtribuicao(DateTime.now());
		tarefa6.setTempoEstimado(6);
		return Arrays.asList(tarefa1, tarefa2, tarefa3, tarefa4, tarefa5,
				tarefa6);
	}

	public void handleReorder(DashboardReorderEvent event) {
		String widgetId = event.getWidgetId();
		int columnIndex = event.getColumnIndex();
		Tarefa task = findTarefa(Integer.valueOf(widgetId.substring(TASK_PREFIX
				.length())));
		task.setSituacao((SituacaoTarefaEnum.values()[columnIndex]));
		atualizarTarefa(task);
	}

	private void atualizarTarefa(Tarefa task) {
		tarefaClientService.inserirOuAtualizar(task);
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
}
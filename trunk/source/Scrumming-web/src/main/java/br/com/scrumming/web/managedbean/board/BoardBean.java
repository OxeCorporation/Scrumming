package br.com.scrumming.web.managedbean.board;

import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.joda.time.DateTime;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;
import br.com.scrumming.web.infra.bean.AbstractBean;
@ManagedBean
@ViewScoped
public class BoardBean extends AbstractBean {

	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;

	private DashboardModel dashboardModel;
	private List<Tarefa> tarefas = criarFaceTarefa();
	private static final String PREFIX_TASK = "task_id_";
	
	
	@Override
	protected void inicializar() {
		init();
	}
	
	private void init() {
		dashboardModel = new DefaultDashboardModel();
		DashboardColumn toDo = new DefaultDashboardColumn();
		DashboardColumn inProgress = new DefaultDashboardColumn();
		DashboardColumn impediment = new DefaultDashboardColumn();
		DashboardColumn done = new DefaultDashboardColumn();
		
		
		dashboardModel.addColumn(toDo);
        dashboardModel.addColumn(inProgress);
        dashboardModel.addColumn(impediment);
        dashboardModel.addColumn(done);

        for(Tarefa tarefa: tarefas ){
            DashboardColumn column = dashboardModel.getColumn(tarefa.getSituacao().ordinal());
            column.addWidget(PREFIX_TASK+tarefa.getCodigo());
		}
	}

	//TODO remover apos criação do serviço
	private List<Tarefa> criarFaceTarefa(){
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
		return Arrays.asList(tarefa1,tarefa2,tarefa3,tarefa4,tarefa5,tarefa6);
	}

	
	public void handleReorder(DashboardReorderEvent event) {
        String widgetId = event.getWidgetId();
        int columnIndex = event.getColumnIndex();
        Tarefa task = findTarefa(Integer.valueOf(widgetId.substring(PREFIX_TASK.length())));
        task.setSituacao((SituacaoTarefaEnum.values()[columnIndex]));
    }
	
	private Tarefa findTarefa(Integer id){
		for (Tarefa tarefa : tarefas) {
			if(tarefa.getCodigo().equals(id)){
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
}
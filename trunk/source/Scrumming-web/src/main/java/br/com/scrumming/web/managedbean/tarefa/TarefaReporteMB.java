package br.com.scrumming.web.managedbean.tarefa;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.TarefaReporte;
import br.com.scrumming.web.clientService.TarefaReporteClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class TarefaReporteMB extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FlashScoped
	private TarefaReporte tarefaReporte;
	private TarefaReporteClientService tarefaReporteClientService;
	
	
	@Override
    public void inicializar() {
		setTarefaReporte(new TarefaReporte());
		tarefaReporteClientService = new TarefaReporteClientService();
	}
	
	public void reportarHora() {
		tarefaReporteClientService.reportarHora(tarefaReporte);
		limparObjetoTarefaReporte();
	}
	
	private void limparObjetoTarefaReporte(){
		setTarefaReporte(null);		
	}
	
	public void preparaParaInserir() {
		setTarefaReporte(new TarefaReporte());
	}

	/* getters and setters */
	public TarefaReporte getTarefaReporte() {
		return tarefaReporte;
	}

	public void setTarefaReporte(TarefaReporte tarefaReporte) {
		this.tarefaReporte = tarefaReporte;
	}	
	
}
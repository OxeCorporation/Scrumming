package br.com.scrumming.web.managedbean.dailyscrum;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.DailyScrum;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.web.clientService.DailyScrumClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class DailyScrumMB extends AbstractBean {
	
	private static final long serialVersionUID = 1L;
	private DailyScrum dailyScrum;
	private List<DailyScrum> dailyScrumsDaSprint;
	private DailyScrumClientService dailyScrumClientService;
	@FlashScoped
	private DailyScrum proximoDailyScrum;
	@FlashScoped
	private Sprint sprintSelecionada;
	
	@Override
	public void inicializar() {
		dailyScrum = new DailyScrum();
		dailyScrumClientService = new DailyScrumClientService();
	}
	
	/*Funções da tela*/
	public String listarDailyScrumDaSprint(Integer sprintID){
		setDailyScrumsDaSprint(dailyScrumClientService.consultarDailyScrumPorSprints(sprintID));
		return "";
	}
	
	public String consultarProximoDailyScrum(Integer sprintID){
		setDailyScrum((DailyScrum)dailyScrumClientService.consultarProximoDailyScrum(sprintID));
		return "";
	}
	
	public void salvarDailyScrum(){
		dailyScrumClientService.salvarDailyScrum(dailyScrum);
	}
	
	/*Getters and Setters*/
	public DailyScrum getDailyScrum() {
		return dailyScrum;
	}

	public void setDailyScrum(DailyScrum dailyScrum) {
		this.dailyScrum = dailyScrum;
	}

	public List<DailyScrum> getDailyScrumsDaSprint() {
		return dailyScrumsDaSprint;
	}

	public void setDailyScrumsDaSprint(List<DailyScrum> dailyScrumsDaSprint) {
		this.dailyScrumsDaSprint = dailyScrumsDaSprint;
	}

	public DailyScrumClientService getDailyScrumClientService() {
		return dailyScrumClientService;
	}

	public void setDailyScrumClientService(
			DailyScrumClientService dailyScrumClientService) {
		this.dailyScrumClientService = dailyScrumClientService;
	}

	public DailyScrum getProximoDailyScrum() {
		return proximoDailyScrum;
	}

	public void setProximoDailyScrum(DailyScrum proximoDailyScrum) {
		this.proximoDailyScrum = proximoDailyScrum;
	}

	public Sprint getSprintSelecionada() {
		return sprintSelecionada;
	}

	public void setSprintSelecionada(Sprint sprintSelecionada) {
		this.sprintSelecionada = sprintSelecionada;
	}
}
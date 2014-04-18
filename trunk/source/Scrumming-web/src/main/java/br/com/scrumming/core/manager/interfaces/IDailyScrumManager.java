package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.DailyScrum;

public interface IDailyScrumManager extends IManager<DailyScrum, Integer> {

	String salvarDailyScrum(DailyScrum dailyScrum);
	List<DailyScrum> listarDailyScrumDaSprint(Integer sprintID);
	DailyScrum consultarProximoDailyScrum(Integer sprintID);
	String excluirDailyScrum(DailyScrum dailyScrum);
}
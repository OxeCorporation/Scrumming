package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.DailyScrum;
import br.com.scrumming.domain.Sprint;

public interface IDailyScrumManager extends IManager<DailyScrum, Integer> {

	String salvarDailyScrum(DailyScrum dailyScrum);
	List<DailyScrum> listarDailyScrumDaSprint(Sprint sprint);
	DailyScrum consultarProximoDailyScrum(Sprint sprint);
}
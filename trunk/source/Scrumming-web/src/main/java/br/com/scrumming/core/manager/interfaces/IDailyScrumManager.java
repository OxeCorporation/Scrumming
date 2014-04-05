package br.com.scrumming.core.manager.interfaces;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.DailyScrum;

public interface IDailyScrumManager extends IManager<DailyScrum, Integer> {

	public String salvarDailyScrum(DailyScrum dailyScrum);

}
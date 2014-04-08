package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.scrumming.domain.DailyScrum;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class DailyScrumClientService extends AbstractClientService {

	public String salvarDailyScrum(DailyScrum dailyScrum) {
		return getRestTemplate().postForObject(getURIService(ConstantesService.DailyScrum.URL_SALVAR), dailyScrum, String.class);
	}

	public List<DailyScrum> consultarDailyScrumPorSprints(Integer sprintID) {
		return Arrays.asList(getRestTemplate().getForObject(getURIService(ConstantesService.DailyScrum.URL_CONSULTAR_POR_SPRINT), DailyScrum[].class, sprintID));
	}
	
	public DailyScrum consultarProximoDailyScrum(Integer sprintID) {
		
		ResponseEntity<DailyScrum> forEntity = getRestTemplate().getForEntity(getURIService(ConstantesService.DailyScrum.URL_CONSULTAR_PROXIMO_DAILYSCRUM), DailyScrum.class, sprintID);
		return forEntity.getBody();
	}
	
}
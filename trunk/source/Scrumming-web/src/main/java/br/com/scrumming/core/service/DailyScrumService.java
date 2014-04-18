package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.IDailyScrumManager;
import br.com.scrumming.domain.DailyScrum;

@RestController
@RequestMapping("/dailyscrum")
public class DailyScrumService {

    @Autowired
    private IDailyScrumManager dailyScrumManager;
    
    /*POSTS*/
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public String salvarDailyScrum(@RequestBody DailyScrum dailyScrum) {
    	return this.dailyScrumManager.salvarDailyScrum(dailyScrum);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/excluir")
    public String excluirDailyScrum(@RequestBody DailyScrum dailyScrum) {
    	return dailyScrumManager.excluirDailyScrum(dailyScrum);
    }
   
    /*GETS*/
    @RequestMapping(method = RequestMethod.GET, value = "/list/{sprintID}")
    public List<DailyScrum> consultarPorSprint(@PathVariable Integer sprintID) {
    	return new ArrayList<DailyScrum>(dailyScrumManager.listarDailyScrumDaSprint(sprintID));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{sprintID}")
    public DailyScrum consultarProximoDaily(@PathVariable Integer sprintID) {
    	return dailyScrumManager.consultarProximoDailyScrum(sprintID);
    }

    /* getters and setters */
	public IDailyScrumManager getDailyScrumManager() {
		return dailyScrumManager;
	}


	public void setDailyScrumManager(IDailyScrumManager dailyScrumManager) {
		this.dailyScrumManager = dailyScrumManager;
	}
}
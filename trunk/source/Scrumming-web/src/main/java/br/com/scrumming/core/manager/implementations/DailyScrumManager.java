package br.com.scrumming.core.manager.implementations;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IDailyScrumManager;
import br.com.scrumming.core.repositorio.DailyScrumRepositorio;
import br.com.scrumming.domain.DailyScrum;

@Service
public class DailyScrumManager extends AbstractManager<DailyScrum, Integer> implements IDailyScrumManager {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private DailyScrumRepositorio dailyScrumRepositorio;
    
	@Autowired
	private TeamManager teamManage;

    @Override
    public AbstractRepositorio<DailyScrum, Integer> getRepositorio() {
        return this.dailyScrumRepositorio;
    }
 
	@Override
	public String salvarDailyScrum(DailyScrum dailyScrum) {

		String retorno = "";
		DateTime dataInicio = dailyScrum.getSprint().getDataInicio();
		DateTime dataFim = dailyScrum.getSprint().getDataFim();
		
		for (int i = dataInicio.getDayOfMonth(); i < dataFim.getDayOfMonth(); i++) {
			dailyScrumRepositorio.save(dailyScrum);			
		}
		return retorno;
	}

}
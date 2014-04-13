package br.com.scrumming.core.manager.implementations;

import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IDailyScrumManager;
import br.com.scrumming.core.repositorio.DailyScrumRepositorio;
import br.com.scrumming.domain.DailyScrum;

@Service
public class DailyScrumManager extends AbstractManager<DailyScrum, Integer>
		implements IDailyScrumManager {

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

		int e;
		String retorno = "";
		DateTime dataInicio = dailyScrum.getSprint().getDataInicio();
		DateTime dataFim = dailyScrum.getSprint().getDataFim();
		if (dailyScrum.getCodigo() == null) {
			if (dataInicio.isAfterNow()) {
				e = dataInicio.getDayOfMonth();
				retorno = "DailyScrum Salvo com sucesso.";
				salve(dailyScrum, e, dataInicio, dataFim);
			} else {
				Calendar.getInstance();
				e = Calendar.DAY_OF_MONTH;
				retorno = "DailyScrum Sailvo. Próxima DailyScrum "
						+ Calendar.DATE + " Horário: "
						+ dailyScrum.getDataHora().getHourOfDay() + ":"
						+ dailyScrum.getDataHora().getMillisOfDay();
				salve(dailyScrum, e, dataInicio, dataFim);
			}
		}else{
			dailyScrumRepositorio.save(dailyScrum);
			retorno = "DailyScrum Alterado";
		}
		return retorno;
	}

	private void salve(DailyScrum dailyScrum, int e, DateTime dataInicio,
			DateTime dataFim) {
		for (int i = e; i < dataFim.getDayOfMonth(); i++) {
			dailyScrumRepositorio.save(dailyScrum);
			dailyScrum.setDataHora(dataInicio.plusDays(1));
		}
	}

	@Override
	public List<DailyScrum> listarDailyScrumDaSprint(int sprintID) {
		return dailyScrumRepositorio.listarDailyScrumPorSprint(sprintID);
	}

	@Override
	public DailyScrum consultarProximoDailyScrum(int sprintID) {
		List<DailyScrum> dailyLista = dailyScrumRepositorio
				.listarDailyScrumPorSprint(sprintID);

		for (int i = 0; i < dailyLista.size(); i++) {
			if (dailyLista.get(i).getDataHora().isAfterNow()) {
				return dailyScrumRepositorio
						.consultarProximoDailyScrum(dailyLista.get(i)
								.getCodigo());
			}
		}
		return null;
	}

}
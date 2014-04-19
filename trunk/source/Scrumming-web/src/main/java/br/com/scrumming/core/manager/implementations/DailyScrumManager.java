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
	private TeamManager teamManager;
	
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
		if (dailyScrum.getCodigo() == null) { // Sem código (Novo daily)
			if (dataInicio.isAfterNow()) {
				e = dataInicio.getDayOfMonth();
				retorno = "DailyScrum Salvo com sucesso.";
				dailyScrum.setDataHora(dataInicio);
				salve(dailyScrum, e, dataInicio, dataFim);
			} else { // Com código (Alteração Daily)
				insertOrUpdate(dailyScrum);
				Calendar.getInstance();
				e = Calendar.DAY_OF_MONTH;
				retorno = "DailyScrum Salvo. Próxima DailyScrum "
						+ Calendar.DATE + " Horário: "
						+ dailyScrum.getDataHora().getHourOfDay() + ":"
						+ dailyScrum.getDataHora().getMillisOfDay();
				salve(dailyScrum, e, dataInicio, dataFim);
			}
		} else {
			dailyScrumRepositorio.save(dailyScrum);
			retorno = "DailyScrum Alterado";
		}
		return retorno;
	}

	private void salve(DailyScrum dailyScrum, int e, DateTime dataInicio, DateTime dataFim) {
		if (dailyScrum.getDataHora() == null) {
			int diaFim = dataFim.getDayOfMonth();
			for (int i = e; i < diaFim; i++) {
				insertOrUpdate(dailyScrum);
				dailyScrum.setDataHora(dataInicio.plusDays(1));
			}
		} else {
			dailyScrum.setDataHora(new DateTime(dailyScrum.getDataHoraCalendar()));
			insertOrUpdate(dailyScrum);
		}
	}

	@Override
	public List<DailyScrum> listarDailyScrumDaSprint(Integer sprintID) {
		return dailyScrumRepositorio.listarDailyScrumPorSprint(sprintID);
	}

	@Override
	public DailyScrum consultarProximoDailyScrum(Integer sprintID) {
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
	
	@Override
	public String excluirDailyScrum(DailyScrum dailyScrum) {
		String retorno = "";
		if (dailyScrum.getDataHora().isAfterNow()) {
			remove(dailyScrum);
			retorno = "Registro excluído com sucesso";
		} else {
			retorno = "Registro não pode ser excluído";
		}
		return retorno;
	}
}
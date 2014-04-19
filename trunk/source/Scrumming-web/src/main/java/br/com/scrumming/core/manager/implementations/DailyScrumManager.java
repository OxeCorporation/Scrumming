package br.com.scrumming.core.manager.implementations;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.core.manager.interfaces.IDailyScrumManager;
import br.com.scrumming.core.repositorio.DailyScrumRepositorio;
import br.com.scrumming.domain.DailyScrum;
import br.com.scrumming.web.infra.FacesMessageUtil;

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

		int diaSprint;
		DateTime dataInicioSprint = dailyScrum.getSprint().getDataInicio();
		DateTime dataFimSprint = dailyScrum.getSprint().getDataFim();
		
		// Sem código (Novo daily)
		if (dailyScrum.getCodigo() == null) {
			
			// Salva apenas um novo registro.
			if (dailyScrum.isUniqueDaily()) {
				dailyScrum.setDataHora(new DateTime(dailyScrum.getDataHoraCalendar()));
				if (dailyScrum.getDataHora().isAfterNow()) {
					insertOrUpdate(dailyScrum);
				} else {
					FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_ERRO_DATA_DAILY);
				}			
			// Salva um novo registro para cada dia da Sprint.
			} else {
				if (dataInicioSprint.isAfterNow()) {
					Date data1 = dataInicioSprint.toDate();
					data1.setTime(dailyScrum.getDataHoraCalendar().getTime());
					diaSprint = dataInicioSprint.getDayOfMonth();
					dailyScrum.setDataHora(new DateTime(data1));
					save(dailyScrum, diaSprint, dataInicioSprint, dataFimSprint);
				} else {
					insertOrUpdate(dailyScrum);
					Calendar.getInstance();
					diaSprint = Calendar.DAY_OF_MONTH;
					save(dailyScrum, diaSprint, dataInicioSprint, dataFimSprint);
				}
			}
		// Com código (Alteração)
		} else {
			insertOrUpdate(dailyScrum);
			FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_REGISTRO_ALTERADO_SUCESSO);
		}
		return "";
	}

	private void save(DailyScrum dailyScrum, int e, DateTime dataInicioSprint, DateTime dataFimSprint) {
		if (dailyScrum.isUniqueDaily()) {
			dailyScrum.setDataHora(new DateTime(dailyScrum.getDataHoraCalendar()));
			insertOrUpdate(dailyScrum);
		} else {
			int interval = Days.daysBetween(dataInicioSprint, dataFimSprint).getDays();
			for (int i = 0; i < interval; i++) {
				insertOrUpdate(dailyScrum);
				dailyScrum.setDataHora(dataInicioSprint.plusDays(1));
			}
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
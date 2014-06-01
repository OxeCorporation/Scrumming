package br.com.scrumming.core.manager.implementations;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.exceptions.NegocioException;
import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.infra.util.ConstantesMensagem;
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
	@Transactional(rollbackFor=Exception.class)
	public String salvarDailyScrum(DailyScrum dailyScrum) {

		DateTime dataInicioSprint = dailyScrum.getSprint().getDataInicio();
		DateTime dataFimSprint = dailyScrum.getSprint().getDataFim();
		
		// Sem código (Novo daily)
		if (dailyScrum.getCodigo() == null) {
			
			// Salva apenas um novo registro.
			if (dailyScrum.isUniqueDaily()) {
				dailyScrum.setDataHora(new DateTime(dailyScrum.getDataHoraCalendar()));
				if (dailyScrum.getDataHora().isAfterNow()) {
					if (isInTheRange(dailyScrum)) {
						if (isDuplicatedDateTime(dailyScrum)) {
							throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_DUPLICIDADE_DAILY);
						} else {
							insertOrUpdate(dailyScrum);
						}
					} else {
						throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_DAILY_FORA_SPRINT);
					}
				} else {
					throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_DATA_DAILY);
				}			
			// Salva um novo registro para cada dia da Sprint.
			} else {
				if (dataInicioSprint.isAfterNow()) {
					dailyScrum.setDataHora(dailyScrum.getSprint().getDataInicio());
					dailyScrum.setDataHora(preparaData(dailyScrum));
					saveSprintDailies(dailyScrum, dataInicioSprint, dataFimSprint);
				} else {
					dailyScrum.setDataHora(new DateTime(DateTime.now()));
					dailyScrum.setDataHora(preparaData(dailyScrum));
					saveSprintDailies(dailyScrum, dailyScrum.getDataHora().minusDays(1), dataFimSprint);
				}
			}
		// Com código (Alteração)
		} else {
			dailyScrum.setDataHora(preparaData(dailyScrum));
			if (dailyScrum.getDataHora().isAfterNow()) {
				insertOrUpdate(dailyScrum);
			} else {
				throw new NegocioException(ConstantesMensagem.ERRO_DAILY_PASSADO);
			}
		}
		return "";
	}
	
	/**
	 * Prepara a data para ser setada no objeto DailyScrum a ser persistido no banco.
	 * Chamado apenas quando o calendário exibe apenas a hora e minuto a serem setados.
	 * @param dailyScrum
	 * @return
	 */
	private DateTime preparaData(DailyScrum dailyScrum) {
		int year, month, date, hourOfDay, minute;
		Calendar cal = new GregorianCalendar();
		year = dailyScrum.getDataHora().getYear();
		month = dailyScrum.getDataHora().getMonthOfYear() - 1;
		date = dailyScrum.getDataHora().getDayOfMonth();
		hourOfDay = new DateTime(dailyScrum.getDataHoraCalendar()).getHourOfDay();
		minute = new DateTime(dailyScrum.getDataHoraCalendar()).getMinuteOfHour();
		cal.set(year, month, date, hourOfDay, minute);
		cal.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
		return new DateTime(cal);
	}

	/**
	 * Salva um Daily para cada dia de uma Sprint
	 * @param dailyScrum
	 * @param dataInicioSprint
	 * @param dataFimSprint
	 */
	@Transactional(rollbackFor=Exception.class)
	private void saveSprintDailies(DailyScrum dailyScrum, DateTime dataInicioSprint, DateTime dataFimSprint) {
		List<DailyScrum> dailies = listarDailyScrumDaSprint(dailyScrum.getSprint().getCodigo());
		int intervalo = Days.daysBetween(dataInicioSprint, dataFimSprint).getDays();
		for (DailyScrum dailyScrum2 : dailies) {
			if (dailyScrum2.getDataHora().isAfterNow()) {
				remove(dailyScrum2);
			}
		}
		for (int i = 0; i <= intervalo; i++) {
			if (dailyScrum.getDataHora().isAfterNow()) {
				if (!isDuplicatedDateTime(dailyScrum)) {
					insertOrUpdate(createNewDaily(dailyScrum));
				}
			}
			dailyScrum.setDataHora(dailyScrum.getDataHora().plusDays(1));
		}
	}
	
	/**
	 * Verifica se existe algum Daily cadastrado no mesmo dia/hora/minuto.
	 */
	private boolean isDuplicatedDateTime(DailyScrum daily) {
		boolean duplicated = false;
		List<DailyScrum> lista = listarDailyScrumDaSprint(daily.getSprint().getCodigo());
		for (DailyScrum dailyScrum : lista) {
			//int intervalo = Days.daysBetween(dailyScrum.getDataHora(), daily.getDataHora().plusDays(1)).getDays();
			if(dailyScrum.getDataHora().getDayOfMonth() == daily.getDataHora().getDayOfMonth()) {
				duplicated = true;
			}
			/*if (intervalo == 0) {
				duplicated = true;
			}*/
		}
		return duplicated;
	}
	
	private boolean isInTheRange(DailyScrum daily) {
		boolean range = false;
		DateTime dataDaily = daily.getDataHora();
		DateTime inicioSprint = daily.getSprint().getDataInicio();
		DateTime fimSprint = daily.getSprint().getDataFim();
		if (dataDaily.isAfter(inicioSprint) && dataDaily.isBefore(fimSprint.plusDays(1))) {
			range = true;
		}
		return range;
	}
	
	private DailyScrum createNewDaily(DailyScrum dailyScrum) {
		DailyScrum retorno = new DailyScrum();
		retorno.setSprint(dailyScrum.getSprint());
		retorno.setDataHora(dailyScrum.getDataHora());
		retorno.setDataHoraCalendar(dailyScrum.getDataHoraCalendar());
		retorno.setLocal(dailyScrum.getLocal());
		retorno.setDuracao(dailyScrum.getDuracao());
		retorno.setDescricao(dailyScrum.getDescricao());
		return retorno;
	}

	@Override
	@Transactional(readOnly=true)
	public List<DailyScrum> listarDailyScrumDaSprint(Integer sprintID) {
		List<DailyScrum> daily = dailyScrumRepositorio.listarDailyScrumPorSprint(sprintID); 
		for (DailyScrum dailyScrum : daily) {
			dailyScrum.setEditableDaily(true);
			if (dailyScrum.getDataHora().isBeforeNow()) {
				dailyScrum.setEditableDaily(false);
			}
		}
		return daily;
	}

	@Override
	@Transactional(readOnly=true)
	public DailyScrum consultarProximoDailyScrum(Integer sprintID) {
		List<DailyScrum> dailyLista = dailyScrumRepositorio.listarDailyScrumPorSprint(sprintID);
		for (int i = 0; i < dailyLista.size(); i++) {
			if (dailyLista.get(i).getDataHora().isAfterNow()) {
				return dailyScrumRepositorio.consultarProximoDailyScrum(dailyLista.get(i).getCodigo());
			}
		}
		return null;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String excluirDailyScrum(DailyScrum dailyScrum) {
		String retorno = "";
		if (dailyScrum.getDataHora().isAfterNow()) {
			remove(dailyScrum);
		} else {
			throw new NegocioException(ConstantesMensagem.ERRO_EXCLUIR_DAILY_PASSADO);
		}
		return retorno;
	}
}
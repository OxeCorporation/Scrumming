package br.com.scrumming.core.manager.implementations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
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

		DateTime dataInicioSprint = dailyScrum.getSprint().getDataInicio();
		DateTime dataFimSprint = dailyScrum.getSprint().getDataFim();
		
		// Sem código (Novo daily)
		if (dailyScrum.getCodigo() == null) {
			
			// Salva apenas um novo registro.
			if (dailyScrum.isUniqueDaily()) {
				dailyScrum.setDataHora(new DateTime(dailyScrum.getDataHoraCalendar()));
				if (dailyScrum.getDataHora().isAfterNow()) {
					if (isDuplicatedDateTime(dailyScrum)) {
						// throw new NegocioException(MensagemUtil.get(ConstantesMensagem.MENSAGEM_ERRO_DUPLICIDADE_DAILY));
					} else {
						insertOrUpdate(dailyScrum);
					}
				} else {
					// throw new NegocioException(MensagemUtil.get(ConstantesMensagem.MENSAGEM_ERRO_DATA_DAILY));
				}			
			// Salva um novo registro para cada dia da Sprint.
			} else {
				if (dataInicioSprint.isAfterNow()) {
					Date data1 = dataInicioSprint.toDate();
					data1.setTime(dailyScrum.getDataHoraCalendar().getTime());
					//dataInicioSprint = new DateTime(data1);
					dailyScrum.setDataHora(dataInicioSprint);
					saveSprintDailies(dailyScrum, dataInicioSprint, dataFimSprint);
				} else {
					insertOrUpdate(dailyScrum);
					Calendar.getInstance();
					saveSprintDailies(dailyScrum, dataInicioSprint, dataFimSprint);
				}
			}
		// Com código (Alteração)
		} else {
			insertOrUpdate(dailyScrum);
			FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_REGISTRO_ALTERADO_SUCESSO);
		}
		return "";
	}

	/**
	 * Salva um Daily para cada dia de uma Sprint
	 * @param dailyScrum
	 * @param dataInicioSprint
	 * @param dataFimSprint
	 */
	private void saveSprintDailies(DailyScrum dailyScrum, DateTime dataInicioSprint, DateTime dataFimSprint) {
		int interval = Days.daysBetween(dataInicioSprint, dataFimSprint).getDays();
		List<DailyScrum> dailies = listarDailyScrumDaSprint(dailyScrum.getSprint().getCodigo());
		for (DailyScrum dailyScrum2 : dailies) {
			remove(dailyScrum2);
		}
		for (int i = 0; i <= interval; i++) {
			
			insertOrUpdate(createNewDaily(dailyScrum));
			dailyScrum.setDataHora(dailyScrum.getDataHora().plusDays(1));
		}
	}
	
	/**
	 * Verifica se existe algum Daily cadastrado no mesmo dia/hora/minuto.
	 */
	private boolean isDuplicatedDateTime(DailyScrum daily) {
		boolean duplicated = false;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<DailyScrum> lista = new ArrayList(consultarPorCampo("dataHora", daily.getDataHora()));
		for (DailyScrum dailyScrum : lista) {
			if (Minutes.minutesBetween(dailyScrum.getDataHora(), daily.getDataHora()).getMinutes() == 0) {
				duplicated = true;
			}
		}
		return duplicated;
	}
	
	private DailyScrum createNewDaily(DailyScrum dailyScrum) {
		DailyScrum retorno = new DailyScrum();
		retorno.setSprint(dailyScrum.getSprint());
		retorno.setDataHora(dailyScrum.getDataHora());
		retorno.setDataHoraCalendar(dailyScrum.getDataHoraCalendar());
		retorno.setLocal(dailyScrum.getLocal());
		retorno.setDuracao(dailyScrum.getDuracao());
		return retorno;
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
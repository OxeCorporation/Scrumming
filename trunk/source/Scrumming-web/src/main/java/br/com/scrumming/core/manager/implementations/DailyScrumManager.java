package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.joda.time.DateTime;
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

		int e;
		DateTime dataInicio = dailyScrum.getSprint().getDataInicio();
		DateTime dataFim = dailyScrum.getSprint().getDataFim();
		if (dailyScrum.getCodigo() == null) { // Sem código (Novo daily)
			// Salva apenas um registro
			if (dailyScrum.isUniqueDaily()) {
				if (dailyScrum.getDataHora().isAfterNow()) {
					saveUniqueDaily(dailyScrum);
				} else {
					FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_ERRO_DATA_DAILY);
				}
			} else {
				/*if (dataInicio.isAfterNow()) {
					e = dataInicio.getDayOfMonth();
					dailyScrum.setDataHora(dataInicio);
					save(dailyScrum, e, dataInicio, dataFim);
				} else { // Com código (Alteração Daily)
					if (dailyScrum.isUniqueDaily()) {
						
					} else {
						insertOrUpdate(dailyScrum);
						Calendar.getInstance();
						e = Calendar.DAY_OF_MONTH;
						save(dailyScrum, e, dataInicio, dataFim);
					}
				}*/
			}
		} else { // Com código (Alteração)
			insertOrUpdate(dailyScrum);
			FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_REGISTRO_ALTERADO_SUCESSO);
		}
		return "";
	}

	private void save(DailyScrum dailyScrum, int e, DateTime dataInicio, DateTime dataFim) {
		if (dailyScrum.isUniqueDaily()) {
			dailyScrum.setDataHora(new DateTime(dailyScrum.getDataHoraCalendar()));
			insertOrUpdate(dailyScrum);
		} else {
			int diaFim = dataFim.getDayOfMonth();
			for (int i = e; i < diaFim; i++) {
				insertOrUpdate(dailyScrum);
				dailyScrum.setDataHora(dataInicio.plusDays(1));
			}
		}
	}
	
	private void saveUniqueDaily(DailyScrum dailyScrum) {
		dailyScrum.setDataHora(new DateTime(dailyScrum.getDataHoraCalendar()));
		insertOrUpdate(dailyScrum);
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
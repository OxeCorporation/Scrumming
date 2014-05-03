package br.com.scrumming.core.manager.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.exceptions.NegocioException;
import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.core.manager.interfaces.ISprintManager;
import br.com.scrumming.core.manager.interfaces.ITarefaReporteManager;
import br.com.scrumming.core.repositorio.TarefaReporteRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.TarefaReporte;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;

@Service
public class TarefaReporteManager extends AbstractManager<TarefaReporte, Integer> implements ITarefaReporteManager {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private TarefaReporteRepositorio tarefaReporteRepositorio;
    @Autowired
    private ISprintManager sprintManager;
    @Autowired
    private IItemBacklogManager itemManager;

	@Override
    public AbstractRepositorio<TarefaReporte, Integer> getRepositorio() {
        return this.tarefaReporteRepositorio;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reportarHora(TarefaReporte tarefaReporte, Integer sprintID, Integer itemID) {
    	Sprint sprint = sprintManager.findByKey(sprintID);
    	ItemBacklog item = itemManager.findByKey(itemID);
    	
    	tarefaReporte.getTarefa().setItemBacklog(item);
    	    
    	validarDados(tarefaReporte, sprint);
    	insertOrUpdate(tarefaReporte);
    }
    
    private void validarDados(TarefaReporte tarefaReporte, Sprint sprint) {
    	if ((tarefaReporte.getTarefa().getSituacao() == SituacaoTarefaEnum.FEITO) ||
    		(tarefaReporte.getTarefa().getSituacao() == SituacaoTarefaEnum.CANCELADO)) {
			throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_NAO_PODE_REPORTAR_HORA_EM_TAREFA_CONCLUIDA_OU_CANCELADA);
		}
    	if (tarefaReporte.getUsuario().getCodigo() != tarefaReporte.getTarefa().getUsuario().getCodigo()) {
    		throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_NAO_PODE_REPORTAR_HORA_EM_TAREFA_NAO_ATRIBUIDA_A_VOCE);
    	}
    	if (tarefaReporte.getTempoRestante() > tarefaReporte.getTarefa().getTempoEstimado()){
    		throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_TEMPO_RESTANTE_SUPERIOR_AO_TEMPO_ESTIMADO);
    	}    	
    	if ((tarefaReporte.getDataReporte().before(sprint.getDataInicio().toDate())) ||
    		(tarefaReporte.getDataReporte().after(sprint.getDataFim().toDate()))) {
			throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_DATA_DO_REPORTE_DEVE_ESTAR_DENTRO_DO_INTERVALO_DA_SPRINT);
		}
    	if ((tarefaReporte.getTarefa().getItemBacklog().getSituacaoBacklog() == SituacaoItemBacklogEnum.ENTREGUE) ||
    		(tarefaReporte.getTarefa().getItemBacklog().getSituacaoBacklog() == SituacaoItemBacklogEnum.CANCELADO)) {
    		throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_NAO_PODE_REPORTAR_HORA_EM_TAREFA_DE_ITEM_ENTREGUE_OU_CANCELADO);
		}
	}	
    
    /* getters and setters */
    public TarefaReporteRepositorio getTarefaReporteRepositorio() {
		return tarefaReporteRepositorio;
	}

	public void setTarefaReporteRepositorio(TarefaReporteRepositorio tarefaReporteRepositorio) {
		this.tarefaReporteRepositorio = tarefaReporteRepositorio;
	}

}

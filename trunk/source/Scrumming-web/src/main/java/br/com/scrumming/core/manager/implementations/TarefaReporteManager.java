package br.com.scrumming.core.manager.implementations;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.core.manager.interfaces.ITarefaManager;
import br.com.scrumming.core.manager.interfaces.ITarefaReporteManager;
import br.com.scrumming.core.manager.interfaces.IUsuarioManager;
import br.com.scrumming.core.repositorio.TarefaReporteRepositorio;
import br.com.scrumming.core.repositorio.TarefaRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.TarefaReporte;
import br.com.scrumming.domain.Usuario;
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
    private ITarefaManager tarefaManager;
    @Autowired
    private IUsuarioManager usuarioManager;

	@Override
    public AbstractRepositorio<TarefaReporte, Integer> getRepositorio() {
        return this.tarefaReporteRepositorio;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reportarHora(TarefaReporte tarefaReporte, Integer tarefaID, Integer usuarioID) {
    	Tarefa tarefa = tarefaManager.findByKey(tarefaID);
    	Usuario usuario = usuarioManager.findByKey(usuarioID);
    	tarefaReporte.setTarefa(tarefa);
    	tarefaReporte.setUsuario(usuario);
    	    
    	//validarDados(tarefaReporte);
    	insertOrUpdate(tarefaReporte);
    }
    
    private void validarDados(TarefaReporte tarefaReporte) throws Exception {
    	if ((tarefaReporte.getTarefa().getSituacao() == SituacaoTarefaEnum.FEITO) ||
    		(tarefaReporte.getTarefa().getSituacao() == SituacaoTarefaEnum.CANCELADO)) {
			throw new Exception("Você não pode reportar hora em uma tarefa concluída ou cancelada.");
		}
    	if ((tarefaReporte.getUsuario().getCodigo() != tarefaReporte.getTarefa().getUsuario().getCodigo())) {
    		throw new Exception("Você não pode reportar hora em uma tarefa que não está atribuida para você.");
    	}
    	/*if ((dataReporte < dataInicio) || (dataReporte > dataFim)) {
			throw new Exception("A data do reporte deve estar dentro do intervalo da Sprint.");
		}
		if ((dataReporte < dataInicio) || (dataReporte > dataFim)) {
			throw new Exception("A data do reporte deve estar dentro do intervalo da Sprint.");
		}
		if (tempoRestante > tempoEstimado) {
			throw new Exception("O tempo restante não pode ser superior ao tempo estimado inicialmente para a tarefa.");
		}*/
	}	
    
    /* getters and setters */
    public TarefaReporteRepositorio getTarefaReporteRepositorio() {
		return tarefaReporteRepositorio;
	}

	public void setTarefaReporteRepositorio(TarefaReporteRepositorio tarefaReporteRepositorio) {
		this.tarefaReporteRepositorio = tarefaReporteRepositorio;
	}

}

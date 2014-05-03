package br.com.scrumming.core.manager.implementations;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.exceptions.NegocioException;
import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.core.manager.interfaces.ITarefaManager;
import br.com.scrumming.core.manager.interfaces.IUsuarioManager;
import br.com.scrumming.core.repositorio.TarefaReporteRepositorio;
import br.com.scrumming.core.repositorio.TarefaRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;

@Service
public class TarefaManager extends AbstractManager<Tarefa, Integer> implements ITarefaManager {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private TarefaRepositorio tarefaRepositorio;
    @Autowired
    private TarefaReporteRepositorio tarefaReporteRepositorio;
    @Autowired
    private IItemBacklogManager itemBacklogManager;
    @Autowired
    private IUsuarioManager usuarioManager;

	@Override
    public AbstractRepositorio<Tarefa, Integer> getRepositorio() {
        return this.tarefaRepositorio;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void salvar(Tarefa tarefa, Integer itemBacklogID) {
    	ItemBacklog itemBacklog = itemBacklogManager.findByKey(itemBacklogID);
    	tarefa.setItemBacklog(itemBacklog);
    	if (tarefa.getSituacao() == null)
    		tarefa.setSituacao(SituacaoTarefaEnum.PARA_FAZER);
    	    	
    	insertOrUpdate(tarefa);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remover(Tarefa tarefa) {
        validarDados(tarefa);
    	remove(tarefa);
    }

    private void validarDados(Tarefa tarefa) {	
    	if ((tarefa.getSituacao() != SituacaoTarefaEnum.PARA_FAZER)) {
    		throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_SO_PODE_REMOVER_TAREFA_COM_SITUACAO_PARAFAZER);
    	}
		if (tarefaReporteRepositorio.existeReporteDeHoras(tarefa.getCodigo())) {
			throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_IMPOSSIVEL_REMOVER_EXISTE_REPORTE_DE_HORAS_NA_TAREFA);
		}
		/*if (tarefaRepositorio.tarefaFoiFavoritada(tarefa.getCodigo())) {
			throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_IMPOSSIVEL_REMOVER_TAREFA_FOI_FAVORITADA);
		}*/
	}

	@Override
    public List<Tarefa> consultarPorItemBacklog(Integer itemBacklogID) {
        List<Tarefa> listaDeTarefas = tarefaRepositorio.consultarPorItemBacklog(itemBacklogID);
        return preencherNovaListaDeTarefas(listaDeTarefas);
    }
	
	private List<Tarefa> preencherNovaListaDeTarefas(List<Tarefa> listaDeTarefas) {
		List<Tarefa> novaListaDeTarefas = new ArrayList<>();
        for (Tarefa tarefa : listaDeTarefas) {
        	if (tarefa.getSituacao() == SituacaoTarefaEnum.PARA_FAZER){
        		tarefa.setSituacaoDescricao("Planejada");
        		tarefa.setBackgroundColor("background-color: grey");
        	} else if (tarefa.getSituacao() == SituacaoTarefaEnum.FAZENDO) {
        		tarefa.setSituacaoDescricao("Em progresso");
        		tarefa.setBackgroundColor("background-color: blue");
        	} else if (tarefa.getSituacao() == SituacaoTarefaEnum.FEITO) {
        		tarefa.setSituacaoDescricao("Concluída");
        		tarefa.setBackgroundColor("background-color: green");
        	} else if (tarefa.getSituacao() == SituacaoTarefaEnum.CANCELADO) {
        		tarefa.setSituacaoDescricao("Cancelada");
        		tarefa.setBackgroundColor("background-color: red");
        	} else if (tarefa.getSituacao() == SituacaoTarefaEnum.EM_IMPEDIMENTO) {
        		tarefa.setSituacaoDescricao("Em impedimento");
        		tarefa.setBackgroundColor("background-color: orange");
        	}
        	
        	novaListaDeTarefas.add(tarefa);
        }
		return novaListaDeTarefas;
	}

	@Override
	public List<Tarefa> consultarPorItemBacklogIhSituacao(Integer itemBacklogID, SituacaoTarefaEnum situacao) {		
		List<Tarefa> listaDeTarefas = tarefaRepositorio.consultarPorItemBacklogIhSituacao(itemBacklogID, situacao);
        return preencherNovaListaDeTarefas(listaDeTarefas);
	}
	
	@Override
	public List<Tarefa> consultarPorItemBacklogIhNotSituacao(Integer itemBacklogID, SituacaoTarefaEnum situacao) {
		return preencherNovaListaDeTarefas(tarefaRepositorio.consultarPorItemBacklogIhNotSituacao(itemBacklogID, situacao));
	}
    
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void atribuirPara(Tarefa tarefa, Integer itemBacklogID, Integer usuarioID) {
		ItemBacklog itemBacklog = itemBacklogManager.findByKey(itemBacklogID);
    	tarefa.setItemBacklog(itemBacklog);
    	
		Usuario usuario = usuarioManager.findByKey(usuarioID);
		tarefa.setUsuario(usuario);
		
		if (usuario != null){
			tarefa.setDataAtribuicao(new DateTime());
		} else {
			tarefa.setDataAtribuicao(null);
		}
    	
		insertOrUpdate(tarefa);
    }
    
    /* getters and setters */
    public TarefaRepositorio getTarefaRepositorio() {
		return tarefaRepositorio;
	}

	public void setTarefaRepositorio(TarefaRepositorio tarefaRepositorio) {
		this.tarefaRepositorio = tarefaRepositorio;
	}

	public TarefaReporteRepositorio getTarefaReporteRepositorio() {
		return tarefaReporteRepositorio;
	}

	public void setTarefaReporteRepositorio(TarefaReporteRepositorio tarefaReporteRepositorio) {
		this.tarefaReporteRepositorio = tarefaReporteRepositorio;
	}

}

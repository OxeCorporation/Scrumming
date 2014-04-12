package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;

@Repository
public class TarefaRepositorio extends AbstractRepositorio<Tarefa, Integer>{
			
	@SuppressWarnings("unchecked")
    public List<Tarefa> consultarPorItemBacklog(Integer itemBacklogID) {
        Criteria criteria = createCriteria();
        criteria.createAlias("itemBacklog", "itemBacklog");
        criteria.add(Restrictions.eq("itemBacklog.codigo", itemBacklogID));
        criteria.add(Restrictions.not(Restrictions.eq("situacao", SituacaoTarefaEnum.CANCELADO)));
        return Collections.checkedList(criteria.list(), Tarefa.class);
    }
	
	@SuppressWarnings("unchecked")
	public boolean existeReporteDeHorasNaTarefa(int tarefaID) {
		Criteria criteria = createCriteria();
		criteria.createAlias("reporteTarefa", "reporteTarefa");
		criteria.add(Restrictions.eq("reporteTarefa.tarefa", tarefaID));
		return !Collections.checkedList(criteria.list(), Tarefa.class).isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	public boolean tarefaFoiFavoritada(int tarefaID) {
		Criteria criteria = createCriteria();
		criteria.createAlias("tarefaFavorita", "tarefaFavorita");
		criteria.add(Restrictions.eq("tarefaFavorita.tarefa", tarefaID));
		return !Collections.checkedList(criteria.list(), Tarefa.class).isEmpty();
	}
	
	/*@SuppressWarnings("unchecked")
    public List<Tarefa> consultarPorUsuario(Usuario usuario) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("FK_usuario", usuario));
        criteria.addOrder(Order.asc("FK_usuario"));
        return Collections.checkedList(criteria.list(), Tarefa.class);
    }
	
	@SuppressWarnings("unchecked")
    public List<Tarefa> consultarPorSituacao(int situacao) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("situacao", situacao));
        criteria.addOrder(Order.asc("situacao"));
        return Collections.checkedList(criteria.list(), Tarefa.class);
    }
	
	@SuppressWarnings("unchecked")
    public List<Tarefa> consultarPorQualquerParteDaDescricao(String descricao) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.like("descricao", descricao, MatchMode.ANYWHERE).ignoreCase());
        criteria.addOrder(Order.asc("descricao"));
        return Collections.checkedList(criteria.list(), Tarefa.class);
    }*/

}
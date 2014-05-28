package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.TarefaReporte;

@Repository
public class TarefaReporteRepositorio extends AbstractRepositorio<TarefaReporte, Integer>{
			
		
	@SuppressWarnings("unchecked")
	public boolean existeReporteDeHoras(int tarefaID) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("tarefa.codigo", tarefaID));
		return !Collections.checkedList(criteria.list(), TarefaReporte.class).isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	public List<TarefaReporte> totalDeHorasReportadasNasTarefasDoItem(Integer itemBacklogID) {
        Criteria criteria = createCriteria();
        criteria.createAlias("tarefa", "tarefaAlias");
        criteria.add(Restrictions.eq("tarefaAlias.itemBacklog.codigo", itemBacklogID));
		criteria.setProjection(Projections.sum("tempoReportado"));		
        
        return Collections.checkedList(criteria.list(), TarefaReporte.class);
    }
}
package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.TarefaDTO;
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
	public List<TarefaDTO> totalDeHorasReportadasNasTarefasDoItem(Integer itemBacklogID) {
        Criteria criteria = createCriteria();
        criteria.createAlias("tarefa", "tarefaAlias");
        criteria.add(Restrictions.eq("tarefaAlias.itemBacklog.codigo", itemBacklogID));		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sum("tempoReportado"))
				.add(Projections.groupProperty("tarefa")));
        
        return Collections.checkedList(criteria.list(), TarefaDTO.class);
    }
	
	@SuppressWarnings("unchecked")
	public List<TarefaReporte> consultarTarefaReportePorTarefa(Integer tarefaID) {
        Criteria criteria = createCriteria();
        criteria.createAlias("tarefa", "tarefaAlias");
        criteria.add(Restrictions.eq("tarefaAlias.codigo", tarefaID));		
//		criteria.setProjection(Projections.projectionList()
//				.add(Projections.sum("tempoReportado"))
//				.add(Projections.groupProperty("tarefa")));
        
        return Collections.checkedList(criteria.list(), TarefaReporte.class);
    }
}
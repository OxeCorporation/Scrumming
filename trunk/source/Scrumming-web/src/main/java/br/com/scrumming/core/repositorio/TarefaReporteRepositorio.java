package br.com.scrumming.core.repositorio;

import java.util.Collections;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.TarefaReporte;

@Repository
public class TarefaReporteRepositorio extends AbstractRepositorio<TarefaReporte, Integer>{
			
		
	@SuppressWarnings("unchecked")
	public boolean existeReporteDeHoras(int tarefaID) {
		Criteria criteria = createCriteria();
		//criteria.createAlias("tarefaReporte", "tarefaReporte");
		criteria.add(Restrictions.eq("tarefa.codigo", tarefaID));
		return !Collections.checkedList(criteria.list(), TarefaReporte.class).isEmpty();
	}
}
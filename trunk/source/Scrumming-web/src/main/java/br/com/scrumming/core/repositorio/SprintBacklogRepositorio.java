package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;
import br.com.scrumming.domain.SprintBacklogChave;
import br.com.scrumming.domain.Tarefa;

@Repository
public class SprintBacklogRepositorio extends AbstractRepositorio<SprintBacklog, SprintBacklogChave> {

	/**
	 * Efetua uma consulta de um item da SprintBacklog pela combinação de sua chave composta.
	 * @param sprint
	 * @param itemBacklog
	 * @return
	 */
	public SprintBacklog consultaPorChaveComposta(Sprint sprint, ItemBacklog itemBacklog) {
		
		Criteria criteria = createCriteria();
        criteria.createAlias("sprint", "sprint");
        criteria.add(Restrictions.eq("sprint.codigo", sprint.getChave()));
        criteria.createAlias("itemBacklog", "itemBacklog");
        criteria.add(Restrictions.eq("itemBacklog.codigo", itemBacklog.getChave()));
        return (SprintBacklog) criteria.uniqueResult();
	}
	
	/**
	 * Efetua uma consulta de um item da SprintBacklog pela combinação de sua chave composta.
	 * @param sprint
	 * @param itemBacklog
	 * @return
	 */
	public SprintBacklog consultaAtivosPorChaveComposta(Sprint sprint, ItemBacklog itemBacklog) {
		
		Criteria criteria = createCriteria();
        criteria.createAlias("sprint", "sprint");
        criteria.add(Restrictions.eq("sprint.codigo", sprint.getChave()));
        criteria.createAlias("itemBacklog", "itemBacklog");
        criteria.add(Restrictions.eq("itemBacklog.codigo", itemBacklog.getChave()));
        criteria.add(Restrictions.eq("isAtivo", true));
        return (SprintBacklog) criteria.uniqueResult();
	}
	
	/**
	 * Retorna um SprintBacklog ativo por itembacklog.
	 * @param item
	 * @return
	 */
	@SuppressWarnings(UNCHECKED)
	public List<SprintBacklog> consultarAtivoPorItem(ItemBacklog item) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("itemBacklog", item));
		criteria.add(Restrictions.eq("isAtivo", true));
		return Collections.checkedList(criteria.list(), SprintBacklog.class);
	}
	
	/**
	 * Consulta uma lista de sprintbacklog ativos por sprint
	 * @param sprint
	 * @return
	 */
	@SuppressWarnings(UNCHECKED)
	public List<SprintBacklog> listarAtivosPorSprint(Sprint sprint) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("sprint", sprint));
		criteria.add(Restrictions.eq("isAtivo", true));
		return Collections.checkedList(criteria.list(), SprintBacklog.class);
	}
	
	/**
	 * Consulta os Itens Backlog ativos de uma sprint.	
	 * @param sprintID
	 * @return
	 */
	@SuppressWarnings(UNCHECKED)
	public List<ItemBacklog> consultarItensSprintBacklogPorSprint(Integer sprintID) {
        Criteria criteria = createCriteria();
        criteria.createAlias("sprint", "sprint");
        criteria.add(Restrictions.eq("sprint.codigo", sprintID));
        criteria.setProjection(Projections.property("itemBacklog"));
        return Collections.checkedList(criteria.list(), ItemBacklog.class);
	}
	
	@SuppressWarnings(UNCHECKED)
	public List<SprintBacklog> consultarTarefasPorSprint(Integer sprintID){
		
		Criteria criteria = createCriteria();
		criteria.createAlias("itemBacklog", "itemBacklogAlias");
		criteria.createAlias("sprint", "sprintAlias");
		criteria.createAlias("itemBacklogAlias.tarefas", "tarefa");
		criteria.add(Restrictions.eq("sprintAlias.codigo", sprintID));
		return Collections.checkedList(criteria.list(), Tarefa.class);
	}
	
	public Long totalDeHorasEstimadasDaSprint(Integer sprintID){		
		Criteria criteria = createCriteria();
		criteria.createAlias("itemBacklog", "itemBacklogAlias");
		criteria.createAlias("sprint", "sprintAlias");
		criteria.createAlias("itemBacklogAlias.tarefas", "tarefaAlias");
		criteria.setProjection(Projections.sum("tarefaAlias.tempoEstimado"));
		criteria.add(Restrictions.eq("sprintAlias.codigo", sprintID));		
		
		return (Long) criteria.uniqueResult();
	}
	
	public Long totalDeHorasRestantesDaSprintPorData(Integer sprintID, String data){
		
		//Pegar horas de tarefas q foram reportadas
 		String sql = "SELECT min(tr.tempo_restante) as tempoRestante " +
				     "FROM SprintBacklog sb " +
				     "	INNER JOIN ItemBacklog i ON sb.FK_backlog = i.PK_backlog " +
				     "	INNER JOIN Tarefa t ON i.PK_backlog = t.FK_itemBacklog " +
				     "	INNER JOIN TarefaReporte tr ON t.PK_tarefa = tr.FK_tarefa " +
				     "	INNER JOIN Sprint s ON sb.FK_sprint = s.PK_sprint " +
				     "WHERE s.PK_sprint = :sprint " +
					 "	AND tr.data_reporte <= :data " +
					 "GROUP BY tr.FK_tarefa";
		
		Query query = getSession().createSQLQuery(sql);
		query.setParameter("sprint", sprintID);
		query.setParameter("data", data);
		
		List<Integer> valores = query.list();
		
		if (valores.size() == 0) {
			return null;
		}
		
		int tempoRestante = 0;
		for (int i = 0; i < valores.size(); i++) {
			tempoRestante = tempoRestante + valores.get(i);
		}
		
		//Pegar horas de tarefas q ainda nao foram reportadas
		sql = "SELECT t.tempo_estimado as tempo_estimado " +
		      "FROM SprintBacklog sb " +
		      "	INNER JOIN ItemBacklog i ON sb.FK_backlog = i.PK_backlog " +
		      "	INNER JOIN Tarefa t ON i.PK_backlog = t.FK_itemBacklog " +
		      "	INNER JOIN Sprint s ON sb.FK_sprint = s.PK_sprint " +
		      "WHERE s.PK_sprint = :sprint " +
			  "		AND t.PK_tarefa not in " +
			  "  (SELECT FK_tarefa " +
			  "	  FROM SprintBacklog sb " +
			  "		  INNER JOIN ItemBacklog i ON sb.FK_backlog = i.PK_backlog 	" +
			  "		  INNER JOIN Tarefa t ON i.PK_backlog = t.FK_itemBacklog 	" +
			  "		  INNER JOIN TarefaReporte tr ON t.PK_tarefa = tr.FK_tarefa 	" +
			  "		  INNER JOIN Sprint s ON sb.FK_sprint = s.PK_sprint " +
			  "		  WHERE s.PK_sprint = :sprint " +
			  "		  GROUP BY tr.FK_tarefa) " +
			  "GROUP BY t.PK_tarefa";
	
		Query queryN = getSession().createSQLQuery(sql);
		queryN.setParameter("sprint", sprintID);
		
		List<Integer> valoresNaoReportados = queryN.list();
		
		for (int i = 0; i < valoresNaoReportados.size(); i++) {
			tempoRestante = tempoRestante + valoresNaoReportados.get(i);
		}
		
		return (long) tempoRestante;		
	}
}
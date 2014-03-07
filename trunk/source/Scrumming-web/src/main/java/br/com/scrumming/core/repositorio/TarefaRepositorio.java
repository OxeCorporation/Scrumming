package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.Usuario;

@Repository
public class TarefaRepositorio extends AbstractRepositorio<Tarefa, Integer>{
	
	@SuppressWarnings("unchecked")
    public List<Tarefa> consultarPorQualquerParteDaDescricao(String descricao) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.like("descricao", descricao, MatchMode.ANYWHERE).ignoreCase());
        criteria.addOrder(Order.asc("descricao"));
        return Collections.checkedList(criteria.list(), Tarefa.class);
    }
	
	@SuppressWarnings("unchecked")
    public List<Tarefa> consultarPorItemBacklog(ItemBacklog itemBacklog) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("FK_itemBacklog", itemBacklog));
        criteria.addOrder(Order.asc("FK_itemBacklog"));
        return Collections.checkedList(criteria.list(), Tarefa.class);
    }
	
	@SuppressWarnings("unchecked")
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

}

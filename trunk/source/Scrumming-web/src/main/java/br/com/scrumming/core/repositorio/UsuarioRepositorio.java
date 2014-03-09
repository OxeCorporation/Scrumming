package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.Usuario;

@Repository
public class UsuarioRepositorio extends AbstractRepositorio<Usuario, Integer> {

    @SuppressWarnings("unchecked")
    public List<Usuario> consultarPorNome(String nome) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
        criteria.addOrder(Order.asc("nome"));
        return Collections.checkedList(criteria.list(), Usuario.class);
    }

    public Usuario consultarPorLoginSenha(String login, String senha) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("login", login));
        criteria.add(Restrictions.eq("senha", senha));
        return (Usuario) criteria.uniqueResult();
    }
}
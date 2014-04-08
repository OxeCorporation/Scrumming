package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;

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
        Criteria criteria = createCriteria(UsuarioEmpresa.class);
        criteria.createAlias("usuario","usuarioAlias");
        criteria.createAlias("empresa","empresaAlias");
        criteria.add(Restrictions.eq("usuarioAlias.login", login));
        criteria.add(Restrictions.eq("usuarioAlias.senha", senha));
        criteria.add(Restrictions.eq("ativo", true));
        criteria.setProjection(Projections.property("usuario"));
        return (Usuario) criteria.uniqueResult();
    }
}
package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.Usuario;

@Repository
public class TeamRepositorio extends AbstractRepositorio<Team, Integer> {
	/**
	 * Consulta de usuários por projeto
	 * 
	 * @param projeto
	 * @return coleção de usuários
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> consultarUsuarioPorProjeto(Integer usuarioID) {
		Criteria criteria = createCriteria();
		criteria.createAlias("usuario", "usuario");
		criteria.add(Restrictions.eq("usuario.codigo", usuarioID));
		return Collections.checkedList(criteria.list(), Usuario.class);
	}
}
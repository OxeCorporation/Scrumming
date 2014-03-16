package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.spel.ast.Projection;
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
	
	@SuppressWarnings("unchecked")
	public List<Usuario> consultar(Integer projetoID, Integer empresaID){
		
		Criteria criteria = createCriteria();
		criteria.createAlias("projeto", "projetoAlias");
		criteria.createAlias("empresa", "empresaAlias");
		
		criteria.add(Restrictions.eq("projetoAlias.codigo", projetoID));
		criteria.add(Restrictions.eq("empresaAlias.codigo", empresaID));
		
		return Collections.checkedList(criteria.list(), Usuario.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Team> consultaTeamPorProjeto(Integer projetoID){
		
		Criteria criteria = createCriteria();
		criteria.createAlias("projeto", "projetoAlias");
		
		criteria.add(Restrictions.eq("projetoAlias.codigo", projetoID));
		
		criteria.setProjection(Projections.property("usuario"));
		
		return Collections.checkedList(criteria.list(), Team.class);
	}
	
}
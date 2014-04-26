package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.domain.UsuarioEmpresaChave;

@Repository
public class UsuarioEmpresaRepositorio extends AbstractRepositorio<UsuarioEmpresa, UsuarioEmpresaChave> {
	
	private static final String UNCHECKED = "unchecked";

	@SuppressWarnings(UNCHECKED)
	public List<Usuario> consultarUsuarioPorEmpresa(Integer empresaID) {
        Criteria criteria = criarAliasUsuarioEmpresa();
        criteria.addOrder(Order.asc("usuarioAlias.nome"));
        criteria.add(Restrictions.eq("empresaAlias.codigo", empresaID));
        criteria.add(Restrictions.eq("usuarioAlias.empresa", false));
        criteria.setProjection(Projections.property("usuario"));
        return Collections.checkedList(criteria.list(), Usuario.class);
	}

	private Criteria criarAliasUsuarioEmpresa() {
		Criteria criteria = createCriteria();
        criteria.createAlias("empresa", "empresaAlias");
        criteria.createAlias("usuario", "usuarioAlias");
		return criteria;
	}
	
	@SuppressWarnings(UNCHECKED)
	public List<Empresa> consultarEmpresaPorUsuario(Integer usuarioID){
		Criteria criteria = criarAliasUsuarioEmpresa();
		criteria.addOrder(Order.asc("empresaAlias.nome"));
		criteria.add(Restrictions.eq("usuarioAlias.codigo", usuarioID));
		criteria.add(Restrictions.eq("ativo", true));
		criteria.setProjection(Projections.property("empresa"));
		return Collections.checkedList(criteria.list(), Empresa.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> consultarUsuarioPorProjeto(Integer projetoID) {
		Criteria criteria = createCriteria();
		criteria.createAlias("projeto", "projetoAlias");
		criteria.createAlias("usuario", "usuarioAlias");
        criteria.addOrder(Order.asc("usuarioAlias.nome"));
        criteria.add(Restrictions.eq("projetoAlias.codigo", projetoID));
        criteria.add(Restrictions.eq("isAtivo", true));
        criteria.setProjection(Projections.property("usuario"));
        return Collections.checkedList(criteria.list(), Usuario.class);
	}
}
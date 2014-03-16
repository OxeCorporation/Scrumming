package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.UsuarioEmpresa;

@Repository
public class UsuarioEmpresaRepositorio extends AbstractRepositorio<UsuarioEmpresa, Integer> {
	
	@SuppressWarnings("unchecked")
	public List<UsuarioEmpresa> consultarUsuarioPorEmpresa(Integer empresaID) {
        Criteria criteria = createCriteria();
        criteria.createAlias("empresa", "empresa");
        criteria.add(Restrictions.eq("empresa.codigo", empresaID));
        criteria.add(Restrictions.eq("isUsuarioEmpresa", new Integer(1)));
        return Collections.checkedList(criteria.list(), UsuarioEmpresa.class);
	}

}
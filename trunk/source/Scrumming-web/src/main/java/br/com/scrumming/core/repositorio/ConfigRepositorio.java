package br.com.scrumming.core.repositorio;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.Config;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.enuns.ConfigEnum;

@Repository
public class ConfigRepositorio extends AbstractRepositorio<Config, Integer> {

	/**
	 * Consulta uma determinada configuração pelo seu nome filtrado pela empresa.
	 * @param configEnum Nome da configuração
	 * @param empresa Empresa
	 * @return Configuração da empresa.
	 */
	public Config consultarNomeConfig(ConfigEnum configEnum, Empresa empresa) {
		Criteria criteria = createCriteria();
		criteria.createAlias("empresa", "empresa");
        criteria.add(Restrictions.eq("codigoConfig", configEnum));
        criteria.add(Restrictions.eq("empresa.codigo", empresa.getCodigo()));
        return (Config) criteria.uniqueResult();
	}
}
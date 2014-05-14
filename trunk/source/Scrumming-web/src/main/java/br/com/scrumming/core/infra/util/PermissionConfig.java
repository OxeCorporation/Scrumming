package br.com.scrumming.core.infra.util;

import br.com.scrumming.core.repositorio.ConfigRepositorio;
import br.com.scrumming.domain.Config;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.enuns.ConfigEnum;
import br.com.scrumming.domain.enuns.PerfilUsuarioEnum;

public class PermissionConfig {
	
	private static ConfigRepositorio repositorio;

	/**
	 * Verifica se o usuário tem permissão em uma determinada configuração do sistema.
	 * @param time
	 * @param configEnum
	 * @return
	 */
	public static boolean verifyPermission(Team time, ConfigEnum configEnum) {
		boolean allowed = false;
		try {
			time.getUsuario();
			
			Config config = getConfig(configEnum, time.getEmpresa());
			PerfilUsuarioEnum perfil = time.getPerfilUsuario();
			switch (perfil) {
				case PRODUCT_OWNER:
					allowed = config.isPerfilOwner();
					break;
				
				case SCRUM_MASTER:
					allowed = config.isPerfilMaster();
					break;
								
				case TEAM:
					allowed = config.isPerfilTeam();
					break;
			}
		} catch (NullPointerException e) {
			allowed = false;
		}
		return allowed;
	}
	
	/**
	 * Busca a configuração filtrando pela empresa.
	 * @param configEnum
	 * @param empresa
	 * @return
	 */
	private static Config getConfig(ConfigEnum configEnum, Empresa empresa) {		
		repositorio = new ConfigRepositorio();
		return repositorio.consultarNomeConfig(configEnum, empresa);
	}
}
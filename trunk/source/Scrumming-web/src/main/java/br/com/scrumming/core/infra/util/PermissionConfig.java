package br.com.scrumming.core.infra.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.repositorio.ConfigRepositorio;
import br.com.scrumming.domain.Config;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.enuns.ConfigEnum;
import br.com.scrumming.domain.enuns.PerfilUsuarioEnum;

@Service
public class PermissionConfig {
	
	@Autowired
	private ConfigRepositorio repositorio;

	/**
	 * Verifica se o usuário tem permissão em uma determinada configuração do sistema.
	 * @param time
	 * @param configEnum
	 * @return
	 */
	private static PermissionConfig permissionConfig = null;
	
	@Transactional(readOnly = true)
	public boolean verifyPermission(Team time, ConfigEnum configEnum) {
		boolean allowed = false;
			
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
		return allowed;
	}
	
	public static PermissionConfig getInstance(){
		if(permissionConfig == null){
			permissionConfig = new PermissionConfig();
		}
		return permissionConfig;
	}
	/**
	 * Busca a configuração filtrando pela empresa.
	 * @param configEnum
	 * @param empresa
	 * @return
	 */
	
	private Config getConfig(ConfigEnum configEnum, Empresa empresa) {		
		return repositorio.consultarNomeConfig(configEnum, empresa);
	}

	public ConfigRepositorio getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(ConfigRepositorio repositorio) {
		this.repositorio = repositorio;
	}
	
}
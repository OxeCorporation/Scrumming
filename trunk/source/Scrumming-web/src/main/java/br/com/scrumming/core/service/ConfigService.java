package br.com.scrumming.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.infra.util.PermissionConfig;
import br.com.scrumming.domain.ConfigDTO;

@RestController
@RequestMapping("/config")
public class ConfigService {
 
	@Autowired
	private PermissionConfig permissonConfig;
	
	@RequestMapping(method = RequestMethod.POST, value = "/verificar_permissao")
    public boolean consultarSprintDTO(@RequestBody ConfigDTO configDTO) {
    	return permissonConfig.verifyPermission(configDTO.team, configDTO.configEnum);
    }

	/*Getters & Setters*/
	
	public PermissionConfig getPermissonConfig() {
		return permissonConfig;
	}

	public void setPermissonConfig(PermissionConfig permissonConfig) {
		this.permissonConfig = permissonConfig;
	}
}
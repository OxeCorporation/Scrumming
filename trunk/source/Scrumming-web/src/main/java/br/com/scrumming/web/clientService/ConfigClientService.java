package br.com.scrumming.web.clientService;

import br.com.scrumming.domain.ConfigDTO;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.enuns.ConfigEnum;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class ConfigClientService extends AbstractClientService {
	
	/**
	 * Consulta para objer e exibir os dados da tela de cadastro da Sprint.
	 * @param sprintID Chave da Sprint.
	 * @return Objeto DTO que representa os dados da tela da Sprint.
	 */
	public boolean verificarPermissao(Team time, ConfigEnum configEnum) {
		
		ConfigDTO configDTO = new ConfigDTO();
		configDTO.setTeam(time);
		configDTO.setConfigEnum(configEnum);
		return getRestTemplate().postForObject(getURIService(ConstantesService.Config.URL_VERIFICAR_PERMISSAO), configDTO, Boolean.class);
	}
}
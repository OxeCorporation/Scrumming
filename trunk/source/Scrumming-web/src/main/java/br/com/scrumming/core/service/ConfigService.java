package br.com.scrumming.core.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.scrumming.core.infra.util.PermissionConfig;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.enuns.ConfigEnum;

@RestController
@RequestMapping("/config")
public class ConfigService {
 
    @RequestMapping(method = RequestMethod.GET, value = "/verificar_permissao/{time}/{configEnum}")
    public boolean consultarSprintDTO(@PathVariable Team time, @PathVariable ConfigEnum configEnum) {
    	return PermissionConfig.verifyPermission(time, configEnum);
    }
}
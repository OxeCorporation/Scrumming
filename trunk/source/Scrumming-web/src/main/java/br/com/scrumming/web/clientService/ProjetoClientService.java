package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.ProjetoDTO;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class ProjetoClientService extends AbstractClientService {

	public String salvarProjeto(ProjetoDTO projetoDTO) {
		return getRestTemplate().postForObject(getURIService(ConstantesService.Projeto.URL_SALVAR_PROJETO), projetoDTO, String.class);
	}

	public List<Projeto> consultarProjetosPorEmpresa(Integer empresaID) {
		return Arrays.asList(getRestTemplate().getForObject(getURIService(ConstantesService.Projeto.URL_CONSULTAR_POR_EMPRESA), Projeto[].class, empresaID));
	}

	public String deletarProjeto(Integer projetoID) {
		return getRestTemplate().postForObject(getURIService(ConstantesService.Projeto.URL_DELETE_PROJETO), projetoID, String.class);
	}

}
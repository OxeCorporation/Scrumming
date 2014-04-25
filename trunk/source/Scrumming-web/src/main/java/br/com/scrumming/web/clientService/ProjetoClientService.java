package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.ProjetoDTO;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class ProjetoClientService extends AbstractClientService {

	public String salvarProjeto(ProjetoDTO projetoDTO) {
		return getRestTemplate().postForObject(getURIService(ConstantesService.Projeto.URL_SALVAR_PROJETO), projetoDTO, String.class);
	}

	public String concluirProjeto(Projeto projeto) {
		return getRestTemplate().postForObject(getURIService(ConstantesService.Projeto.URL_CONCLUIR_PROJETO), projeto, String.class);
	}

	public ProjetoDTO consultarProjtoDTO(Integer projetoID) {
		return getRestTemplate().getForObject(getURIService(ConstantesService.Projeto.URL_CONSULTAR_PROJETO_DTO), ProjetoDTO.class, projetoID);
	}

	public List<Projeto> consultarProjetosPorEmpresa(Integer empresaID) {
		return Arrays.asList(getRestTemplate().getForObject(getURIService(ConstantesService.Projeto.URL_CONSULTAR_POR_EMPRESA), Projeto[].class, empresaID));
	}

	public List<Projeto> consultarProjetosAtivosPorEmpresa(Integer empresaID) {
		return Arrays.asList(getRestTemplate().getForObject(getURIService(ConstantesService.Projeto.URL_CONSULTAR_ATIVOS_POR_EMPRESA), Projeto[].class, empresaID));
	}

	public List<Projeto> consultarProjetosConcluidosPorEmpresa(Integer empresaID) {
		return Arrays.asList(getRestTemplate().getForObject(getURIService(ConstantesService.Projeto.URL_CONSULTAR_CONCLUIDOS_POR_EMPRESA), Projeto[].class, empresaID));
	}

	public String deletarProjeto(Integer projetoID) {
		return getRestTemplate().postForObject(getURIService(ConstantesService.Projeto.URL_DELETE_PROJETO), projetoID, String.class);
	}
	
	public List<Usuario> consultarUsuarioPorEmpresaForaDoProjeto(Integer projetoID, Integer empresaID){
		return Arrays.asList(getRestTemplate().getForObject(getURIService(ConstantesService.Projeto.URL_CONSULTAR_POR_USURIO_EMPRESA_NOTPROJETO), Usuario[].class, projetoID, empresaID));
	}

	public List<Usuario> consultarUsuarioPorEmpresa(Integer empresaID){
		return Arrays.asList(getRestTemplate().getForObject(getURIService(ConstantesService.Projeto.URL_CONSULTAR_POR_USURIO_EMPRESA), Usuario[].class, empresaID));
	}

}
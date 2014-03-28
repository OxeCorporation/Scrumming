package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.Empresa;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class EmpresaClientService extends AbstractClientService {

	/**
	 * Consultar Empresas pelo nome
	 * @param Nome da Empresa
	 * @return Uma lista de Empresas
	 */
	public List<Empresa> consultarPorNome(String nome) {
		return Arrays.asList(getRestTemplate().getForObject(
				ConstantesService.Empresa.CONSULTAR_POR_NOME, Empresa[].class,
				nome));
	}
	
	/**
	 * Consultar Empresas pelo código
	 * @param Nome da Empresa
	 * @return Uma lista de Empresas
	 */
	public List<Empresa> consultarPorCodigo(Integer empresaID) {
		return Arrays.asList(getRestTemplate().getForObject(
				ConstantesService.Empresa.CONSULTAR_POR_CODIGO, Empresa[].class,
				empresaID));
	}
	
	/**
	 * Listar todas as Empresas
	 * @return Uma lista de Empresas
	 */
	public List<Empresa> listarTodas() {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Empresa[]> listaEmpresas = restTemplate.getForEntity(
				ConstantesService.Empresa.LISTAR_TODAS_EMPRESAS,
				Empresa[].class);
		return Arrays.asList(listaEmpresas.getBody());
	}
	
	/**
	 * Salvar uma empresa
	 * @param Empresa
	 * @return void
	 */
	public void salvarEmpresa(Empresa empresa) {
		getRestTemplate().postForObject(
				getURIService(ConstantesService.Empresa.SALVAR_EMPRESA),
				empresa, void.class);
	}
}
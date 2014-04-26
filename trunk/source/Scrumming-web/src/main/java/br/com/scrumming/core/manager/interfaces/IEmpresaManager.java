package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.EmpresaDTO;

public interface IEmpresaManager extends IManager<Empresa, Integer> {
	
	void salvar(EmpresaDTO empresaDTO);
	
	void atualizarEmpresa(Empresa empresa);

	List<Empresa> consultarPorNome(String nome);
	
	List<Empresa> consultarPorCodigo(Integer empresaID);
}
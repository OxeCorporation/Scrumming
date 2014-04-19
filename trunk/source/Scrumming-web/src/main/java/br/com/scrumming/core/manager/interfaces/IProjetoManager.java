package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import org.joda.time.DateTime;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.ProjetoDTO;

public interface IProjetoManager extends IManager<Projeto, Integer> {

	String salvarProjeto(ProjetoDTO projetoDTO);
	ProjetoDTO consultarProjetoDTO(Integer projetoID);
    List<Projeto> consultarPorNome(String nome);
    List<Projeto> consultarPorPeriodo(DateTime dataInicio, DateTime dataFim);
    List<Projeto> consultarPorEmpresa(Integer empresaID);
}
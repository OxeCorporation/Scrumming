package br.com.scrumming.core.manager.interfaces;

import java.util.List;
import org.joda.time.DateTime;
import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Projeto;

public interface IProjetoManager extends IManager<Projeto, Integer> {

    List<Projeto> consultarPorNome(String nome);
    List<Projeto> consultarPorPeriodo(DateTime dataInicio, DateTime dataFim);
}
package br.com.scrumming.core.manager.interfaces;

import java.util.List;
import org.joda.time.DateTime;
import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Sprint;

public interface ISprintManager extends IManager<Sprint, Integer> {

    List<Sprint> consultarPorNome(String nome);
    List<Sprint> consultarPorPeriodo(DateTime dataInicio, DateTime dataFim);
}
package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IProjetoManager;
import br.com.scrumming.core.manager.interfaces.ITeamManager;
import br.com.scrumming.core.repositorio.ProjetoRepositorio;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.Usuario;

@Service
public class ProjetoManager extends AbstractManager<Projeto, Integer> implements IProjetoManager {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private ProjetoRepositorio projetoRepositorio;
    
	@Autowired
	private ITeamManager iTeamManage;


    @Override
    public AbstractRepositorio<Projeto, Integer> getRepositorio() {
        return this.projetoRepositorio;
    }

	@Override
	public void salvarProjeto(Projeto projeto, List<Team> team, List<Usuario> usuarioEmpresa) {

		// Persiste o objeto Projeto e retorna a chave.
		Integer projetoID = insertOrUpdate(projeto);
		
		// Caso sera inserido ou alterado a Sprint
		if (projetoID != null) {
			
			// Busca o objeto persistido pela chave.
			Projeto projetoPersistido = findByKey(projetoID);
			
			if (CollectionUtils.isNotEmpty(team)) {
				iTeamManage.associarTeamProjeto(projetoPersistido, team);
			}
		}
	}

    @Override
    public List<Projeto> consultarPorNome(String nome) {
        return projetoRepositorio.consultarPorNome(nome);
    }
    
    @Override
	public List<Projeto> consultarPorPeriodo(DateTime dataInicio, DateTime dataFim) {
		return projetoRepositorio.consultarPorPeriodo(dataInicio, dataFim);
	}

    /* getters and setters */
    public ProjetoRepositorio getProjetoRepositorio() {
        return projetoRepositorio;
    }

    public void setProjetoRepositorio(ProjetoRepositorio projetoRepositorio) {
        this.projetoRepositorio = projetoRepositorio;
    }
}
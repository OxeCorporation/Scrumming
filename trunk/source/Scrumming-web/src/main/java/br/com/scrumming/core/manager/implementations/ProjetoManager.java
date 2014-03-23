package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IProjetoManager;
import br.com.scrumming.core.repositorio.ProjetoRepositorio;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.ProjetoDTO;
import br.com.scrumming.domain.Team;

@Service
public class ProjetoManager extends AbstractManager<Projeto, Integer> implements IProjetoManager {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private ProjetoRepositorio projetoRepositorio;
    
	@Autowired
	private TeamManager teamManage;

    @Override
    public AbstractRepositorio<Projeto, Integer> getRepositorio() {
        return this.projetoRepositorio;
    }

	@Override
	public String salvarProjeto(ProjetoDTO projetoDTO) {

		String retorno = "";
		Projeto projeto = projetoDTO.getProjeto();
		List<Team> team = projetoDTO.getTimeProjeto();
		// Persiste o objeto Projeto e retorna a chave.
		Integer projetoID = insertOrUpdate(projeto);
		
		if (projetoID != null) {
			retorno = "Registro foi salvo";
			// Busca o objeto persistido pela chave.
			Projeto projetoPersistido = findByKey(projetoID);
			
			if (CollectionUtils.isNotEmpty(team)) {
				teamManage.associarTeamProjeto(projetoPersistido, team);
			}
		}
		
		return retorno;
	}

	public ProjetoDTO consultarProjetoDTO(Integer projetoID) {
		
		// Cria o DTO que será enviado à tela para exibição
		ProjetoDTO projetoDTO = new ProjetoDTO();
		// Seta o Projeto
		projetoDTO.setProjeto(findByKey(projetoID));
		// Seta a lista de Times do projeto
		projetoDTO.setTimeProjeto(teamManage.consultaTeamPorProjeto(projetoID));
		// Pesquisa todos os usuarios da empresa
		//projetoDTO.setUsuarioEmpresa(iUsuarioEmpresaManager.consultarUsuarioPorEmpresa(projetoDTO.getProjeto().getEmpresa().getCodigo()));
		return projetoDTO;
	}

    @Override
    public List<Projeto> consultarPorNome(String nome) {
        return projetoRepositorio.consultarPorNome(nome);
    }
    
    @Override
    public List<Projeto> consultarPorEmpresa(Integer empresaID) {
    	return projetoRepositorio.consultarPorEmpresa(empresaID);
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
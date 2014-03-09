package br.com.scrumming.web.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.joda.time.DateTime;

import br.com.scrumming.domain.Contato;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;
import br.com.scrumming.domain.enuns.SituacaoProjetoEnum;
import br.com.scrumming.domain.enuns.SituacaoSprintEnum;
import br.com.scrumming.web.clientService.ContatoClientService;
import br.com.scrumming.web.clientService.SprintClientService;

@ManagedBean
@ViewScoped
public class ExemploMB {

    private List<Contato> contatos;

    private ContatoClientService clientService;
    private String nomeCotato;
    private SprintClientService sprintService;
    private static final DateTime NOW = DateTime.now();
	private static final DateTime FIM = DateTime.now().plusDays(5);
    
    @PostConstruct
    public void construir() {
        clientService = new ContatoClientService();
        sprintService = new SprintClientService();
    }

    public String construirContatos() {
        //contatos = clientService.findAll();
    	
    	// Empresa
		Empresa empresa = new Empresa();
		empresa.setNome("Empresa1");
		empresa.setAtivo(true);
		empresa.setDataCadastro(NOW);
		
		// Projeto 1
		Projeto projeto1 = new Projeto();
		projeto1.setCodigo(5);
		projeto1.setNome("Projeto_01");
		projeto1.setDescricao("teste01");
		projeto1.setEmpresa(empresa);
		projeto1.setDataCadastro(NOW);
		projeto1.setDataInicio(NOW);
		projeto1.setDataFim(FIM);
		projeto1.setSituacaoProjeto(SituacaoProjetoEnum.ATIVO);
    	
		ItemBacklog ib = new ItemBacklog();
		ib.setCriterioAceitacao("blablabla");
		ib.setDescricao("blublublu");
		ib.setNome("It01");
		ib.setProjeto(projeto1);
		ib.setRoi(2.1);
		ib.setStoryPoints(2);
		ib.setSituacaoBacklog(SituacaoItemBacklogEnum.FEITO);
		ib.setValorNegocio(20.0);
		
		// Sprint 1
		Sprint sprint1 = new Sprint();
		sprint1.setDataRevisao(NOW);
		sprint1.setDataCadastro(NOW);
		sprint1.setDataInicio(NOW);
		sprint1.setDataFim(FIM);
		sprint1.setDescricao("teste01");
		sprint1.setNome("Sprint1");
		sprint1.setProjeto(projeto1);
		sprint1.setSituacaoSprint(SituacaoSprintEnum.ABERTA);
		
    	List<ItemBacklog> lista = new ArrayList<>();
    	lista.add(ib);
    	sprintService.salvarSprint(sprint1, lista, lista);
        return "";
    }

    public String consultarContato() {
        return null;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public String getNomeCotato() {
        return nomeCotato;
    }

    public void setNomeCotato(String nomeCotato) {
        this.nomeCotato = nomeCotato;
    }
}
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
import br.com.scrumming.domain.SprintDTO;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;
import br.com.scrumming.domain.enuns.SituacaoProjetoEnum;
import br.com.scrumming.domain.enuns.SituacaoSprintEnum;
import br.com.scrumming.web.clientService.ContatoClientService;
import br.com.scrumming.web.clientService.SprintClientService;
import br.com.scrumming.web.clientService.UsuarioClientService;

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
        contatos = clientService.findAll();
        return "";
    }

    public String salvarSprint() {

    	// Empresa
		Empresa empresa = new Empresa();
		empresa.setCodigo(1);
		empresa.setNome("Empresa1");
		empresa.setAtivo(true);
		empresa.setDataCadastro(NOW);
		
		// Projeto 1
		Projeto projeto1 = new Projeto();
		projeto1.setCodigo(1);
		projeto1.setNome("Projeto_01");
		projeto1.setDescricao("teste01");
		projeto1.setEmpresa(empresa);
		projeto1.setDataCadastro(NOW);
		projeto1.setDataInicio(NOW);
		projeto1.setDataFim(FIM);
		projeto1.setSituacaoProjeto(SituacaoProjetoEnum.ATIVO);
    	
		ItemBacklog ib = new ItemBacklog();
		ib.setCodigo(1);
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
		sprint1.setCodigo(1);
		sprint1.setDataRevisao(NOW);
		sprint1.setDataCadastro(NOW);
		sprint1.setDataInicio(NOW);
		sprint1.setDataFim(FIM);
		sprint1.setDescricao("teste01");
		sprint1.setNome("Sprint1");
		sprint1.setProjeto(projeto1);
		sprint1.setSituacaoSprint(SituacaoSprintEnum.ABERTA);
		
    	List<ItemBacklog> lista = new ArrayList<ItemBacklog>();
    	lista.add(ib);
    	
    	SprintDTO sprintDTO = new SprintDTO();
    	sprintDTO.setSprint(sprint1);
    	sprintDTO.setSprintBacklog(lista);
    	sprintDTO.setProductBacklog(lista);
    	
    	String retorno = sprintService.salvarSprint(sprintDTO);
    	System.out.println(retorno);
    	return "";
    }
    
    public String salvarUsuario(){
    	Usuario usuario = new Usuario();
    	usuario.setNome("Esdras");
    	
    	UsuarioClientService clientService = new UsuarioClientService();
    	String resposta  =  clientService.salvarUsuario(usuario);
    	System.err.println(resposta);
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
package br.com.scrumming.core.repositorio;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.scrumming.core.infra.AbstractRepositorioTest;
import br.com.scrumming.core.manager.implementations.SprintManager;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;
import br.com.scrumming.domain.SprintDTO;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;
import br.com.scrumming.domain.enuns.SituacaoProjetoEnum;
import br.com.scrumming.domain.enuns.SituacaoSprintEnum;
import br.com.scrumming.web.clientService.SprintClientService;

public class SprintRepositorioTest extends AbstractRepositorioTest {

	private static final DateTime NOW = DateTime.now();
	private static final DateTime FIM = DateTime.now().plusDays(5);
	@Autowired
	private SprintRepositorio sprintRepositorio;

	@Autowired
	private SprintBacklogRepositorio sprintBacklogRepositorio;

	@Autowired
	private SprintManager sprintManager;
	
	private SprintClientService clientService = new SprintClientService();

	//@Test
	public void verificarConsultaPorProjeto() {

		// Empresa
		Empresa empresa = new Empresa();
		empresa.setNome("Empresa1");
		empresa.setAtivo(true);
		empresa.setDataCadastro(NOW);

		save(empresa);

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

		// Projeto 2
		Projeto projeto2 = new Projeto();
		projeto2.setNome("Projeto_02");
		projeto2.setDescricao("teste02");
		projeto2.setEmpresa(empresa);
		projeto2.setDataCadastro(NOW);
		projeto2.setDataInicio(NOW);
		projeto2.setDataFim(FIM);
		projeto2.setSituacaoProjeto(SituacaoProjetoEnum.ATIVO);

		save(projeto1, projeto2);

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

		// Sprint 2
		Sprint sprint2 = new Sprint();
		sprint2.setDataRevisao(NOW);
		sprint2.setDataCadastro(NOW);
		sprint2.setDataInicio(NOW);
		sprint2.setDataFim(FIM);
		sprint2.setDescricao("teste02");
		sprint2.setNome("Sprint2");
		sprint2.setProjeto(projeto1);
		sprint2.setSituacaoSprint(SituacaoSprintEnum.ABERTA);

		// Sprint 3
		Sprint sprint3 = new Sprint();
		sprint3.setDataRevisao(NOW);
		sprint3.setDataCadastro(NOW);
		sprint3.setDataInicio(NOW);
		sprint3.setDataFim(FIM);
		sprint3.setDescricao("teste03");
		sprint3.setNome("Sprint3");
		sprint3.setProjeto(projeto1);
		sprint3.setSituacaoSprint(SituacaoSprintEnum.ABERTA);

		// Sprint 4
		Sprint sprint4 = new Sprint();
		sprint4.setDataRevisao(NOW);
		sprint4.setDataCadastro(NOW);
		sprint4.setDataInicio(NOW);
		sprint4.setDataFim(FIM);
		sprint4.setDescricao("teste04");
		sprint4.setNome("Sprint4");
		sprint4.setProjeto(projeto2);
		sprint4.setSituacaoSprint(SituacaoSprintEnum.ABERTA);

		// Sprint 5
		Sprint sprint5 = new Sprint();
		sprint5.setDataRevisao(NOW);
		sprint5.setDataCadastro(NOW);
		sprint5.setDataInicio(NOW);
		sprint5.setDataFim(FIM);
		sprint5.setDescricao("teste05");
		sprint5.setNome("Sprint5");
		sprint5.setProjeto(projeto2);
		sprint5.setSituacaoSprint(SituacaoSprintEnum.ABERTA);

		save(sprint1, sprint2, sprint3, sprint4, sprint5);

		Projeto proj = findById(projeto1.getChave(), Projeto.class);

		List<Sprint> sprints = sprintRepositorio.consultarPorProjeto(proj
				.getChave());

		Assert.assertTrue("SPRINTS ERRADAS", sprints.size() == 3);
	}

	//@Test
	public void consultaPorChaveComposta() {

		// Empresa
		Empresa empresa = new Empresa();
		empresa.setNome("Empresa1");
		empresa.setAtivo(true);
		empresa.setDataCadastro(NOW);

		save(empresa);

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

		save(projeto1);

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

		save(sprint1);

		ItemBacklog itemBacklog = new ItemBacklog();
		itemBacklog.setCriterioAceitacao("bla");
		itemBacklog.setDescricao("blu");
		itemBacklog.setNome("item1");
		itemBacklog.setProjeto(projeto1);
		itemBacklog.setRoi(2.0);
		itemBacklog.setSituacaoBacklog(SituacaoItemBacklogEnum.ENTREGUE);
		itemBacklog.setStoryPoints(13);
		itemBacklog.setValorNegocio(2.5);

		save(itemBacklog);

		SprintBacklog sb = new SprintBacklog();
		sb.setAtivo(true);
		sb.setItemBacklog(itemBacklog);
		sb.setSprint(sprint1);

		save(sb);

		SprintBacklog itens = sprintBacklogRepositorio
				.consultaPorChaveComposta(sprint1, itemBacklog);

		Assert.assertTrue("NÃO ENCONTRADO", itens != null);
	}
	
	@Test
	public void consultaSprintDTO() {

		// Empresa
		Empresa empresa = new Empresa();
		empresa.setNome("Empresa1");
		empresa.setAtivo(true);
		empresa.setDataCadastro(NOW);

		save(empresa);

		// Projeto 1
		Projeto projeto1 = new Projeto();
		projeto1.setNome("Projeto_01");
		projeto1.setDescricao("teste01");
		projeto1.setEmpresa(empresa);
		projeto1.setDataCadastro(NOW);
		projeto1.setDataInicio(NOW);
		projeto1.setDataFim(FIM);
		projeto1.setSituacaoProjeto(SituacaoProjetoEnum.ATIVO);

		// Projeto 2
		Projeto projeto2 = new Projeto();
		projeto2.setNome("Projeto_02");
		projeto2.setDescricao("teste02");
		projeto2.setEmpresa(empresa);
		projeto2.setDataCadastro(NOW);
		projeto2.setDataInicio(NOW);
		projeto2.setDataFim(FIM);
		projeto2.setSituacaoProjeto(SituacaoProjetoEnum.ATIVO);

		save(projeto1, projeto2);

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

		// Sprint 2
		Sprint sprint2 = new Sprint();
		sprint2.setDataRevisao(NOW);
		sprint2.setDataCadastro(NOW);
		sprint2.setDataInicio(NOW);
		sprint2.setDataFim(FIM);
		sprint2.setDescricao("teste02");
		sprint2.setNome("Sprint2");
		sprint2.setProjeto(projeto1);
		sprint2.setSituacaoSprint(SituacaoSprintEnum.ABERTA);

		save(sprint1, sprint2);
		
		// Item 1
		ItemBacklog item1 = new ItemBacklog();
		item1.setAtivo(true);
		item1.setCriterioAceitacao("lkasdlkjd");
		item1.setDescricao("opa");
		item1.setNome("01");
		item1.setProjeto(projeto1);
		item1.setRoi(2.0);
		item1.setSituacaoBacklog(SituacaoItemBacklogEnum.PLANEJADO);
		item1.setStoryPoints(10);
		item1.setValorNegocio(2.0);
		
		// Item 2
		ItemBacklog item2 = new ItemBacklog();
		item2.setAtivo(true);
		item2.setCriterioAceitacao("lasdkasdlkjd");
		item2.setDescricao("sdopa");
		item2.setNome("02");
		item2.setProjeto(projeto1);
		item2.setRoi(2.0);
		item2.setSituacaoBacklog(SituacaoItemBacklogEnum.PLANEJADO);
		item2.setStoryPoints(10);
		item2.setValorNegocio(2.0);
		
		// Item 2
		ItemBacklog item3 = new ItemBacklog();
		item3.setAtivo(true);
		item3.setCriterioAceitacao("dasd");
		item3.setDescricao("sd");
		item3.setNome("03");
		item3.setProjeto(projeto1);
		item3.setRoi(2.0);
		item3.setSituacaoBacklog(SituacaoItemBacklogEnum.PLANEJADO);
		item3.setStoryPoints(10);
		item3.setValorNegocio(2.0);
		
		save(item1, item2, item3);
		
		// SP Bklog1
		SprintBacklog spBacklo1 = new SprintBacklog();
		spBacklo1.setAtivo(true);
		spBacklo1.setItemBacklog(item1);
		spBacklo1.setSprint(sprint1);
		
		// SP Bklog1
		SprintBacklog spBacklo2 = new SprintBacklog();
		spBacklo2.setAtivo(true);
		spBacklo2.setItemBacklog(item2);
		spBacklo2.setSprint(sprint1);
		
		// SP Bklog1
		SprintBacklog spBacklo3 = new SprintBacklog();
		spBacklo3.setAtivo(true);
		spBacklo3.setItemBacklog(item3);
		spBacklo3.setSprint(sprint2);
		
		save(spBacklo1, spBacklo2, spBacklo3);
		SprintDTO dto = new SprintDTO();
		dto = clientService.consultarSprintDTO(0);
		System.out.println("OPA!!!");
		Assert.assertTrue("NÃO ENCONTRADO", dto != null);
	}
}
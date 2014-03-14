package br.com.scrumming.core.repositorio;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.scrumming.core.infra.AbstractRepositorioTest;
import br.com.scrumming.core.manager.implementations.ProjetoManager;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;
import br.com.scrumming.domain.SprintDTO;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.domain.UsuarioEmpresaUtil;
import br.com.scrumming.domain.UsuarioUtil;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;
import br.com.scrumming.domain.enuns.SituacaoProjetoEnum;
import br.com.scrumming.domain.enuns.SituacaoSprintEnum;

public class ProjetoRepositorioTest extends AbstractRepositorioTest {

	private static final DateTime NOW = DateTime.now();
	private static final DateTime FIM = DateTime.now().plusDays(5);
	@Autowired
	private ProjetoRepositorio projetoRepositorio;

	@Autowired
	private ProjetoManager projetoManager;

	@Test
	public void verificarConsultaPorProjeto() {

		// Empresas
		Empresa empresa1 = new Empresa();
		empresa1.setNome("Empresa1");
		empresa1.setAtivo(true);
		empresa1.setDataCadastro(NOW);

		save(empresa1);
		
		Empresa empresa2 = new Empresa();
		empresa2.setNome("Empresa2");
		empresa2.setAtivo(true);
		empresa2.setDataCadastro(NOW);

		save(empresa2);


		// usuarios
		Usuario usuario1 = UsuarioUtil.criar("Esdras");
		Usuario usuario2 = UsuarioUtil.criar("Astrogildo");
		Usuario usuario3 = UsuarioUtil.criar("Gilmar");
		Usuario usuario4 = UsuarioUtil.criar("Wilson");
		Usuario usuario5 = UsuarioUtil.criar("Sandro");
		Usuario usuario6 = UsuarioUtil.criar("Daniel");
		Usuario usuario7 = UsuarioUtil.criar("Rodrigo");
		Usuario usuario8 = UsuarioUtil.criar("Leyla");
		Usuario usuario9 = UsuarioUtil.criar("Gilson");
		Usuario usuario10 = UsuarioUtil.criar("Maria");
		Usuario usuario11 = UsuarioUtil.criar("Pablo");
		Usuario usuario12 = UsuarioUtil.criar("Paulo");

		save(usuario1, usuario2, usuario3, usuario4);

		save(usuario5, usuario6, usuario7, usuario8);

		save(usuario9, usuario10, usuario11, usuario12);

		// UsuarioEmpresa
		
		UsuarioEmpresa usuario1Empresa1 = UsuarioEmpresaUtil.criarFuncionario(empresa1, usuario1);
		UsuarioEmpresa usuario2Empresa1 = UsuarioEmpresaUtil.criarFuncionario(empresa1, usuario2);
		UsuarioEmpresa usuario3Empresa2 = UsuarioEmpresaUtil.criarFuncionario(empresa2, usuario3);
		UsuarioEmpresa usuario4Empresa1 = UsuarioEmpresaUtil.criarFuncionario(empresa1, usuario4);
		UsuarioEmpresa usuario5Empresa2 = UsuarioEmpresaUtil.criarFuncionario(empresa2, usuario5);
		UsuarioEmpresa usuario6Empresa2 = UsuarioEmpresaUtil.criarFuncionario(empresa2, usuario6);
		UsuarioEmpresa usuario7Empresa1 = UsuarioEmpresaUtil.criarFuncionario(empresa1, usuario7);
		UsuarioEmpresa usuario8Empresa1 = UsuarioEmpresaUtil.criarFuncionario(empresa1, usuario8);
		UsuarioEmpresa usuario9Empresa1 = UsuarioEmpresaUtil.criarFuncionario(empresa1, usuario9);
		UsuarioEmpresa usuario10Empresa2 = UsuarioEmpresaUtil.criarFuncionario(empresa2, usuario10);
		UsuarioEmpresa usuario11Empresa2 = UsuarioEmpresaUtil.criarFuncionario(empresa2, usuario11);
		UsuarioEmpresa usuario12Empresa2 = UsuarioEmpresaUtil.criarFuncionario(empresa2, usuario12);
		
		save(usuario1Empresa1, usuario2Empresa1, usuario3Empresa2, usuario4Empresa1);

		save(usuario5Empresa2, usuario6Empresa2, usuario7Empresa1, usuario8Empresa1);

		save(usuario9Empresa1, usuario10Empresa2, usuario11Empresa2, usuario12Empresa2);
		
		// Projeto 1
		Projeto projeto1 = new Projeto();
		projeto1.setCodigo(5);
		projeto1.setNome("Projeto_01");
		projeto1.setDescricao("teste01");
		projeto1.setEmpresa(empresa1);
		projeto1.setDataCadastro(NOW);
		projeto1.setDataInicio(NOW);
		projeto1.setDataFim(FIM);
		projeto1.setSituacaoProjeto(SituacaoProjetoEnum.ATIVO);

		// Projeto 2
		Projeto projeto2 = new Projeto();
		projeto2.setNome("Projeto_02");
		projeto2.setDescricao("teste02");
		projeto2.setEmpresa(empresa2);
		projeto2.setDataCadastro(NOW);
		projeto2.setDataInicio(NOW);
		projeto2.setDataFim(FIM);
		projeto2.setSituacaoProjeto(SituacaoProjetoEnum.ATIVO);

		save(projeto1, projeto2);

	}

	@Test
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
		itemBacklog.setSituacaoBacklog(SituacaoItemBacklogEnum.FEITO);
		itemBacklog.setStoryPoints(13);
		itemBacklog.setValorNegocio(2.5);

		save(itemBacklog);

		SprintBacklog sb = new SprintBacklog();
		sb.setAtivo(true);
		sb.setItemBacklog(itemBacklog);
		sb.setSprint(sprint1);

		save(sb);

//		SprintBacklog itens = sprintBacklogRepositorio
//				.consultaPorChaveComposta(sprint1, itemBacklog);

		//Assert.assertTrue("NÃO ENCONTRADO", itens != null);
	}

	public void consultaSprintDTO() {

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

		// Sprint

		save(sprint1, sprint2, sprint3, sprint4, sprint5);

//		List<Sprint> sprints = (List<Sprint>) sprintManager.findAll();

		SprintDTO sprintDTO = new SprintDTO();

//		sprintDTO = sprintManager.consultarSprintDTO(sprints.get(0).getChave());

		Assert.assertTrue("NÃO ENCONTRADO", sprintDTO != null);
	}
}
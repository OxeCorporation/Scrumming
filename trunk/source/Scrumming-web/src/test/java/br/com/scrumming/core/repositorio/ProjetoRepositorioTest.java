package br.com.scrumming.core.repositorio;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.scrumming.core.infra.AbstractRepositorioTest;
import br.com.scrumming.core.manager.interfaces.ITeamManager;
import br.com.scrumming.core.manager.interfaces.IUsuarioEmpresaManager;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.domain.UsuarioEmpresaUtil;
import br.com.scrumming.domain.UsuarioUtil;
import br.com.scrumming.domain.enuns.SituacaoProjetoEnum;

public class ProjetoRepositorioTest extends AbstractRepositorioTest {

	private static final DateTime NOW = DateTime.now();
	private static final DateTime FIM = DateTime.now().plusDays(5);
	@Autowired
	private ProjetoRepositorio projetoRepositorio;
	
	@Autowired
	private TeamRepositorio teamRepositorio;

	@Autowired
	private IUsuarioEmpresaManager iUsuarioEmpresaManager;
	
	@Autowired
	private ITeamManager iTeamManager;

	@Test
	public void verConsultaPorProjeto() {
		// daniel, refaser teste, mito confuso e errado.
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
		usuario9.setAtivo(false);
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
		projeto1.setEmpresa(empresa2);
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

		Projeto projeto3 = new Projeto();
		projeto3.setNome("Projeto_03");
		projeto3.setDescricao("teste03");
		projeto3.setEmpresa(empresa1);
		projeto3.setDataCadastro(NOW);
		projeto3.setDataInicio(NOW);
		projeto3.setDataFim(FIM);
		projeto3.setSituacaoProjeto(SituacaoProjetoEnum.ATIVO);

		
		save(projeto1, projeto2, projeto3);
		
		Team team1 = new Team();
		team1.setProjeto(projeto1);
		team1.setUsuario(usuario12);
		team1.setEmpresa(empresa2);
		team1.setAtivo(true);
		team1.setPerfilUsuario(null);
		
		Team team2 = new Team();
		team2.setProjeto(projeto1);
		team2.setUsuario(usuario11);
		team2.setEmpresa(empresa2);
		team2.setAtivo(true);
		team2.setPerfilUsuario(null);
		
		Team team3 = new Team();
		team3.setProjeto(projeto1);
		team3.setUsuario(usuario10);
		team3.setEmpresa(empresa2);
		team3.setAtivo(true);
		team3.setPerfilUsuario(null);
		
		Team team4 = new Team();
		team4.setProjeto(projeto3);
		team4.setUsuario(usuario9);
		team4.setEmpresa(empresa1);
		team4.setAtivo(true);
		team4.setPerfilUsuario(null);
		
		Team team5 = new Team();
		team5.setProjeto(projeto1);
		team5.setUsuario(usuario8);
		team5.setEmpresa(empresa2);
		team5.setAtivo(false);
		team5.setPerfilUsuario(null);
		
		Team team6 = new Team();
		team6.setProjeto(projeto1);
		team6.setUsuario(usuario7);
		team6.setEmpresa(empresa2);
		team6.setAtivo(true);
		team6.setPerfilUsuario(null);

		save(team1, team2, team3, team4, team5, team6);

		
		//Empresa empresa = findById(empresa1.getChave(), Empresa.class);
		//List<Usuario> itens = iTeamManager.consultarUsuarioPorProjeto(projeto1.getCodigo());
		List<Usuario> itens = iTeamManager.consultarUsuarioPorProjeto(projeto3.getCodigo());
		Assert.assertTrue("NÃO ENCONTRADO", itens != null);
		

	}
}
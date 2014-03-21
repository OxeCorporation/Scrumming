package br.com.scrumming.core.repositorio;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.scrumming.core.infra.AbstractRepositorioTest;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.EmpresaUtil;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.domain.UsuarioEmpresaUtil;
import br.com.scrumming.domain.UsuarioUtil;

public class UsuarioEmpresaRepositorioTest extends AbstractRepositorioTest {

	@Autowired
	private UsuarioEmpresaRepositorio usuarioEmpresaRepositorio;
	
	@Test
	public void verificarConsultarUsuarioPorEmpresa(){
		Empresa empresa = EmpresaUtil.criar("Organizações Tabajara");
		save(empresa);
		
		Usuario usuario = UsuarioUtil.criarUsuarioEmpresa("Tabajara");
		Usuario usuario2 = UsuarioUtil.criar("Fulano");
		Usuario usuario3 = UsuarioUtil.criar("Sicrano");
		Usuario usuario4 = UsuarioUtil.criar("Beltrano");
		Usuario usuario5 = UsuarioUtil.criar("Astrogildo");
		
		save(usuario,usuario2,usuario3,usuario4,usuario5);
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaUtil.criarFuncionario(empresa, usuario);
		UsuarioEmpresa usuarioEmpresa2 = UsuarioEmpresaUtil.criarFuncionario(empresa, usuario2);
		UsuarioEmpresa usuarioEmpresa3 = UsuarioEmpresaUtil.criarFuncionario(empresa, usuario3);
		UsuarioEmpresa usuarioEmpresa4 = UsuarioEmpresaUtil.criarFuncionario(empresa, usuario4);
		UsuarioEmpresa usuarioEmpresa5 = UsuarioEmpresaUtil.criarFuncionario(empresa, usuario5);
		
		save(usuarioEmpresa,usuarioEmpresa2,usuarioEmpresa3,usuarioEmpresa4,usuarioEmpresa5);
		
		List<Usuario> usuarios = usuarioEmpresaRepositorio.consultarUsuarioPorEmpresa(0);
		
		Assert.assertTrue(usuarios.size() == 4);
		Assert.assertEquals(OBJETOS_DEVERIAM_SER_IGUAIS, usuario5, usuarios.get(0));
	}
	
	@Test
	public void verificarConsultarEmpresaPorUsuarioComUmaEmpresa(){
		Empresa empresa = EmpresaUtil.criar("Organizações Tabajara");
		save(empresa);
		
		Usuario usuario = UsuarioUtil.criar("Fulano");
		save(usuario);
		
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaUtil.criarFuncionario(empresa, usuario);
		save(usuarioEmpresa);
		
		Integer codigoUsuario = findAll(Usuario.class).get(0).getCodigo();
		
		List<Empresa> empresas = usuarioEmpresaRepositorio.consultarEmpresaPorUsuario(codigoUsuario);
		
		Assert.assertTrue(empresas.size() == 1);
		Assert.assertEquals(OBJETOS_DEVERIAM_SER_IGUAIS, empresa, empresas.get(0));
	}
	
	@Test
	public void verificarConsultarEmpesasPorUsuarioComVariasEmpresas(){
		Empresa empresa = EmpresaUtil.criar("A Organizações Tabajara");
		Empresa empresa1 = EmpresaUtil.criar("B Organizações TUTUTU");
		save(empresa,empresa1);
		
		Usuario usuario = UsuarioUtil.criar("Fulano");
		save(usuario);
		
		
		UsuarioEmpresa usuarioEmpresa1 = UsuarioEmpresaUtil.criarFuncionario(empresa, usuario);
		UsuarioEmpresa usuarioEmpresa2 = UsuarioEmpresaUtil.criarFuncionario(empresa1, usuario);
		save(usuarioEmpresa1,usuarioEmpresa2);
		
		Integer codigoUsuario = findAll(Usuario.class).get(0).getCodigo();
		
		List<Empresa> empresas = usuarioEmpresaRepositorio.consultarEmpresaPorUsuario(codigoUsuario);
		
		Assert.assertTrue(empresas.size() == 2);
		Assert.assertEquals(OBJETOS_DEVERIAM_SER_IGUAIS, empresa, empresas.get(0));
	}
}

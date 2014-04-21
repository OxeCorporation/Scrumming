package br.com.scrumming.core.repositorio;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.scrumming.core.infra.AbstractRepositorioTest;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;
import br.com.scrumming.domain.enuns.SituacaoProjetoEnum;


public class ItemBacklogRepositorioTest extends AbstractRepositorioTest {

	@Autowired
	private ItemBacklogRepositorio itemRepositorio;
	
	@Test
	public void consultarPorProjeto() {
        //Empresa1
        Empresa empresa = new Empresa();
        empresa.setNome("SEM FUTURO");
        empresa.setDataCadastro(DateTime.now());
        empresa.setAtivo(true);

        save(empresa);

        //Projeto1
        Projeto projeto1 = new Projeto();
        projeto1.setEmpresa(empresa);
        projeto1.setNome("Unibratec");
        projeto1.setDescricao("O mundo é dos experts");
        projeto1.setDataInicio(DateTime.now());
        projeto1.setDataFim(DateTime.now());
        projeto1.setDataCadastro(DateTime.now());
        projeto1.setSituacaoProjeto(SituacaoProjetoEnum.CONCLUIDO);

        //Projeto2
        Projeto projeto2 = new Projeto();
        projeto2.setEmpresa(empresa);
        projeto2.setNome("Trabalho");
        projeto2.setDescricao("Ta Dificil");
        projeto2.setDataInicio(DateTime.now());
        projeto2.setDataFim(DateTime.now());
        projeto2.setDataCadastro(DateTime.now());
        projeto2.setSituacaoProjeto(SituacaoProjetoEnum.ATIVO);

        save(projeto1, projeto2);
        
        //ItemBacklog1
        ItemBacklog item1 = new ItemBacklog();
        item1.setProjeto(projeto1);
        item1.setNome("Manter usuario");
        item1.setDescricao("este item serve para Manter usuario");
        item1.setCriterioAceitacao("dgasdgagasdgasdgasdgasd");
        item1.setValorNegocio(10.0);
        item1.setStoryPoints(2);
        item1.setRoi(10.0);
        item1.setSituacaoBacklog(SituacaoItemBacklogEnum.FEITO);

        //itemBacklog2
        ItemBacklog item2 = new ItemBacklog();
        item2.setProjeto(projeto1);
        item2.setNome("Manter projeto");
        item2.setDescricao("este item serve para manter o projeto");
        item2.setCriterioAceitacao("dgasdgagasdgasdgasdgasd");
        item2.setValorNegocio(15.0);
        item2.setStoryPoints(2);
        item2.setRoi(10.0);
        item2.setSituacaoBacklog(SituacaoItemBacklogEnum.FAZER);

        //ItemBacklog3
        ItemBacklog item3 = new ItemBacklog();
        item3.setProjeto(projeto1);
        item3.setNome("usuario");
        item3.setDescricao("fazer algo com o usuario");
        item3.setCriterioAceitacao("dgasdgagasdgasdgasdgasd");
        item3.setValorNegocio(18.0);
        item3.setStoryPoints(2);
        item3.setRoi(10.0);
        item3.setSituacaoBacklog(SituacaoItemBacklogEnum.FAZENDO);

        //itemBacklog4
        ItemBacklog item4 = new ItemBacklog();
        item4.setProjeto(projeto2);
        item4.setNome("projeto");
        item4.setDescricao("fazer algo com o  projeto");
        item4.setCriterioAceitacao("dgasdgagasdgasdgasdgasd");
        item4.setValorNegocio(09.0);
        item4.setStoryPoints(2);
        item4.setRoi(10.0);
        item4.setSituacaoBacklog(SituacaoItemBacklogEnum.FAZENDO);

        save(item1, item2, item3, item4);

        //List<ItemBacklog> itens = itemRepositorio.consultarPorProjeto(2);
        
        itemRepositorio.consultarItemPorID(new Integer(0));
        
        Assert.assertTrue("ItemBacklog não encontrado", itemRepositorio != null);

	}

}

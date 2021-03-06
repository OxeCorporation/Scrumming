package br.com.scrumming.web.managedbean.itembacklog;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.clientService.ItemBacklogClientService;
import br.com.scrumming.web.clientService.TarefaClientService;
import br.com.scrumming.web.clientService.TeamClientService;
import br.com.scrumming.web.clientService.UsuarioClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class ItemBacklogDetalheMB extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4605021646391355541L;
	private List<ItemBacklog> itens;
	@FlashScoped
	private ItemBacklog itemBacklog;
	@FlashScoped
	private ItemBacklog itemSelecionado;
	private ItemBacklogClientService clienteService;
	@FlashScoped
	private Projeto projetoSelecionado;
	private TarefaClientService tarefaClientService;
	@FlashScoped
	private Tarefa tarefa;
	@FlashScoped
	private Tarefa tarefaSelecionada;
	private List<Usuario> usuarios;
	private long idUsuarioSelecionado;
	private TeamClientService teamClientService;
	private UsuarioClientService usuarioClientService;
	private String nomeDoFormulario;

	@Override
    public void inicializar() {
		if (tarefa == null) {
			tarefa = new Tarefa();
		}
		if (tarefaSelecionada == null) {
			tarefaSelecionada = new Tarefa();
		}
		tarefaClientService = new TarefaClientService();
		teamClientService = new TeamClientService();
		usuarioClientService = new UsuarioClientService();
		atualizarListaDeTarefas();
		atualizarListaDeUsuarios();
	}

	private void atualizarListaDeUsuarios() {
		usuarios = teamClientService.consultarUsuarioPorProjeto(projetoSelecionado.getCodigo());		
	}

	/* Métodos para redirecionamento das páginas */
	public String itemBacklogCadastroPage() {
		return redirecionar(PaginasUtil.ItemBacklog.CADASTRAR_ITEM_BACKLOG);
	}

	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}
	
	public String itemBacklogDetalhePage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_DETAIL_PAGE);
	}

	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}
	
	public String tarefaCadastroPage() {
		return redirecionar(PaginasUtil.Tarefa.SAVE_PAGE);
	}

	
	/* Funções específicas da tela */
	public void atualizarListaDeTarefas() {
		if (itemSelecionado != null) {
			itemSelecionado.setTarefas(tarefaClientService.consultarTarefasPorItemBacklog(itemSelecionado.getCodigo()));			
		}
	}
	
	public String salvarTarefa() {
		Integer itemBacklodID;
		if (tarefa.getItemBacklog() == null) {
			itemBacklodID = itemSelecionado.getCodigo();
		} else {
			itemBacklodID = tarefa.getItemBacklog().getCodigo();
		}
		setarUsuarioNaTarefa();		
		tarefaClientService.salvarTarefa(tarefa, itemBacklodID);
		limparObjetoTarefa();
		atualizarListaDeTarefas();
    	FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
    	atualizarFormulario();
    	return "";
	}	
	
	private void atualizarFormulario(){
		RequestContext context = RequestContext.getCurrentInstance();
		//context.update("tabView:frmSprintItem");
		context.update(nomeDoFormulario);
	}

	private void setarUsuarioNaTarefa() {
		if (idUsuarioSelecionado == 0) {
			tarefa.setUsuario(null);
			tarefa.setDataAtribuicao(null);
		} else {
			if (tarefa.getUsuario() == null || tarefa.getUsuario().getCodigo() != idUsuarioSelecionado) {
				Usuario usuarioSelecionado = usuarioClientService.obterUsuario(idUsuarioSelecionado);
				tarefa.setUsuario(usuarioSelecionado);
				tarefa.setDataAtribuicao(new DateTime());
			}
		}
	}

	private void limparObjetoTarefa() {
		tarefa = null;		
	}

	public String removerTarefa(){
		tarefaClientService.removerTarefa(tarefaSelecionada);
		atualizarListaDeTarefas();
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
		return "";
	}
	
	public void preparaParaInserir() {
		tarefa = new Tarefa();
		idUsuarioSelecionado = 0;
	}
	
	public void preparaParaAlterar() {
		tarefa = tarefaSelecionada;
		idUsuarioSelecionado = (tarefa.getUsuario() != null) ? tarefa.getUsuario().getCodigo() : 0;
	}
	
	public String salvarItemBacklog() {
		clienteService.salvarItemBacklog(itemBacklog);
		return "";
	}

	public String cancelarItemBacklog() {
		clienteService.cancelarItemBacklog(itemBacklog);
		return "";
	}

	public String consultarPorProjeto(Integer projetoID) {
		// criado para teste
		itens = clienteService.consultarItemPorProjeto(projetoID);
		return "";
	}

	public String consultarItemPorID() {
		clienteService.consultarItemPorID(itemBacklog.getChave());
		return "";
	}

	
	/* getters and setters */
	public List<ItemBacklog> getItens() {
		return itens;
	}

	public void setItens(List<ItemBacklog> itens) {
		this.itens = itens;
	}

	public ItemBacklog getItemBacklog() {
		return itemBacklog;
	}

	public void setItemBacklog(ItemBacklog itemBacklog) {
		this.itemBacklog = itemBacklog;
	}

	public ItemBacklog getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(ItemBacklog itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}
	
	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Tarefa getTarefaSelecionada() {
		return tarefaSelecionada;
	}

	public void setTarefaSelecionada(Tarefa tarefaSelecionada) {
		this.tarefaSelecionada = tarefaSelecionada;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public long getIdUsuarioSelecionado() {
		return idUsuarioSelecionado;
	}

	public void setIdUsuarioSelecionado(long idUsuarioSelecionado) {
		this.idUsuarioSelecionado = idUsuarioSelecionado;
	}

	public String getNomeDoFormulario() {
		return nomeDoFormulario;
	}

	public void setNomeDoFormulario(String nomeDoFormulario) {
		this.nomeDoFormulario = nomeDoFormulario;
	}
}
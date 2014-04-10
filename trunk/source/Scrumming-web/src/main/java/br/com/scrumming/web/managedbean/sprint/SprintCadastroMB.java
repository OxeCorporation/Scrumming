package br.com.scrumming.web.managedbean.sprint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintDTO;
import br.com.scrumming.web.clientService.SprintClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class SprintCadastroMB extends AbstractBean {

	private static final long serialVersionUID = 1L;
	@FlashScoped
	private Sprint sprintSelecionada;
	@FlashScoped
	private Projeto projetoSelecionado;
	private SprintClientService sprintClientService;
	@FlashScoped
	private SprintDTO sprintDTO;
	@FlashScoped
	private List<ItemBacklog> itensDisponiveis;
	@FlashScoped
	private List<ItemBacklog> sprintBacklog;
	private ItemBacklog itemSelecionado;
	private ItemBacklog sprintBacklogSelecionado;
	
	@Override
	public void inicializar() {
		if (itensDisponiveis == null) {
			itensDisponiveis = new ArrayList<>();
		}
		if (sprintBacklog == null) {
			sprintBacklog = new ArrayList<>();
		}
		if (sprintDTO == null) {
			sprintDTO = new SprintDTO();
		}
		sprintClientService = new SprintClientService();
	}
	
	/**
	 * Salva o sprintDTO
	 * @return
	 */
	public String salvarSprint() {
		sprintDTO.getSprint().setProjeto(projetoSelecionado);
		sprintClientService.salvarSprint(sprintDTO);
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}
	
	/**
	 * 
	 * @return
	 */
	public String moveItemToSprint() {
		if (itemSelecionado != null) {
			List<ItemBacklog> listToRemove = new LinkedList<>(itensDisponiveis);
			ItemBacklog itemToRemove = new ItemBacklog();
			for (ItemBacklog item : itensDisponiveis) {
				if (item.getCodigo() == itemSelecionado.getCodigo()) {
					itemToRemove = item;
				}
			}
			listToRemove.remove(itemToRemove);
			itensDisponiveis = listToRemove;
			sprintBacklog.add(itemSelecionado);
			itemSelecionado = null;
		} else {
			FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_SELECIONAR_ITEM_LISTA);
		}
		return "";
	}
	
	/**
	 * 
	 * @return
	 */
	public String removeItemFromSprint() {
		if (sprintBacklogSelecionado != null) {
			List<ItemBacklog> listToRemove = new LinkedList<>(sprintBacklog);
			ItemBacklog itemToRemove = new ItemBacklog();
			for (ItemBacklog item : sprintBacklog) {
				if (item.getCodigo() == sprintBacklogSelecionado.getCodigo()) {
					itemToRemove = item;
				}
			}
			listToRemove.remove(itemToRemove);
			sprintBacklog = listToRemove;
			itensDisponiveis.add(sprintBacklogSelecionado);
			sprintBacklogSelecionado = null;
		} else {
			FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_SELECIONAR_ITEM_LISTA);
		}
		return "";
	}
	
	/*Métodos para redirecionamento das páginas*/
	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}
	
	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}

	/*Getters anda Setters*/
	public Sprint getSprintSelecionada() {
		return sprintSelecionada;
	}

	public void setSprintSelecionada(Sprint sprintSelecionada) {
		this.sprintSelecionada = sprintSelecionada;
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}

	public SprintDTO getSprintDTO() {
		return sprintDTO;
	}

	public void setSprintDTO(SprintDTO sprintDTO) {
		this.sprintDTO = sprintDTO;
	}

	public List<ItemBacklog> getItensDisponiveis() {
		return itensDisponiveis;
	}

	public void setItensDisponiveis(List<ItemBacklog> itensDisponiveis) {
		this.itensDisponiveis = itensDisponiveis;
	}

	public List<ItemBacklog> getSprintBacklog() {
		return sprintBacklog;
	}

	public void setSprintBacklog(List<ItemBacklog> sprintBacklog) {
		this.sprintBacklog = sprintBacklog;
	}

	public ItemBacklog getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(ItemBacklog itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public ItemBacklog getSprintBacklogSelecionado() {
		return sprintBacklogSelecionado;
	}

	public void setSprintBacklogSelecionado(ItemBacklog sprintBacklogSelecionado) {
		this.sprintBacklogSelecionado = sprintBacklogSelecionado;
	}
}
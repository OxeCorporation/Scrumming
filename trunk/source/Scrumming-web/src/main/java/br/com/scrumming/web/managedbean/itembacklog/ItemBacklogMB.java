package br.com.scrumming.web.managedbean.itembacklog;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.web.clientService.ItemBacklogClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ItemBacklogMB extends AbstractBean {

    private List<ItemBacklog> itens;
    @FlashScoped
    private ItemBacklog itemBacklog;
    @FlashScoped
    private ItemBacklog itemSelecionado;
    private ItemBacklogClientService clienteService;
    @FlashScoped
    private Projeto projetoSelecionado;
    private Integer number;
   
    @Override
    public void inicializar() {
    	itemBacklog= new ItemBacklog();
    	setarStoryPointValorNegocio();
        clienteService = new ItemBacklogClientService();
        atualizarListaDeItens();
    }
    
    /*Funções específicas da tela*/
	public String salvarItemBacklog() {
		itemBacklog.setProjeto(projetoSelecionado);
		clienteService.salvarItemBacklog(itemBacklog);
		atualizarListaDeItens();
		
		FacesContext context = FacesContext.getCurrentInstance();         
        context.addMessage(null, new FacesMessage("Operação Realizada com Sucesso!"));
		return "";
    }

	public void atualizarListaDeItens(){
			itens= clienteService.consultarItemPorProjeto(projetoSelecionado.getCodigo());
	}
	
    public String cancelarItemBacklog() {
       	clienteService.cancelarItemBacklog(itemSelecionado);
       	atualizarListaDeItens();
       	
       	FacesContext context = FacesContext.getCurrentInstance();         
        context.addMessage(null, new FacesMessage("Item Backlog Cancelado com Sucesso!"));
        return "";
    }
    
    public void alterar(){
    	itemBacklog= itemSelecionado;
    	number = numValor(itemBacklog.getStoryPoints());
    }
    
    public void preparaParaInserir() {
		itemBacklog = new ItemBacklog();
		setarStoryPointValorNegocio();
	}
    
    public void setarStoryPointValorNegocio(){
    	itemBacklog.setStoryPoints(0);
    	itemBacklog.setValorNegocio(0.0);
    }
    
    public String consultarItemPorID() {
    	clienteService.consultarItemPorID(itemBacklog.getChave());
    	return "";
    }
    
	private Integer numValor(Integer numero) {
		Integer num = 0;
		if (numero == 0){
			return num = 0;
		}else if (numero == 1){
			return num = 1;
		}else if (numero == 2){
			return num = 2;
		}else if (numero == 3){
			return num = 3;
		}else if (numero == 5){
			return num = 4;
		}else if (numero == 8){
			return num = 5;
		}else if (numero == 13){
			return num = 6;
		}else if (numero == 21){
			return num = 7;
		}else if (numero == 34){
			return num = 8;
		}else if (numero == 55){
			return num = 9;
		}else{
			return num;
		}
	}
    
    /*Métodos para redirecionamento das páginas*/
    public String itemBacklogCadastroPage(){
    	return redirecionar(PaginasUtil.ItemBacklog.CADASTRAR_ITEM_BACKLOG);
    }
    
    public String itembacklogDetalhePage(){
    	return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_DETAIL_PAGE);
    }
    
    public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}
    
    public String projetoPage() {
    	return redirecionar(PaginasUtil.Projeto.PROJETO_PAGE);
    }

    /* getters and setters */
    
    public List<ItemBacklog> getItens() {
		return itens;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {

		if (number == 0) {
    		this.number = 0;
        	return;
        }else if(number == 1){
    		this.number = 1;
        	return;
        }else if(number == 2){
    		this.number = 2;
        	return;
        }else if(number == 3){
    		this.number = 3;
        	return;
        }else if(number == 4){
    		this.number = 5;
        	return;
        }else if(number == 5){
    		this.number = 8;
        	return;
        }else if(number == 6){
    		this.number = 13;
        	return;
        }else if(number == 7){
    		this.number = 21;
        	return;
        }else if(number == 8){
        	this.number = 34;
        	return;
        }else if(number == 9){
    		this.number = 55;
        }
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
}
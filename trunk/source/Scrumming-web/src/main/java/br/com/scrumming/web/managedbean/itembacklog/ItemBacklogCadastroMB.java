package br.com.scrumming.web.managedbean.itembacklog;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.web.clientService.ItemBacklogClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class ItemBacklogCadastroMB extends AbstractBean {

	private List<ItemBacklog> itens;
	@FlashScoped
	private ItemBacklog itemBacklog;
	@FlashScoped
	private ItemBacklog itemSelecionado;
	private ItemBacklogClientService clienteService;
	@FlashScoped
	private Projeto projetoSelecionado;
	private int number;
	
	
	
	@Override
	public void inicializar() {
		itemBacklog= new ItemBacklog();
		clienteService = new ItemBacklogClientService();
	}

	/* MÃ©todos para redirecionamento das pÃ¡ginas */
	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}

	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}

	/* FunÃ§Ãµes especÃ­ficas da tela */
	public String salvarItemBacklog() {
		itemBacklog.setStoryPoints(number);
		itemBacklog.setProjeto(projetoSelecionado);
		itemBacklog.setRoi(20.0);
		clienteService.salvarItemBacklog(itemBacklog);
		atualizar();
		return "";
	}

	private void atualizar(){
		itens = clienteService.consultarItemPorProjeto(projetoSelecionado.getCodigo());
	}
	
	public String cancelarItemBacklog() {
		clienteService.cancelarItemBacklog(itemBacklog);
		return "";
	}

	public String consultarPorProjeto(Integer projetoID) {
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

	public int getNumber() {
		return number;
	}

	private int numValor() {
		int num = itemSelecionado.getStoryPoints().intValue();
		if (num == 0){
			return num = 0;
		}else if (num == 1){
			return num = 10;
		}else if (num == 2){
			return num = 20;
		}else if (num == 3){
			return num = 30;
		}else if (num == 5){
			return num = 40;
		}else if (num == 8){
			return num = 50;
		}else if (num == 13){
			return num = 60;
		}else if (num == 20){
			return num = 70;
		}else if (num == 40){
			return num = 80;
		}else if (num == 100){
			return num = 90;
		}else{
			return num = 100;
		}
	}

	public void setNumber(int number) {
/*		int contador= 0;
		while(contador <= 100){
			number= contador + 5;
			contador++;
		}		
*/		
        if (number == 0) {
    		this.number = 0;
        	return;
        }else if(number == 10){
    		this.number = 1;
        	return;
        }else if(number == 20){
    		this.number = 2;
        	return;
        }else if(number == 30){
    		this.number = 3;
        	return;
        }else if(number == 40){
    		this.number = 5;
        	return;
        }else if(number == 50){
    		this.number = 8;
        	return;
        }else if(number == 60){
    		this.number = 13;
        	return;
        }else if(number == 70){
    		this.number = 21;
        	return;
        }else if(number == 80){
        	this.number = 34;
        	return;
        }else if(number == 90){
    		this.number = 55;
        	return;
        }
	}
}
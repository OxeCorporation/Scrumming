package br.com.scrumming.web.managedbean.tarefa;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.TarefaFavorita;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.clientService.TarefaFavoritaClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class TarefaFavoritaMB extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FlashScoped
	private TarefaFavorita tarefaFavorita;
	private Tarefa tarefaSelecionada;
	private TarefaFavoritaClientService tarefaFavoritaClientService;
	@ManagedProperty(value="#{sessaoMB.usuario}")
	private Usuario usuarioLogado;
	
	
	@Override
    public void inicializar() {
		setTarefaFavorita(new TarefaFavorita());
		setTarefaFavoritaClientService(new TarefaFavoritaClientService());
		if (tarefaSelecionada == null) {
			tarefaSelecionada = new Tarefa();
		}
	}
	
	public void favoritarTarefa() {		
		tarefaFavorita.setTarefa(tarefaSelecionada);
		tarefaFavorita.setUsuario(getUsuarioLogado());
		tarefaFavoritaClientService.favoritarTarefa(tarefaFavorita);
		limparObjetoTarefaFavorita();
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
	}
	
	private void limparObjetoTarefaFavorita(){
		setTarefaFavorita(null);		
	}

	/* getters and setters */
	public Tarefa getTarefaSelecionada() {
		return tarefaSelecionada;
	}

	public void setTarefaSelecionada(Tarefa tarefaSelecionada) {
		this.tarefaSelecionada = tarefaSelecionada;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public TarefaFavorita getTarefaFavorita() {
		return tarefaFavorita;
	}

	public void setTarefaFavorita(TarefaFavorita tarefaFavorita) {
		this.tarefaFavorita = tarefaFavorita;
	}

	public TarefaFavoritaClientService getTarefaFavoritaClientService() {
		return tarefaFavoritaClientService;
	}

	public void setTarefaFavoritaClientService(
			TarefaFavoritaClientService tarefaFavoritaClientService) {
		this.tarefaFavoritaClientService = tarefaFavoritaClientService;
	}
	
}
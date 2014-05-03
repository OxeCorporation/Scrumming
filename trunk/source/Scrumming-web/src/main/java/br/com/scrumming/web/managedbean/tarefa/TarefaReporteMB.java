package br.com.scrumming.web.managedbean.tarefa;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.TarefaReporte;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.clientService.TarefaReporteClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class TarefaReporteMB extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FlashScoped
	private TarefaReporte tarefaReporte;
	private Tarefa tarefaSelecionada;
	@FlashScoped
	private Sprint sprintSelecionada;
	private TarefaReporteClientService tarefaReporteClientService;
	@ManagedProperty(value="#{sessaoMB.usuario}")
	private Usuario usuarioLogado;
	
	
	@Override
    public void inicializar() {
		setTarefaReporte(new TarefaReporte());
		tarefaReporteClientService = new TarefaReporteClientService();
		if (tarefaSelecionada == null) {
			tarefaSelecionada = new Tarefa();
		}
	}
	
	public void reportarHora() {
		tarefaReporte.setTarefa(tarefaSelecionada);
		tarefaReporte.setUsuario(getUsuarioLogado());
		tarefaReporteClientService.reportarHora(tarefaReporte, sprintSelecionada.getCodigo());
		limparObjetoTarefaReporte();
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
	}
	
	private void limparObjetoTarefaReporte(){
		setTarefaReporte(null);		
	}
	
	public void preparaParaInserir() {
		setTarefaReporte(new TarefaReporte());
	}

	/* getters and setters */
	public TarefaReporte getTarefaReporte() {
		return tarefaReporte;
	}

	public void setTarefaReporte(TarefaReporte tarefaReporte) {
		this.tarefaReporte = tarefaReporte;
	}

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

	public Sprint getSprintSelecionada() {
		return sprintSelecionada;
	}

	public void setSprintSelecionada(Sprint sprintSelecionada) {
		this.sprintSelecionada = sprintSelecionada;
	}	
	
}
package br.com.scrumming.web.infra;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import br.com.scrumming.core.infra.exceptions.NegocioException;
import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.core.infra.util.MensagemUtil;

public class FacesMessageUtil {

    private FacesMessageUtil() {
        throw new UnsupportedOperationException();
    }

    public static void adicionarMensagemAdvertencia(String messageId, Object... parametros) {
        String mensagem = MensagemUtil.get(messageId, parametros);
        String titulo = MensagemUtil.get(ConstantesMensagem.LABEL_ADVERTENCIA);
        adicionarMensagem(FacesMessage.SEVERITY_WARN, titulo, mensagem);
    }

    public static void adicionarMensagemInfo(String messageId, Object... parametros) {
        String mensagem = MensagemUtil.get(messageId, parametros);
        String titulo = MensagemUtil.get(ConstantesMensagem.LABEL_INFORMACAO);
        adicionarMensagem(FacesMessage.SEVERITY_INFO, titulo, mensagem);
    }

    public static void adicionarMensagemErro(String messageId, Object... parametros) {
        String mensagem = MensagemUtil.get(messageId, parametros);
        String titulo = MensagemUtil.get(ConstantesMensagem.LABEL_ERRO);
        adicionarMensagem(FacesMessage.SEVERITY_ERROR, titulo, mensagem);
    }

    public static void adicionarMensagemErro(NegocioException excecao) {
        adicionarMensagemErro(excecao.getMessage());
    }

    public static void adicionarMensagem(Severity severidade, String titulo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severidade, titulo, detalhe));
    }

    public static void adicionarMensagem(FacesMessage facesMessage) {
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public static void adicionarMensagem(String clientId, FacesMessage facesMessage) {
        FacesContext.getCurrentInstance().addMessage(clientId, facesMessage);
    }
}
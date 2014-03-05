package br.com.scrumming.web.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.Usuario;

@ManagedBean
@ViewScoped
public class CadastroUsuarioBean extends AbstractBean {

    private List<Usuario> usuarios;

    private Usuario usuarioSelecionado;

    @Override
    public void inicializar() {
        usuarios = new ArrayList<Usuario>();
    }

    public String novo() {
        // TODO esc implementar depois
        return "";
    }

    public String detalhar() {
        // TODO esc implementar depois
        return "";

    }

    public String alterar() {
        // TODO esc implementar depois
        return "";

    }

    public String excluir() {
        // TODO esc implementar depois
        return "";
    }

    /* getters and setters */

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }
}
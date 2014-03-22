package br.com.scrumming.web.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;

@ManagedBean
@ViewScoped
public class CadastroUsuarioBean extends AbstractBean {

    private Usuario usuarioSelecionado;
    private List<Usuario> usuarios;

    @FlashScoped
    private String testeFlash;
    
    @ManagedProperty(value="#{sessaoMB.empresaSelecionada}")
    private Empresa empresa;

    public String pesquisarPorNome(){
    	return "";
    }
    
    @Override
    public void inicializar() {
        usuarios = new ArrayList<Usuario>();
    }

    public String sprintPage(){
    	return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
    }
    
    public String novo() {
        return "";
    }

    public String detalhar() {
        return "";

    }

    public String alterar() {
        return "";

    }

    public String excluir() {
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

	public String getTesteFlash() {
		return testeFlash;
	}

	public void setTesteFlash(String testeFlash) {
		this.testeFlash = testeFlash;
	}
}
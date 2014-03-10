package br.com.scrumming.web.managedbean;

import javax.annotation.PostConstruct;

public abstract class AbstractBean {

    @PostConstruct
    protected void inicializar() {

    }

    protected String redirecionar(String pagina) {
        return pagina + "?faces-redirect=true";
    }
}

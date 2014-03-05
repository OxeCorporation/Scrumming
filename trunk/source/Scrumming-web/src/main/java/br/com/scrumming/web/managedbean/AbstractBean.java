package br.com.scrumming.web.managedbean;

import javax.annotation.PostConstruct;

public abstract class AbstractBean {

    @PostConstruct
    public abstract void inicializar();
}

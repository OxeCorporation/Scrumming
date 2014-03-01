package br.com.scrumming.core.infra.util;

import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class MensagensBundle extends ResourceBundle {

	protected final String BUNDLE_NAME = "mensagens";
	protected final Control UTF8_CONTROL = new UTF8Control();

	public MensagensBundle() {
		ResourceBundle.clearCache();
		setParent(ResourceBundle.getBundle(BUNDLE_NAME, FacesContext
				.getCurrentInstance().getViewRoot().getLocale(), UTF8_CONTROL));
	}

	@Override
	public Enumeration<String> getKeys() {
		return parent.getKeys();
	}

	@Override
	protected Object handleGetObject(String key) {
		return parent.getObject(key);
	}

}

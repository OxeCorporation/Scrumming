package br.com.scrumming.web.infra.bean;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import br.com.scrumming.web.infra.FlashFactoryUtil;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.ReflectionUtils;

public abstract class AbstractBean implements Serializable{
	
	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;

	protected void inicializar() {

	}

	@PostConstruct
	public void construirMB() {
		Flash flash = FlashFactoryUtil.getFlash();
		for (Class<?> clazz = this.getClass(); clazz != null; clazz = clazz
				.getSuperclass()) {
			for (Field field : clazz.getDeclaredFields()) {
				String nameField = field.getName();
				if (flash.containsKey(nameField)) {
					Object object = flash.get(nameField);
					ReflectionUtils.setFieldValue(this, nameField, object);
				}
			}
		}
		inicializar();
	}

	private void putFlash() {
		if (FacesContext.getCurrentInstance() != null) {
			Flash flash = FlashFactoryUtil.getFlash();
			for (Class<?> clazz = this.getClass(); clazz != null; clazz = clazz
					.getSuperclass()) {
				for (Field field : clazz.getDeclaredFields()) {
					if(field.isAnnotationPresent(FlashScoped.class)){
						String nameField = field.getName();
						Object object = ReflectionUtils.getFieldValue(this, field.getName());
						flash.put(nameField, object);
					}
				}
			}
			 flash.setKeepMessages(true);
	         flash.setRedirect(true);
		}
	}

	protected String redirecionar(String pagina) {
		putFlash();
		return pagina + "?faces-redirect=true";
	}
}

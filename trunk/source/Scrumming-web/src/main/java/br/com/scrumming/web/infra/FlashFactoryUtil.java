package br.com.scrumming.web.infra;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class FlashFactoryUtil{
	
	public static Map<String, Object> getFlash(){
		return FacesContext.getCurrentInstance().getExternalContext().getFlash();
	}
}
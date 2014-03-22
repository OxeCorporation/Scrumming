package br.com.scrumming.web.infra;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.context.FlashFactory;

import org.apache.myfaces.shared.context.flash.FlashImpl;

public class FlashFactoryUtil extends FlashFactory {

	private static FlashFactoryUtil INSTANCE = null;
	private FlashFactoryUtil(){}
	
	private static FlashFactoryUtil getInstance(){
		if(INSTANCE == null){
			INSTANCE = new FlashFactoryUtil();
		}
		return INSTANCE;
	}
	
	@Override
	public Flash getFlash(boolean create) {
		return FlashImpl.getCurrentInstance(FacesContext.getCurrentInstance()
				.getExternalContext(), create);
	}

	public static Flash getFlash(){
		return getInstance().getFlash(true);
	}
}

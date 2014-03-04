package br.com.scrumming.core.infra.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MesagemUtil {

	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
			"mensagens", new UTF8Control());

	private static ResourceBundle getBundle() {
		return BUNDLE;
	}

	public static String get(String chave, String... parametros) {
		String mensagem = getBundle().getString(chave);
		if (parametros != null && parametros.length > 0) {
			mensagem = MessageFormat.format(mensagem, new Object[]{parametros});
		}
		return mensagem;
	}
}

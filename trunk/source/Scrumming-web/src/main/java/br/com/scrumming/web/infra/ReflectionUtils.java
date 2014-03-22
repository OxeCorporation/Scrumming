package br.com.scrumming.web.infra;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;
public class ReflectionUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String ERRO_OBTER_VALOR_PROPRIEDADE = "Não foi possível obter o valor da propriedade: ";
	private static final String ERRO_ATRIBUIR_VALOR_PROPRIEDADE = "Não foi possível obter o valor da propriedade: ";

	private static final Logger LOGGER = Logger.getLogger(ReflectionUtils.class);

	public static void setFieldValue(Object object, String fieldName, Object value) {
		boolean configured = false;
		for (Class<?> clazz = object.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(object, value);
				configured = true;
				break;
			} catch (Exception e) {
				// Continuar a busca na classe pai.
			}
		}
		
		if (!configured) {
			LOGGER.error(ERRO_ATRIBUIR_VALOR_PROPRIEDADE + fieldName);
		}
	}

	public static Object getFieldValue(Object object, String fieldName) {
		if (object == null) {
			return null;
		}

		Object retrievedObject = null;
		boolean located = false;
		for (Class<?> clazz = object.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				retrievedObject = field.get(object);
				located = true;
				break;
			} catch (Exception e) {
				// Continuar a busca na classe pai.
			}
		}
		
		if (!located) {
			LOGGER.error(ERRO_OBTER_VALOR_PROPRIEDADE + fieldName);
		}
		
		return retrievedObject;
	}
	
}
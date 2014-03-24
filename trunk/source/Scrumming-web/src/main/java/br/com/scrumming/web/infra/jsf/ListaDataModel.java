package br.com.scrumming.web.infra.jsf;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class ListaDataModel<T extends Serializable> extends ListDataModel<T>  implements SelectableDataModel<T> {

	private static final String UNCHECKED = "unchecked";
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 6375681822811937487L;
	
	
	public ListaDataModel(List<T> lista){
		super(lista);
	}
	
	@SuppressWarnings(UNCHECKED)
	@Override
	public Object getRowKey(T object) {
		return ((List<T>) getWrappedData()).indexOf(object);
	}
	
	@SuppressWarnings(UNCHECKED)
	@Override
	public T getRowData(String rowKey) {
		int index = Integer.valueOf(rowKey);
		if(index >= 0){
			return ((List<T>) getWrappedData()).get(index);
		}
		return null;
	}
}

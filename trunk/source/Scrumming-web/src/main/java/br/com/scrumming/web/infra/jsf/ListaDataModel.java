package br.com.scrumming.web.infra.jsf;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.DataModelEvent;
import javax.faces.model.DataModelListener;

import org.primefaces.model.SelectableDataModel;

public class ListaDataModel<T extends Serializable> extends DataModel<T> implements SelectableDataModel<T>{
	private int _rowIndex = -1;
	private List<T> _data;

	public ListaDataModel() {
	}

	public ListaDataModel(List<T> list) {
		if (list == null) {
			throw new RuntimeException("list is null");
		}
		setWrappedData(list);
	}

	public int getRowCount() {
		if (this._data == null) {
			return -1;
		}
		return this._data.size();
	}

	public T getRowData() {
		if (this._data == null) {
			return null;
		}
		if (!(isRowAvailable())) {
			throw new IllegalArgumentException("row is unavailable");
		}
		return this._data.get(this._rowIndex);
	}

	public int getRowIndex() {
		return this._rowIndex;
	}

	public Object getWrappedData() {
		return this._data;
	}

	public boolean isRowAvailable() {
		return ((this._data != null) && (this._rowIndex >= 0) && (this._rowIndex < this._data
				.size()));
	}

	public void setRowIndex(int rowIndex) {
		if (rowIndex < -1) {
			throw new IllegalArgumentException("illegal rowIndex " + rowIndex);
		}
		int oldRowIndex = this._rowIndex;
		this._rowIndex = rowIndex;
		if ((this._data == null) || (oldRowIndex == this._rowIndex))
			return;
		Object data = (isRowAvailable()) ? getRowData() : null;
		DataModelEvent event = new DataModelEvent(this, this._rowIndex, data);
		DataModelListener[] listeners = getDataModelListeners();
		for (int i = 0; i < listeners.length; ++i) {
			listeners[i].rowSelected(event);
		}
	}

	public void setWrappedData(Object data) {
		if (data == null) {
			setRowIndex(-1);
			this._data = null;
		} else {
			this._data = ((List) data);
			this._rowIndex = -1;
			setRowIndex(0);
		}
	}

	@Override
	public T getRowData(String rowKey) {
		int index = Integer.valueOf(rowKey);
		if (index >= 0) {
			return ((List<T>) getWrappedData()).get(index);
		}
		return null;
	}

	@Override
	public Object getRowKey(T paramT) {
		return ((List<T>) getWrappedData()).indexOf(paramT);
	}
}
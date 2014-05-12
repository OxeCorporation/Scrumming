package br.com.scrumming.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.UsuarioEmpresa;

public class UsuarioEmpresaAdapter extends ArrayAdapter<UsuarioEmpresa> {

	public UsuarioEmpresaAdapter(Context context, List<UsuarioEmpresa> objects) {
		super(context, 0, 0, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		UsuarioEmpresa usuarioEmpresa = getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.linha_empresa, null);

			holder = new ViewHolder();
			holder.txtTexto = (TextView) convertView
					.findViewById(R.id.txtNomeProjeto);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtTexto.setText(usuarioEmpresa.getEmpresa().getNome());

		return convertView;
	}

	static class ViewHolder {
		TextView txtTexto;
	}
}

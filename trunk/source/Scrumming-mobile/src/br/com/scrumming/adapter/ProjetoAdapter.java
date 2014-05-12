package br.com.scrumming.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.Projeto;

public class ProjetoAdapter extends ArrayAdapter<Projeto> {

	
	
	
	public ProjetoAdapter(Context context, List<Projeto> objects) {
		super(context, 0, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		
		Projeto projeto = getItem(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.linha_projeto, null);
			
			holder = new ViewHolder();
			holder.txtNomeProjeto = (TextView) convertView.findViewById(R.id.txtNomeProjeto);
			holder.txtDataInico = (TextView) convertView.findViewById(R.id.txtDataInicioProjeto);
			holder.txtDescricaoProjeto = (TextView) convertView.findViewById(R.id.txtDescricaoProjeto);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtNomeProjeto.setText(projeto.getNome());
		holder.txtDataInico.setText(projeto.getDataInicioFormatada());
		holder.txtDescricaoProjeto.setText(projeto.getDescricao());
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txtNomeProjeto;
		TextView txtDataInico;
		TextView txtDescricaoProjeto;
	}
}

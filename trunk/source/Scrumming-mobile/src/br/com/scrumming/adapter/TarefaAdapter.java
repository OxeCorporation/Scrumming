package br.com.scrumming.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.Tarefa;

public class TarefaAdapter extends ArrayAdapter<Tarefa> {

	public TarefaAdapter(Context context, List<Tarefa> objects) {
		super(context, 0, 0, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		
		Tarefa tarefa = getItem(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.linha_tarefa, null);
			
			holder = new ViewHolder();
			holder.txtNomeTarefa		   = (TextView) convertView.findViewById(R.id.txtNomeTarefa);
			holder.txtDescricaoTarefa 	   = (TextView) convertView.findViewById(R.id.txtDescricaoTarefa);
			holder.txtTempoEstimado		   = (TextView) convertView.findViewById(R.id.txtTempoEstimado);
			holder.txtNomeUsuarioAtribuido = (TextView)convertView.findViewById(R.id.txtNomeUsuarioAtribuido);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		} 
		if (tarefa.getUsuario() == null) {
			holder.txtNomeUsuarioAtribuido.setVisibility(View.GONE);
			holder.txtNomeTarefa.setText(tarefa.getNome());
			holder.txtDescricaoTarefa.setText(tarefa.getDescricao());
			holder.txtTempoEstimado.setText("Estimativa: " + tarefa.getTempoEstimado().toString() + "hr(s)");
		}else{
			holder.txtNomeTarefa.setText(tarefa.getNome());
			holder.txtDescricaoTarefa.setText(tarefa.getDescricao());
			holder.txtTempoEstimado.setText("Estimativa: " + tarefa.getTempoEstimado().toString() + "hr(s)");
			holder.txtNomeUsuarioAtribuido.setText(tarefa.getUsuario().getNome());
		}
		return convertView;
	}
	
	static class ViewHolder {
		TextView txtNomeTarefa;
		TextView txtDescricaoTarefa;
		TextView txtTempoEstimado;
		TextView txtNomeUsuarioAtribuido;
	}
}

package br.com.scrumming.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.TarefaDTO;
import br.com.scrumming.domain.TarefaReporte;

public class TarefaAdapter extends ArrayAdapter<TarefaDTO> {

	public TarefaAdapter(Context context, List<TarefaDTO> objects) {
		super(context, 0, 0, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		
		TarefaDTO tarefaDTO = getItem(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.linha_tarefa, null);
			
			holder = new ViewHolder();
			holder.txtNomeTarefa		   = (TextView) convertView.findViewById(R.id.txtNomeTarefa);
			holder.txtDescricaoTarefa 	   = (TextView) convertView.findViewById(R.id.txtDescricaoTarefa);
			holder.txtTempoEstimado		   = (TextView) convertView.findViewById(R.id.txtTempoEstimado);
			holder.txtNomeUsuarioAtribuido = (TextView) convertView.findViewById(R.id.txtNomeUsuarioAtribuido);
			holder.txtTempoReportado       = (TextView) convertView.findViewById(R.id.textTempoReportado);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		} 
		if (tarefaDTO.getTarefa().getUsuario() == null) {
			holder.txtNomeUsuarioAtribuido.setVisibility(View.GONE);
			holder.txtNomeTarefa.setText(tarefaDTO.getTarefa().getNome());
			holder.txtDescricaoTarefa.setText(tarefaDTO.getTarefa().getDescricao());
			holder.txtTempoEstimado.setText("Estimativa: " + tarefaDTO.getTarefa().getTempoEstimado().toString() + "hr(s)");
			holder.txtTempoReportado.setText("Reportado: " + tarefaDTO.getTotalDeHorasReportadas() + "hs(s)");
		}else{
			holder.txtNomeTarefa.setText(tarefaDTO.getTarefa().getNome());
			holder.txtDescricaoTarefa.setText(tarefaDTO.getTarefa().getDescricao());
			holder.txtTempoEstimado.setText("Estimativa: " + tarefaDTO.getTarefa().getTempoEstimado().toString() + "hr(s)");
			holder.txtNomeUsuarioAtribuido.setText(tarefaDTO.getTarefa().getUsuario().getNome());
			holder.txtTempoReportado.setText("Reportado: " + tarefaDTO.getTotalDeHorasReportadas() + "hs(s)");
		}
		return convertView;
	}
	
	static class ViewHolder {
		TextView txtNomeTarefa;
		TextView txtDescricaoTarefa;
		TextView txtTempoEstimado;
		TextView txtNomeUsuarioAtribuido;
		TextView txtTempoReportado;
	}
}

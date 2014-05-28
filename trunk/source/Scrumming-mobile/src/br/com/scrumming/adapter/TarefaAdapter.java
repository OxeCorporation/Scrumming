package br.com.scrumming.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.TarefaReporte;

public class TarefaAdapter extends ArrayAdapter<TarefaReporte> {

	public TarefaAdapter(Context context, List<TarefaReporte> objects) {
		super(context, 0, 0, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		
		TarefaReporte tarefaReport = getItem(position);
		
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
		if (tarefaReport.getUsuario() == null) {
			holder.txtNomeUsuarioAtribuido.setVisibility(View.GONE);
			holder.txtNomeTarefa.setText(tarefaReport.getTarefa().getNome());
			holder.txtDescricaoTarefa.setText(tarefaReport.getTarefa().getDescricao());
			holder.txtTempoEstimado.setText("Estimativa: " + tarefaReport.getTarefa().getTempoEstimado().toString() + "hr(s)");
			holder.txtTempoReportado.setText("Reportado: " + tarefaReport.getTempoReportado() + "hs(s)");
		}else{
			holder.txtNomeTarefa.setText(tarefaReport.getTarefa().getNome());
			holder.txtDescricaoTarefa.setText(tarefaReport.getTarefa().getDescricao());
			holder.txtTempoEstimado.setText("Estimativa: " + tarefaReport.getTarefa().getTempoEstimado().toString() + "hr(s)");
			holder.txtNomeUsuarioAtribuido.setText(tarefaReport.getTarefa().getUsuario().getNome());
			holder.txtTempoReportado.setText("Reportado: " + tarefaReport.getTempoReportado() + "hs(s)");
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

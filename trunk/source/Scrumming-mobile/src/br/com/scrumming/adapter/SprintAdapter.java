package br.com.scrumming.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.Sprint;

public class SprintAdapter extends ArrayAdapter<Sprint> {

	public SprintAdapter(Context context, List<Sprint> objects) {
		super(context, 0, 0, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		
		Sprint sprint = getItem(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.linha_sprint, null);
			
			holder = new ViewHolder();
			holder.txtNomeSprint = (TextView) convertView.findViewById(R.id.txtNomeSprint);
			holder.txtDataInico = (TextView) convertView.findViewById(R.id.txtDataInicioSprint);
			holder.txtDataFim = (TextView) convertView.findViewById(R.id.txtDataFimSprint);
			holder.txtDescricaoSprint = (TextView) convertView.findViewById(R.id.txtDescricaoSprint);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtNomeSprint.setText(sprint.getNome());
		holder.txtDataInico.setText(sprint.getDataInicioFormatada().toString());
		holder.txtDataFim.setText(sprint.getDataFimFormatada().toString());
		holder.txtDescricaoSprint.setText(sprint.getDescricao());
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txtNomeSprint;
		TextView txtDataInico;
		TextView txtDataFim;
		TextView txtDescricaoSprint;
	}
}

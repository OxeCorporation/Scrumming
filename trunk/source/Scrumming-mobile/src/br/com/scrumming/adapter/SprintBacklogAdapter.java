package br.com.scrumming.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.ItemBacklog;

public class SprintBacklogAdapter extends ArrayAdapter<ItemBacklog> {

	public SprintBacklogAdapter(Context context, List<ItemBacklog> objects) {
		super(context, 0, 0, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		
		ItemBacklog itembacklog = getItem(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.linha_itembacklog, null);
			
			holder = new ViewHolder();
			holder.txtNomeItem = (TextView) convertView.findViewById(R.id.txtNomeItem);
			holder.txtDescricaoItem = (TextView) convertView.findViewById(R.id.txtDescricaoItem);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtNomeItem.setText(itembacklog.getNome());
		holder.txtDescricaoItem.setText(itembacklog.getDescricao());
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txtNomeItem;
		TextView txtDescricaoItem;
	}
}

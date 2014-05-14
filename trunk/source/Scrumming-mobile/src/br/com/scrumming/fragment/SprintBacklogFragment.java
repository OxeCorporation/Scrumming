package br.com.scrumming.fragment;

import java.util.List;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.scrumming.R;
import br.com.scrumming.adapter.SprintBacklogAdapter;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.rest.RestSprintBacklog;

public class SprintBacklogFragment extends ListFragment {
	
	List<ItemBacklog> listaItemBacklog;
	AsyncTaskSprintBacklog taskSprintBacklog;
	Sprint sprint;
	
	public static SprintBacklogFragment novaInstancia(Sprint sprint){
		Bundle args = new Bundle();
		args.putSerializable("sprint", sprint);
		
		SprintBacklogFragment bvf = new SprintBacklogFragment();
		bvf.setArguments(args);
		return bvf;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		if (listaItemBacklog != null){
			AtualizarListaDeItemBacklog();;

		} else {
			if (taskSprintBacklog != null && taskSprintBacklog.getStatus() == Status.RUNNING){
				//mostrarProgress();

			} else {
				taskSprintBacklog = new AsyncTaskSprintBacklog();
				taskSprintBacklog.execute(sprint);
			}
		}
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_sprintbacklog, container, false);
		
		//pega a sprint clicada no sprintFragment para listar os itensbacklog da sprint
		sprint = (Sprint) getArguments().getSerializable("sprint");
		
		return layout;
	}
	
	private void AtualizarListaDeItemBacklog() {
		SprintBacklogAdapter adapter = new SprintBacklogAdapter(getActivity(), listaItemBacklog);
		setListAdapter(adapter);
	}
	
	class AsyncTaskSprintBacklog extends AsyncTask<Sprint, Void, List<ItemBacklog>>{

		@Override
		protected List<ItemBacklog> doInBackground(Sprint... params) {
			return RestSprintBacklog.retornarSprintBacklog(params[0]);
		}
		
		@Override
		protected void onPostExecute(List<ItemBacklog> itemBacklog) {
			super.onPostExecute(itemBacklog);
			if(itemBacklog != null) {
				listaItemBacklog = itemBacklog;
				AtualizarListaDeItemBacklog();;
			}
		}

	}

}

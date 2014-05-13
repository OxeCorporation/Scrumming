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
import br.com.scrumming.adapter.SprintAdapter;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.rest.RestSprint;

public class SprintFragment extends ListFragment {
	
	List<Sprint> listaSprints;
	AsyncTaskSprint taskSprint;
	Projeto projeto;
	
	public static SprintFragment novaInstancia(Projeto projeto){
		Bundle args = new Bundle();
		args.putSerializable("projeto", projeto);
		
		SprintFragment bvf = new SprintFragment();
		bvf.setArguments(args);
		return bvf;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		if (listaSprints != null){
			AtualizarListaDeSprints();

		} else {
			if (taskSprint != null && taskSprint.getStatus() == Status.RUNNING){
				//mostrarProgress();

			} else {
				taskSprint = new AsyncTaskSprint();
				taskSprint.execute(projeto);
			}
		}
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_sprint, container,false);
		
		//pega o projeto clicado no projetoFragment para listar as sprints correspondentes a esse projeto
		projeto = (Projeto) getArguments().getSerializable("projeto");
		
		return layout;
	}
	
	private void AtualizarListaDeSprints() {
		SprintAdapter adapter = new SprintAdapter(getActivity(), listaSprints);
		setListAdapter(adapter);
	}
	
	class AsyncTaskSprint extends AsyncTask<Projeto, Void, List<Sprint>>{

		@Override
		protected List<Sprint> doInBackground(Projeto... params) {
			return RestSprint.retornarSprints(params[0]);
		}
		
		@Override
		protected void onPostExecute(List<Sprint> sprints) {
			super.onPostExecute(sprints);
			if(sprints != null) {
				listaSprints = sprints;
				AtualizarListaDeSprints();
			}
		}
		
		
	}

}

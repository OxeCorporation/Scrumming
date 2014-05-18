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
import br.com.scrumming.adapter.TarefaAdapter;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.rest.RestTarefa;

public class TarefaFragment extends ListFragment {
	
	List<Tarefa> listaTarefa;
	AsyncTaskTarefa taskSprintTarefa;
	ItemBacklog itemBacklog;
	UsuarioEmpresa usuarioEmpresa;
	Integer sprintID, usuarioID;
	
	public static TarefaFragment novaInstancia(ItemBacklog itemBacklog){
		Bundle args = new Bundle();
		args.putSerializable("itemBacklog", itemBacklog);
		//args.putSerializable("usuarioEmpresa", usuarioEmpresa);
		
		TarefaFragment bvf = new TarefaFragment();
		bvf.setArguments(args);
		return bvf;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		if (listaTarefa != null){
			AtualizarListaDeTarefa();;

		} else {
			if (taskSprintTarefa != null && taskSprintTarefa.getStatus() == Status.RUNNING){
				//mostrarProgress();

			} else {
				taskSprintTarefa = new AsyncTaskTarefa();
				taskSprintTarefa.execute(itemBacklog.getCodigo());
			}
		}
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_tarefa, container, false);
		
		itemBacklog = (ItemBacklog) getArguments().getSerializable("itemBacklog");
		//usuarioEmpresa = (UsuarioEmpresa)getArguments().getSerializable("usuarioEmpresa");
		
		return layout;
	}
	
	private void AtualizarListaDeTarefa() {
		TarefaAdapter adapter = new TarefaAdapter(getActivity(), listaTarefa);
		setListAdapter(adapter);
	}
	
	class AsyncTaskTarefa extends AsyncTask<Integer, Void, List<Tarefa>>{

		@Override
		protected List<Tarefa> doInBackground(Integer... params) {
			return RestTarefa.retornarTarefas(params[0]);
		}
		
		@Override
		protected void onPostExecute(List<Tarefa> tarefas) {
			super.onPostExecute(tarefas);
			if(tarefas != null) {
				listaTarefa = tarefas;
				AtualizarListaDeTarefa();;
			}
		}
	}
}

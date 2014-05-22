package br.com.scrumming.fragment;

import java.util.List;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import br.com.scrumming.R;
import br.com.scrumming.adapter.TarefaAdapter;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.rest.RestTarefa;

public class TarefaFragment extends ListFragment {
	
	List<Tarefa> listaTarefa;
	AsyncTaskTarefa taskTarefa;
	ItemBacklog itemBacklog;
	UsuarioEmpresa usuarioEmpresa;
	Integer sprintID, usuarioID;
	
	public static TarefaFragment novaInstancia(ItemBacklog itemBacklog, UsuarioEmpresa usuarioEmpresa){
		Bundle args = new Bundle();
		args.putSerializable("itemBacklog", itemBacklog);
		args.putSerializable("usuarioEmpresa", usuarioEmpresa);
		
		TarefaFragment bvf = new TarefaFragment();
		bvf.setArguments(args);
		return bvf;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		
		ActionBar ab = ((ActionBarActivity)getActivity()).getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("Tarefas");
		
		if (listaTarefa != null){
			AtualizarListaDeTarefa();;

		} else {
			if (taskTarefa != null && taskTarefa.getStatus() == Status.RUNNING){
				//mostrarProgress();

			} else {
				taskTarefa = new AsyncTaskTarefa();
				taskTarefa.execute(itemBacklog.getCodigo());
			}
		}
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_tarefa, container, false);
		
		//pega a sprint clicada no sprintFragment para listar os itensbacklog da sprint
		itemBacklog = (ItemBacklog) getArguments().getSerializable("itemBacklog");
		usuarioEmpresa = (UsuarioEmpresa)getArguments().getSerializable("usuarioEmpresa");
		
		return layout;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_fragment_telas, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.logout:
			if (getActivity() instanceof ClickedOnLogout) {
				((ClickedOnLogout)getActivity()).clicouNoLogout(usuarioEmpresa);;
			}
			break;

		case android.R.id.home:
			if (getActivity() instanceof ClickedOnHome) {
				((ClickedOnHome)getActivity()).clicouNoHome(usuarioEmpresa);
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void AtualizarListaDeTarefa() {
		TarefaAdapter adapter = new TarefaAdapter(getActivity(), listaTarefa);
		setListAdapter(adapter);
	}
	
	class AsyncTaskTarefa extends AsyncTask<Integer, Void, List<Tarefa>>{

		@Override
		protected List<Tarefa> doInBackground(Integer... params) {
			return RestTarefa.retornarTarefa(params[0]);
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

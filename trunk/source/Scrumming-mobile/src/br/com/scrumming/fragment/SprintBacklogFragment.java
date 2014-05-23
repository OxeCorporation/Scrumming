package br.com.scrumming.fragment;

import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.adapter.SprintBacklogAdapter;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnItemBacklog;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.rest.RestSprintBacklog;

public class SprintBacklogFragment extends ListFragment {
	
	List<ItemBacklog> listaItemBacklog;
	AsyncTaskSprintBacklog taskSprintBacklog;
	Sprint sprint;
	UsuarioEmpresa usuarioEmpresa;
	Integer sprintID, usuarioID;
	Projeto projeto;
	ProgressBar progressSprintBacklog;
	TextView txtMensagemSprintBacklog;
	
	
	public static SprintBacklogFragment novaInstancia(Sprint sprint, UsuarioEmpresa usuarioEmpresa){
		Bundle args = new Bundle();
		args.putSerializable("sprint", sprint);
		args.putSerializable("usuarioEmpresa", usuarioEmpresa);
		
		SprintBacklogFragment bvf = new SprintBacklogFragment();
		bvf.setArguments(args);
		return bvf;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		
		//Transforma o Home "Scrumming" em um botão
		ActionBar ab = ((ActionBarActivity)getActivity()).getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("SprintBacklog");
		
		if (listaItemBacklog != null){
			progressSprintBacklog.setVisibility(View.GONE);
			txtMensagemSprintBacklog.setVisibility(View.GONE);
			AtualizarListaDeItemBacklog();;

		} else {
			if (taskSprintBacklog != null && taskSprintBacklog.getStatus() == Status.RUNNING){
				mostrarProgress();

			} else {
				iniciarDownload();
			}
		}
		
	}
	
	private void mostrarProgress() {
		progressSprintBacklog.setVisibility(View.VISIBLE);
		txtMensagemSprintBacklog.setVisibility(View.VISIBLE);
		txtMensagemSprintBacklog.setText("Carregando...");
	}

	private void iniciarDownload(){
		
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
			taskSprintBacklog = new AsyncTaskSprintBacklog();
			taskSprintBacklog.execute(sprint.getCodigo(), usuarioEmpresa.getUsuario().getCodigo());

		} else {
			progressSprintBacklog.setVisibility(View.GONE);
			txtMensagemSprintBacklog.setVisibility(View.VISIBLE);
			txtMensagemSprintBacklog.setText("Sem conexao com a Internet");
		}
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_sprintbacklog, container, false);
		
		progressSprintBacklog    = (ProgressBar)layout.findViewById(R.id.progressBarSprintBacklog);
		txtMensagemSprintBacklog = (TextView)layout.findViewById(R.id.txtMensagemSprintBacklog);
		
		//pega a sprint clicada no sprintFragment para listar os itensbacklog da sprint
		sprint         = (Sprint) getArguments().getSerializable("sprint");
		usuarioEmpresa = (UsuarioEmpresa)getArguments().getSerializable("usuarioEmpresa");
		projeto 	   = (Projeto)sprint.getProjeto();
		
		return layout;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_fragment_telas, menu);
	}
	
	/**
	 * @author Naftali
	 * Método que manipula os componentes do ActionBar
	 * @param item do tipo MenuItem
	 * @return boolean (true)
	 * 
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.logout:
			if (getActivity() instanceof ClickedOnLogout) {
				((ClickedOnLogout)getActivity()).clicouNoLogout(null);;
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
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (getActivity() instanceof ClickedOnItemBacklog) {
			((ClickedOnItemBacklog)getActivity()).itemBacklogFoiClicada(listaItemBacklog.get(position), usuarioEmpresa, sprint);
		}
	}

	private void AtualizarListaDeItemBacklog() {
		SprintBacklogAdapter adapter = new SprintBacklogAdapter(getActivity(), listaItemBacklog);
		setListAdapter(adapter);
	}
	
	class AsyncTaskSprintBacklog extends AsyncTask<Integer, Void, List<ItemBacklog>>{
		
		@Override
		protected void onPreExecute() {
			mostrarProgress();
		}
		
		@Override
		protected List<ItemBacklog> doInBackground(Integer... params) {
			return RestSprintBacklog.retornarSprintBacklog(params[0], params[1]);
		}
		
		@Override
		protected void onPostExecute(List<ItemBacklog> itemBacklog) {
			super.onPostExecute(itemBacklog);
			if(itemBacklog != null) {
				listaItemBacklog = itemBacklog;
				AtualizarListaDeItemBacklog();
				txtMensagemSprintBacklog.setVisibility(View.GONE);
			}else {
				txtMensagemSprintBacklog.setText("Não Existe Projetos Cadastrados");
			}
			progressSprintBacklog.setVisibility(View.GONE);
		}

	}

}

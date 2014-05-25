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
import br.com.scrumming.adapter.SprintAdapter;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.interfaces.ClickedOnSprint;
import br.com.scrumming.rest.RestSprint;

public class SprintFragment extends ListFragment {
	
	List<Sprint> listaSprints;
	AsyncTaskSprint taskSprint;
	Projeto projeto;
	UsuarioEmpresa usuarioEmpresa;
	ProgressBar progressSprint;
	TextView txtMensagemSprint;
	
	public static SprintFragment novaInstancia(Projeto projeto, UsuarioEmpresa usuarioEmpresa){
		Bundle args = new Bundle();
		args.putSerializable("projeto", projeto);
		args.putSerializable("usuarioEmpresa", usuarioEmpresa);
		SprintFragment bvf = new SprintFragment();
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
		ab.setTitle("Sprints");
		
		if (listaSprints != null){
			progressSprint.setVisibility(View.GONE);
			txtMensagemSprint.setVisibility(View.GONE);
			AtualizarListaDeSprints();

		} else {
			if (taskSprint != null && taskSprint.getStatus() == Status.RUNNING){
				mostrarProgress();

			} else {
				iniciarDownload();
			}
		}
		
	}
	
	private void mostrarProgress() {
		progressSprint.setVisibility(View.VISIBLE);
		txtMensagemSprint.setVisibility(View.VISIBLE);
		txtMensagemSprint.setText("Carregando...");
	}

	private void iniciarDownload(){
		
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
			taskSprint = new AsyncTaskSprint();
			taskSprint.execute(projeto);

		} else {
			progressSprint.setVisibility(View.GONE);
			txtMensagemSprint.setVisibility(View.VISIBLE);
			txtMensagemSprint.setText("Sem conexao com a Internet");
		}
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_sprint, container, false);
		
		progressSprint    = (ProgressBar)layout.findViewById(R.id.progressBarSprint);
		txtMensagemSprint = (TextView)layout.findViewById(R.id.txtMensagemSprint);
		
		//pega o projeto clicado no projetoFragment para listar as sprints correspondentes a esse projeto
		projeto = (Projeto) getArguments().getSerializable("projeto");
		usuarioEmpresa = (UsuarioEmpresa) getArguments().getSerializable("usuarioEmpresa");
		
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
		if (getActivity() instanceof ClickedOnSprint) {
			((ClickedOnSprint)getActivity()).sprintFoiClicada(listaSprints.get(position), usuarioEmpresa);
		}
	}
	
	private void AtualizarListaDeSprints() {
		SprintAdapter adapter = new SprintAdapter(getActivity(), listaSprints);
		setListAdapter(adapter);
	}
	
	class AsyncTaskSprint extends AsyncTask<Projeto, Void, List<Sprint>>{

		@Override
		protected void onPreExecute() {
			mostrarProgress();
		}
		
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
				txtMensagemSprint.setVisibility(View.GONE);
			}else{
				txtMensagemSprint.setText("Não Existe Projetos Cadastrados");
			}
			progressSprint.setVisibility(View.GONE);
		}
	}
}

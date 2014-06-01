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
	
	//Instancia��o dos Objetos e vari�veis
	List<Sprint> listaSprints;
	AsyncTaskSprint taskSprint;
	Projeto projeto;
	UsuarioEmpresa usuarioEmpresa;
	ProgressBar progressSprint;
	TextView txtMensagemSprint;
	
	/**
	* M�todo que gera uma nova instancia do fragment de Sprint
	* @param Projeto projeto
	* @param UsuarioEmpresa usuarioEmpresa
	* @return SpringBacklogFragment
	*/
	public static SprintFragment novaInstancia(Projeto projeto, UsuarioEmpresa usuarioEmpresa){
		Bundle args = new Bundle();
		args.putSerializable("projeto", projeto);
		args.putSerializable("usuarioEmpresa", usuarioEmpresa);
		SprintFragment sf = new SprintFragment();
		sf.setArguments(args);
		return sf;
	}
	
	/**
	* M�todo utilizado no momento que a Activity do fragment � criada
	* @param Bundle savedInstanceState
	* @return void
	*/
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		
		//Transforma o Home "Scrumming" em um bot�o
		ActionBar ab = ((ActionBarActivity)getActivity()).getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("Sprints");
		ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_principal));
		
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
	
	/**
	* M�todo utilizado para exibir uma imagem de Carregando enquanto os dados estiverem sendo baixados
	* @return void
	*/
	private void mostrarProgress() {
		progressSprint.setVisibility(View.VISIBLE);
		txtMensagemSprint.setVisibility(View.VISIBLE);
		txtMensagemSprint.setText("Carregando...");
	}

	/**
	* M�todo utlilizado para realizar o download dos dados atrav�s de uma AsyncTask especifica
	* @return void
	*/
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
	
	/**
	* M�todo utilizado no momento que a View � criada
	* @param LayoutInflater inflater
	* @param ViewGroup container
	* @param Bundle savedInstanceState
	* @return View
	*/
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_sprint, container, false);
		
		progressSprint = (ProgressBar)layout.findViewById(R.id.progressBarSprint);
		txtMensagemSprint = (TextView)layout.findViewById(R.id.txtMensagemSprint);
		
		//pega o projeto clicado no projetoFragment para listar as sprints correspondentes a esse projeto
		projeto = (Projeto) getArguments().getSerializable("projeto");
		usuarioEmpresa = (UsuarioEmpresa) getArguments().getSerializable("usuarioEmpresa");
		
		return layout;
	}
	
	/**
	* M�todo utilizado para exibir as op�oes de menu
	* @param Menu menu
	* @param MenuInflater inflater
	* @return Void
	*/
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_fragment_telas, menu);
	}
	
	/**
	 * @author Naftali
	 * M�todo que manipula os componentes do ActionBar
	 * @param item do tipo MenuItem
	 * @return boolean (true)
	 * 
	 * */
	
	/**
	* M�todo utilizado ao clicar em uma op��o no menu
	* @param MenuItem item
	* @return boolean
	*/
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
	
	/**
	* M�todo utilizado ao clicar em um item da lista do fragment
	* @param ListView l
	* @param View v
	* @param int position
	* @param long id
	* @return void
	*/
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (getActivity() instanceof ClickedOnSprint) {
			((ClickedOnSprint)getActivity()).sprintFoiClicada(listaSprints.get(position), usuarioEmpresa);
		}
	}
	
	/**
	* M�todo utilizado para atualizar a lista do fragment de Sprint
	* @return void
	*/
	private void AtualizarListaDeSprints() {
		SprintAdapter adapter = new SprintAdapter(getActivity(), listaSprints);
		setListAdapter(adapter);
	}
	
	//InnerClass do AsyncTask da Empresa
	class AsyncTaskSprint extends AsyncTask<Projeto, Void, List<Sprint>>{

		/**
		* M�todo proviniente da heran�a do AsyncTask para executar algo antes do DoInBackground 
		* @return void
		*/
		@Override
		protected void onPreExecute() {
			mostrarProgress();
		}
		
		/**
		* M�todo proviniente da heran�a do AsyncTask para executar algo em uma thread paralela a Activity atual
		* @param Projeto... params
		* @return Lista de Sprints
		*/
		@Override
		protected List<Sprint> doInBackground(Projeto... params) {
			return RestSprint.retornarSprints(params[0]);
		}
		
		/**
		* M�todo proviniente da heran�a do AsyncTask para executar algo depois do DoInBackground 
		* @param Lista de Sprints
		* @return void
		*/
		@Override
		protected void onPostExecute(List<Sprint> sprints) {
			super.onPostExecute(sprints);
			if(sprints != null) {
				listaSprints = sprints;
				AtualizarListaDeSprints();
				txtMensagemSprint.setVisibility(View.GONE);
			}else{
				txtMensagemSprint.setText("N�o Existem Sprints Cadastradas");
			}
			progressSprint.setVisibility(View.GONE);
		}
	}
}
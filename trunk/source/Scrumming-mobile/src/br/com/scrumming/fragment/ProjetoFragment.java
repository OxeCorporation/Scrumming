package br.com.scrumming.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
//import android.app.ActionBar;
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
import br.com.scrumming.adapter.ProjetoAdapter;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.interfaces.ClickedOnProjeto;
import br.com.scrumming.rest.RestProjeto;

@SuppressLint("NewApi")
public class ProjetoFragment extends ListFragment{

	AsyncTaskProjeto taskProjeto;
	List<Projeto> listaProjetos;
	UsuarioEmpresa usuarioEmpresa;
	ProgressBar progressProjeto;
	TextView txtMensagemProjeto;
	
	Empresa empresa;
	
	public static ProjetoFragment novaInstancia(UsuarioEmpresa usuarioEmpresa){
		Bundle args = new Bundle();
		args.putSerializable("usuarioEmpresa", usuarioEmpresa);
		
		ProjetoFragment pf = new ProjetoFragment();
		pf.setArguments(args);
		return pf;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		
		ActionBar ab = ((ActionBarActivity)getActivity()).getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("Projetos");
		
		if (listaProjetos != null){
			progressProjeto.setVisibility(View.GONE);
			txtMensagemProjeto.setVisibility(View.GONE);
			AtualizarListaDeProjetos();

		} else {
			if (taskProjeto != null && taskProjeto.getStatus() == Status.RUNNING){
				mostrarProgress();
				
			} else {
				iniciarDownload();
			}
		}
	}
	
	private void mostrarProgress() {
		progressProjeto.setVisibility(View.VISIBLE);
		txtMensagemProjeto.setVisibility(View.VISIBLE);
		txtMensagemProjeto.setText("Carregando...");
	}

	private void iniciarDownload(){
		
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
			taskProjeto = new AsyncTaskProjeto();
			taskProjeto.execute(usuarioEmpresa);

		} else {
			progressProjeto.setVisibility(View.GONE);
			txtMensagemProjeto.setVisibility(View.VISIBLE);
			txtMensagemProjeto.setText("Sem conexao com a Internet");
		}
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_projetos, container,false);
		
		progressProjeto = (ProgressBar)layout.findViewById(R.id.progressBarProjeto);
		txtMensagemProjeto = (TextView)layout.findViewById(R.id.txtMensagemProjeto);
		
		usuarioEmpresa = (UsuarioEmpresa) getArguments().getSerializable("usuarioEmpresa");
		
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
		if (getActivity() instanceof ClickedOnProjeto) {
			((ClickedOnProjeto)getActivity()).projetoFoiClicado(listaProjetos.get(position), usuarioEmpresa);
		}
	}
	
	private void AtualizarListaDeProjetos() {
		ProjetoAdapter adapter = new ProjetoAdapter(getActivity(), listaProjetos);
		setListAdapter(adapter);
		if (listaProjetos.size()==1){
			if (getActivity() instanceof ClickedOnProjeto) {
				((ClickedOnProjeto)getActivity()).projetoFoiClicado(listaProjetos.get(0), usuarioEmpresa);
			}
		}
	}
	
	class AsyncTaskProjeto extends AsyncTask<UsuarioEmpresa, Void, List<Projeto>>{
		
		@Override
		protected void onPreExecute() {
			mostrarProgress();
		}
		
		@Override
		protected List<Projeto> doInBackground(UsuarioEmpresa... params) {
			return RestProjeto.retorneProjetosPorUsuario(params[0]);
		}

		@Override
		protected void onPostExecute(List<Projeto> projetos) {
			super.onPostExecute(projetos);
			if(projetos != null) {
				listaProjetos = projetos;
				AtualizarListaDeProjetos();
				txtMensagemProjeto.setVisibility(View.GONE);
			}else{
				txtMensagemProjeto.setText("Não Existem Projetos Cadastrados");
			}
			progressProjeto.setVisibility(View.GONE);
		}
	}
}
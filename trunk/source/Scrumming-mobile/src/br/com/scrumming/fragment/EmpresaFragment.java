package br.com.scrumming.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
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
import br.com.scrumming.adapter.UsuarioEmpresaAdapter;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.ProjetoFragment.AsyncTaskProjeto;
import br.com.scrumming.interfaces.ClickedOnEmpresa;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.rest.RestEmpresa;

@SuppressLint("NewApi")
public class EmpresaFragment extends ListFragment {

	Usuario usuario;
	TextView txtNome;
	List<UsuarioEmpresa> listaEmpresas;
	AsyncTaskEmpresa task;
	ProgressBar progressEmpresa;
	TextView txtMensagemEmpresa;

	public static EmpresaFragment novaInstancia(Usuario usuario) {
		Bundle args = new Bundle();
		args.putSerializable("usuario", usuario);

		EmpresaFragment bvf = new EmpresaFragment();
		bvf.setArguments(args);
		return bvf;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		
		if (listaEmpresas != null) {
			progressEmpresa.setVisibility(View.GONE);
			txtMensagemEmpresa.setVisibility(View.GONE);
			AtualizarLista();

		} else {
			if (task != null && task.getStatus() == Status.RUNNING) {
				 mostrarProgress();

			} else {
				iniciarDownload();
			}
		}
	}
	
	private void mostrarProgress() {
		progressEmpresa.setVisibility(View.VISIBLE);
		txtMensagemEmpresa.setVisibility(View.VISIBLE);
		txtMensagemEmpresa.setText("Carregando...");
	}
	
	private void iniciarDownload(){

		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
			task = new AsyncTaskEmpresa();
			task.execute(usuario);

		} else {
			progressEmpresa.setVisibility(View.GONE);
			txtMensagemEmpresa.setVisibility(View.VISIBLE);
			txtMensagemEmpresa.setText("Sem conexao com a Internet");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View layout = inflater.inflate(R.layout.fragment_empresas, container, false);
		
		progressEmpresa    = (ProgressBar)layout.findViewById(R.id.progressBarEmpresa);
		txtMensagemEmpresa = (TextView)layout.findViewById(R.id.txtMensagemEmpresa);
		
		usuario = (Usuario) getArguments().getSerializable("usuario");

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
				((ClickedOnLogout)getActivity()).clicouNoLogout(null);
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (getActivity() instanceof ClickedOnEmpresa) {
			((ClickedOnEmpresa) getActivity()).empresaFoiClicada(listaEmpresas
					.get(position));
		}
	}

	private void AtualizarLista() {
		UsuarioEmpresaAdapter adapter = new UsuarioEmpresaAdapter(
				getActivity(), listaEmpresas);
		setListAdapter(adapter);
		if (listaEmpresas.size() == 1) {
			if (getActivity() instanceof ClickedOnEmpresa) {
				((ClickedOnEmpresa) getActivity())
						.empresaFoiClicada(listaEmpresas.get(0));
			}
		}
	}

	class AsyncTaskEmpresa extends
			AsyncTask<Usuario, Void, List<UsuarioEmpresa>> {
		
		@Override
		protected void onPreExecute() {
			mostrarProgress();
		}
		
		@Override
		protected List<UsuarioEmpresa> doInBackground(Usuario... params) {
			return RestEmpresa.retorneEmpresas(params[0]);
		}

		@Override
		protected void onPostExecute(List<UsuarioEmpresa> usuarioEmpresa) {
			super.onPostExecute(usuarioEmpresa);
			if (usuarioEmpresa != null) {
				listaEmpresas = usuarioEmpresa;
				AtualizarLista();
				txtMensagemEmpresa.setVisibility(View.GONE);
			}else{
				txtMensagemEmpresa.setText("Não Existe Empresas Cadastrados");
			}
			progressEmpresa.setVisibility(View.GONE);
		}
	}
}
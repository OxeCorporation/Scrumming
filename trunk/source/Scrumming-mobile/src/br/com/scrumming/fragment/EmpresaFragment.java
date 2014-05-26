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
import br.com.scrumming.interfaces.ClickedOnEmpresa;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.rest.RestEmpresa;

@SuppressLint("NewApi")
public class EmpresaFragment extends ListFragment {

	//Instanciação dos Objetos e variáveis
	Usuario usuario;
	TextView txtNome;
	List<UsuarioEmpresa> listaEmpresas;
	AsyncTaskEmpresa task;
	ProgressBar progressEmpresa;
	TextView txtMensagemEmpresa;

	/**
	* Método que gera uma nova instancia do fragment de Empresa
	* @param Usuario usuario
	* @return EmpresaFragment
	*/
	public static EmpresaFragment novaInstancia(Usuario usuario) {
		Bundle args = new Bundle();
		args.putSerializable("usuario", usuario);

		EmpresaFragment ef = new EmpresaFragment();
		ef.setArguments(args);
		return ef;
	}
	
	/**
	* Método utilizado no momento que a Activity do fragment é criada
	* @param Bundle savedInstanceState
	* @return void
	*/
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
	
	/**
	* Método utilizado para exibir uma imagem de Carregando enquanto os dados estiverem sendo baixados
	* @return void
	*/
	private void mostrarProgress() {
		progressEmpresa.setVisibility(View.VISIBLE);
		txtMensagemEmpresa.setVisibility(View.VISIBLE);
		txtMensagemEmpresa.setText("Carregando...");
	}
	
	/**
	* Método utlilizado para realizar o download dos dados através de uma AsyncTask especifica
	* @return void
	*/
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
	
	/**
	* Método utilizado no momento que a View é criada
	* @param LayoutInflater inflater
	* @param ViewGroup container
	* @param Bundle savedInstanceState
	* @return View
	*/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View layout = inflater.inflate(R.layout.fragment_empresas, container, false);
		
		progressEmpresa = (ProgressBar)layout.findViewById(R.id.progressBarEmpresa);
		txtMensagemEmpresa = (TextView)layout.findViewById(R.id.txtMensagemEmpresa);
		
		usuario = (Usuario) getArguments().getSerializable("usuario");

		return layout;
	}
	
	/**
	* Método utilizado para exibir as opçoes de menu
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
	* Método utilizado ao clicar em uma opção no menu
	* @param MenuItem item
	* @return boolean
	*/
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
	
	/**
	* Método utilizado ao clicar em um item da lista do fragment
	* @param ListView l
	* @param View v
	* @param int position
	* @param long id
	* @return void
	*/
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (getActivity() instanceof ClickedOnEmpresa) {
			((ClickedOnEmpresa) getActivity()).empresaFoiClicada(listaEmpresas
					.get(position));
		}
	}

	/**
	* Método utilizado para atualizar a lista do fragment de Empresa
	* @return void
	*/
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

	//InnerClass do AsyncTask da Empresa
	class AsyncTaskEmpresa extends
			AsyncTask<Usuario, Void, List<UsuarioEmpresa>> {
		
		/**
		* Método proviniente da herança do AsyncTask para executar algo antes do DoInBackground 
		* @return void
		*/
		@Override
		protected void onPreExecute() {
			mostrarProgress();
		}
		
		/**
		* Método proviniente da herança do AsyncTask para executar algo em uma thread paralela a Activity atual
		* @param Usuario... params
		* @return Lista de UsuarioEmpresa
		*/
		@Override
		protected List<UsuarioEmpresa> doInBackground(Usuario... params) {
			return RestEmpresa.retorneEmpresas(params[0]);
		}

		/**
		* Método proviniente da herança do AsyncTask para executar algo depois do DoInBackground 
		* @param Lista de UsuarioEmpresa
		* @return void
		*/
		@Override
		protected void onPostExecute(List<UsuarioEmpresa> usuarioEmpresa) {
			super.onPostExecute(usuarioEmpresa);
			if (usuarioEmpresa != null) {
				listaEmpresas = usuarioEmpresa;
				AtualizarLista();
				txtMensagemEmpresa.setVisibility(View.GONE);
			}else{
				txtMensagemEmpresa.setText("Não Existem Empresas Cadastradas");
			}
			progressEmpresa.setVisibility(View.GONE);
		}
	}
}
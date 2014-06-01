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

	// Instancia��o dos Objetos e vari�veis
	List<ItemBacklog> listaItemBacklog;
	AsyncTaskSprintBacklog taskSprintBacklog;
	Sprint sprint;
	UsuarioEmpresa usuarioEmpresa;
	Integer sprintID, usuarioID;
	Projeto projeto;
	ProgressBar progressSprintBacklog;
	TextView txtMensagemSprintBacklog;
	boolean bloquear = true;

	/**
	 * M�todo que gera uma nova instancia do fragment de ItemBacklog
	 * 
	 * @param Sprint
	 *            sprint
	 * @param UsuarioEmpresa
	 *            usuarioEmpresa
	 * @return SpringBacklogFragment
	 */
	public static SprintBacklogFragment novaInstancia(Sprint sprint,
			UsuarioEmpresa usuarioEmpresa) {
		Bundle args = new Bundle();
		args.putSerializable("sprint", sprint);
		args.putSerializable("usuarioEmpresa", usuarioEmpresa);

		SprintBacklogFragment sbf = new SprintBacklogFragment();
		sbf.setArguments(args);
		return sbf;
	}

	/**
	 * M�todo utilizado no momento que a Activity do fragment � criada
	 * 
	 * @param Bundle
	 *            savedInstanceState
	 * @return void
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);

		// Transforma o Home "Scrumming" em um bot�o
		ActionBar ab = ((ActionBarActivity) getActivity())
				.getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("SprintBacklog");
		ab.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.bg_principal));

		if (listaItemBacklog != null) {
			progressSprintBacklog.setVisibility(View.GONE);
			txtMensagemSprintBacklog.setVisibility(View.GONE);
			AtualizarListaDeItemBacklog();
			;

		} else {
			if (taskSprintBacklog != null
					&& taskSprintBacklog.getStatus() == Status.RUNNING) {
				mostrarProgress();

			} else {
				iniciarDownload();
			}
		}

	}

	/**
	 * M�todo utilizado para exibir uma imagem de Carregando enquanto os dados
	 * estiverem sendo baixados
	 * 
	 * @return void
	 */
	private void mostrarProgress() {
		progressSprintBacklog.setVisibility(View.VISIBLE);
		txtMensagemSprintBacklog.setVisibility(View.VISIBLE);
		txtMensagemSprintBacklog.setText("Carregando...");
	}

	/**
	 * M�todo utlilizado para realizar o download dos dados atrav�s de uma
	 * AsyncTask especifica
	 * 
	 * @return void
	 */
	private void iniciarDownload() {

		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnected()) {
			taskSprintBacklog = new AsyncTaskSprintBacklog();
			taskSprintBacklog.execute(sprint.getCodigo(), usuarioEmpresa
					.getUsuario().getCodigo());

		} else {
			progressSprintBacklog.setVisibility(View.GONE);
			txtMensagemSprintBacklog.setVisibility(View.VISIBLE);
			txtMensagemSprintBacklog.setText("Sem conexao com a Internet");
		}
	}

	/**
	 * M�todo utilizado no momento que a View � criada
	 * 
	 * @param LayoutInflater
	 *            inflater
	 * @param ViewGroup
	 *            container
	 * @param Bundle
	 *            savedInstanceState
	 * @return View
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View layout = inflater.inflate(R.layout.fragment_sprintbacklog,
				container, false);

		progressSprintBacklog = (ProgressBar) layout
				.findViewById(R.id.progressBarSprintBacklog);
		txtMensagemSprintBacklog = (TextView) layout
				.findViewById(R.id.txtMensagemSprintBacklog);

		// pega a sprint clicada no sprintFragment para listar os itensbacklog
		// da sprint
		sprint = (Sprint) getArguments().getSerializable("sprint");
		usuarioEmpresa = (UsuarioEmpresa) getArguments().getSerializable(
				"usuarioEmpresa");
		projeto = (Projeto) sprint.getProjeto();

		return layout;
	}

	/**
	 * M�todo utilizado para exibir as op�oes de menu
	 * 
	 * @param Menu
	 *            menu
	 * @param MenuInflater
	 *            inflater
	 * @return Void
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_fragment_telas, menu);
	}

	/**
	 * @author Naftali M�todo que manipula os componentes do ActionBar
	 * @param item
	 *            do tipo MenuItem
	 * @return boolean (true)
	 * 
	 * */

	/**
	 * M�todo utilizado ao clicar em uma op��o no menu
	 * 
	 * @param MenuItem
	 *            item
	 * @return boolean
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.logout:
			if (getActivity() instanceof ClickedOnLogout) {
				((ClickedOnLogout) getActivity()).clicouNoLogout(null);
				;
			}
			break;

		case android.R.id.home:
			if (bloquear == false) {
				if (getActivity() instanceof ClickedOnHome) {
					((ClickedOnHome) getActivity())
							.clicouNoHome(usuarioEmpresa);
				}
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * M�todo utilizado ao clicar em um item da lista do fragment
	 * 
	 * @param ListView
	 *            l
	 * @param View
	 *            v
	 * @param int position
	 * @param long id
	 * @return void
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (getActivity() instanceof ClickedOnItemBacklog) {
			((ClickedOnItemBacklog) getActivity()).itemBacklogFoiClicada(
					listaItemBacklog.get(position), usuarioEmpresa, sprint);
		}
	}

	/**
	 * M�todo utilizado para atualizar a lista do fragment de ItemBacklog
	 * 
	 * @return void
	 */
	private void AtualizarListaDeItemBacklog() {
		SprintBacklogAdapter adapter = new SprintBacklogAdapter(getActivity(),
				listaItemBacklog);
		setListAdapter(adapter);
	}

	// InnerClass do AsyncTask da Empresa
	class AsyncTaskSprintBacklog extends
			AsyncTask<Integer, Void, List<ItemBacklog>> {

		/**
		 * M�todo proviniente da heran�a do AsyncTask para executar algo antes
		 * do DoInBackground
		 * 
		 * @return void
		 */
		@Override
		protected void onPreExecute() {
			bloquear = true;
			mostrarProgress();
		}

		/**
		 * M�todo proviniente da heran�a do AsyncTask para executar algo em uma
		 * thread paralela a Activity atual
		 * 
		 * @param Integer
		 *            ... params
		 * @return Lista de ItemBacklogs
		 */
		@Override
		protected List<ItemBacklog> doInBackground(Integer... params) {
			return RestSprintBacklog
					.retornarSprintBacklog(params[0], params[1]);
		}

		/**
		 * M�todo proviniente da heran�a do AsyncTask para executar algo depois
		 * do DoInBackground
		 * 
		 * @param Lista
		 *            de ItemBacklogs
		 * @return void
		 */
		@Override
		protected void onPostExecute(List<ItemBacklog> itemBacklog) {
			super.onPostExecute(itemBacklog);
			if (itemBacklog != null) {
				listaItemBacklog = itemBacklog;
				AtualizarListaDeItemBacklog();
				txtMensagemSprintBacklog.setVisibility(View.GONE);
			} else {
				txtMensagemSprintBacklog
						.setText("N�o Existem Itens de Backlog Cadastrados");
			}
			bloquear = false;
			progressSprintBacklog.setVisibility(View.GONE);
		}
	}
}
package br.com.scrumming.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.adapter.TarefaAdapter;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;
import br.com.scrumming.domain.TarefaDTO;
import br.com.scrumming.domain.TarefaFavorita;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.interfaces.ClickedOnTarefaReporteItem;
import br.com.scrumming.interfaces.MudarParaProcesso;
import br.com.scrumming.rest.RestTarefa;
import br.com.scrumming.rest.RestTarefaFavorita;

public class TarefaProcessFragment extends ListFragment {

	// Instanciação dos Objetos e variáveis
	List<TarefaDTO> listaTarefaProcesso;
	// AsyncTaskTarefa taskTarefa;
	ItemBacklog itemBacklog;
	UsuarioEmpresa usuarioEmpresa;
	Integer sprintID, usuarioID;
	Sprint sprint;
	SprintBacklog sprintBacklog;
	ProgressBar progressTarefa;
	TextView txtMensagemTarefa, txtMensagemTarefaStatus;
	TarefaDTO tarefaSelecionada;
	TarefaFavorita tarefaFavorita;
	boolean bloquear = false;

	/**
	 * Método que gera uma nova instancia do fragment de TarefaProcess
	 * 
	 * @param ItemBacklog
	 *            itemBacklog
	 * @param UsuarioEmpresa
	 *            usuarioEmpresa
	 * @param Sprint
	 *            sprint
	 * @return TarefaProcessFragment
	 */
	public static TarefaProcessFragment novaInstancia(ItemBacklog itemBacklog,
			UsuarioEmpresa usuarioEmpresa, Sprint sprint) {
		Bundle args = new Bundle();
		args.putSerializable("itemBacklog", itemBacklog);
		args.putSerializable("usuarioEmpresa", usuarioEmpresa);
		args.putSerializable("sprint", sprint);
		TarefaProcessFragment tf = new TarefaProcessFragment();
		tf.setArguments(args);
		return tf;
	}

	public void atualizarLista(TarefaDTO tarefa) {
		listaTarefaProcesso.add(tarefa);
	}

	public void receberListaTarafaDTO(List<TarefaDTO> listaTarafaDTO) {
		listaTarefaProcesso = new ArrayList<TarefaDTO>();
		for (int i = 0; i < listaTarafaDTO.size(); i++) {
			if (listaTarafaDTO.get(i).getTarefa().getSituacao() == SituacaoTarefaEnum.FAZENDO) {
				listaTarefaProcesso.add(listaTarafaDTO.get(i));
			}
		}
		bloquear = false;
		AtualizarListaDeTarefa();
	}

	/**
	 * Método utilizado no momento que a Activity do fragment é criada
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
		// Cria a Lista do Menu Contexto
		registerForContextMenu(getListView());

		// Transforma o Home "Scrumming" em um botão
		ActionBar ab = ((ActionBarActivity) getActivity())
				.getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("Board");
		ab.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.bg_principal));

		txtMensagemTarefaStatus.setVisibility(View.GONE);
		progressTarefa.setVisibility(View.GONE);
		txtMensagemTarefa.setVisibility(View.GONE);

		if (listaTarefaProcesso != null) {
			progressTarefa.setVisibility(View.GONE);
			txtMensagemTarefa.setVisibility(View.GONE);
			AtualizarListaDeTarefa();

		}
	}

	/**
	 * Método utilizado no momento que a View é criada
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
		bloquear = true;
		View layout = inflater.inflate(R.layout.fragment_tarefa, container,
				false);

		progressTarefa = (ProgressBar) layout
				.findViewById(R.id.progressBarTarefa);
		txtMensagemTarefa = (TextView) layout
				.findViewById(R.id.txtMensagemTarefa);
		txtMensagemTarefaStatus = (TextView) layout
				.findViewById(R.id.txtMensagemTarefaStatus);

		// pega a sprint clicada no sprintFragment para listar os itensbacklog
		// da sprint
		itemBacklog = (ItemBacklog) getArguments().getSerializable(
				"itemBacklog");
		usuarioEmpresa = (UsuarioEmpresa) getArguments().getSerializable(
				"usuarioEmpresa");
		sprint = (Sprint) getArguments().getSerializable("sprint");

		return layout;
	}

	/**
	 * Método utilizado para exibir as opçoes de menu
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
	 * Método utilizado ao clicar em uma opção no menu
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

	@Override
	public void onPause() {
		AtualizarListaDeTarefa();
		super.onPause();
	}

	/**
	 * Método utilizado para atualizar a lista do fragment de Tarefas
	 * 
	 * @return void
	 */
	public void AtualizarListaDeTarefa() {
		TarefaAdapter adapter = new TarefaAdapter(getActivity(),
				listaTarefaProcesso);
		setListAdapter(adapter);
	}

	/**
	 * Método utilizado ao clicar em um item da lista do fragment
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
		if (getActivity() instanceof ClickedOnTarefaReporteItem) {
			((ClickedOnTarefaReporteItem) getActivity())
					.clicouNaTarefaReportItem(itemBacklog, usuarioEmpresa,
							sprint, listaTarefaProcesso.get(position)
									.getTarefa());
		}
	}

	/**
	 * Método utilizado para usar um menu de contexto
	 * 
	 * @param ContextMenu
	 *            menu
	 * @param View
	 *            v
	 * @param ContextMenuInfo
	 *            menuInfo
	 * @return void
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.menu_contexto_fragment_processo, menu);
	}

	/**
	 * Método utilizado ao selecionar uma item no menu de contexto
	 * 
	 * @param MenuItem
	 *            item
	 * @return boolean
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();

		tarefaSelecionada = (TarefaDTO) getListView().getItemAtPosition(
				info.position);

		switch (item.getItemId()) {
		case R.id.deProcessoParaPlanejada:

			new Thread(new Runnable() {
				public void run() {
					RestTarefa.salvarOuAtualizarTarefa(tarefaSelecionada
							.getTarefa().getCodigo(),
							SituacaoTarefaEnum.PARA_FAZER, usuarioEmpresa
									.getUsuario().getCodigo());
				}
			}).start();
			for (int i = 0; i < listaTarefaProcesso.size(); i++) {
				if (listaTarefaProcesso.get(i).getTarefa().getCodigo() == tarefaSelecionada
						.getTarefa().getCodigo()) {
					listaTarefaProcesso.remove(i);
				}
			}
			((MudarParaProcesso) getActivity())
					.clicouTarefaVoltarPlanejada(tarefaSelecionada);
			AtualizarListaDeTarefa();
			mensagemTarefaAlterada();
			break;

		case R.id.deProcessoParaConcluida:

			new Thread(new Runnable() {
				public void run() {
					RestTarefa.salvarOuAtualizarTarefa(tarefaSelecionada
							.getTarefa().getCodigo(), SituacaoTarefaEnum.FEITO,
							usuarioEmpresa.getUsuario().getCodigo());
				}
			}).start();
			for (int i = 0; i < listaTarefaProcesso.size(); i++) {
				if (listaTarefaProcesso.get(i).getTarefa().getCodigo() == tarefaSelecionada
						.getTarefa().getCodigo()) {
					listaTarefaProcesso.remove(i);
				}
			}
			((MudarParaProcesso) getActivity())
					.clicouTarefaIrConcluida(tarefaSelecionada);
			AtualizarListaDeTarefa();
			mensagemTarefaAlterada();
			break;

		case R.id.deProcessoParaImpedida:

			new Thread(new Runnable() {
				public void run() {
					RestTarefa.salvarOuAtualizarTarefa(tarefaSelecionada
							.getTarefa().getCodigo(),
							SituacaoTarefaEnum.EM_IMPEDIMENTO, usuarioEmpresa
									.getUsuario().getCodigo());
				}
			}).start();
			for (int i = 0; i < listaTarefaProcesso.size(); i++) {
				if (listaTarefaProcesso.get(i).getTarefa().getCodigo() == tarefaSelecionada
						.getTarefa().getCodigo()) {
					listaTarefaProcesso.remove(i);
				}
			}
			((MudarParaProcesso) getActivity())
					.clicouTarefaIrImpedida(tarefaSelecionada);
			AtualizarListaDeTarefa();
			mensagemTarefaAlterada();
			break;

		case R.id.opcaoAtribuir:
			if (tarefaSelecionada != null) {
				tarefaSelecionada.getTarefa().setUsuario(
						usuarioEmpresa.getUsuario());
				new Thread(new Runnable() {
					public void run() {
						RestTarefa.atribuirOuDesatribuirTarefa(
								tarefaSelecionada.getTarefa(), itemBacklog
										.getCodigo(), usuarioEmpresa
										.getUsuario().getCodigo());
					}
				}).start();
				AtualizarListaDeTarefa();
				mensagemTarefaAtribuida();
			}

			break;

		case R.id.opcaoFavoritar:

			tarefaFavorita = new TarefaFavorita();
			tarefaFavorita.setTarefa(tarefaSelecionada.getTarefa());
			tarefaFavorita.setUsuario(usuarioEmpresa.getUsuario());
			if (tarefaFavorita != null) {
				new Thread(new Runnable() {
					public void run() {
						RestTarefaFavorita.favoritarTarefa(tarefaFavorita);
					}
				}).start();
				AtualizarListaDeTarefa();
				mensagemTarefaFavoritada();
			}

			break;

		case R.id.opcaoReportarHoras:

			if (getActivity() instanceof ClickedOnTarefaReporteItem) {
				((ClickedOnTarefaReporteItem) getActivity())
						.clicouNaTarefaReportItem(itemBacklog, usuarioEmpresa,
								sprint, tarefaSelecionada.getTarefa());
			}
			break;

		}
		return super.onContextItemSelected(item);
	}

	private void mensagemTarefaAlterada() {

		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		// Setting Dialog Title
		alertDialog.setTitle("Info");

		// Setting Dialog Message
		alertDialog.setMessage("Tarefa Alterada para Planejada");

		// Setting Icon to Dialog
		alertDialog.setIcon(android.R.drawable.ic_dialog_info);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to execute after dialog closed
				// Toast.makeText(getActivity(),
				// "Operação Realizada com Sucesso", Toast.LENGTH_SHORT).show();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	private void mensagemTarefaFavoritada() {
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		// Setting Dialog Title
		alertDialog.setTitle("Info");

		// Setting Dialog Message
		alertDialog.setMessage("Tarefa Favoritada");

		// Setting Icon to Dialog
		alertDialog.setIcon(android.R.drawable.ic_dialog_info);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to execute after dialog closed
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	private void mensagemTarefaAtribuida() {
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		// Setting Dialog Title
		alertDialog.setTitle("Alert Dialog");

		// Setting Dialog Message
		alertDialog.setMessage("Tarefa Atribuida a "
				+ usuarioEmpresa.getUsuario().getNome());

		// Setting Icon to Dialog
		alertDialog.setIcon(android.R.drawable.ic_dialog_alert);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to execute after dialog closed
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
}
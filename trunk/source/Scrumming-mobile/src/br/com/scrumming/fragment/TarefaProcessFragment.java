package br.com.scrumming.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
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
import android.widget.Toast;
import br.com.scrumming.R;
import br.com.scrumming.adapter.TarefaAdapter;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;
import br.com.scrumming.domain.Tarefa;
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
	
	//Instanciação dos Objetos e variáveis
	List<Tarefa> listaTarefaProcesso;
	AsyncTaskTarefa taskTarefa;
	ItemBacklog itemBacklog;
	UsuarioEmpresa usuarioEmpresa;
	Integer sprintID, usuarioID;
	Sprint sprint;
	SprintBacklog sprintBacklog;
	ProgressBar progressTarefa;
	TextView txtMensagemTarefa, txtMensagemTarefaStatus;
	Tarefa tarefaSelecionada;
	TarefaFavorita tarefaFavorita;
	
	/**
	* Método que gera uma nova instancia do fragment de TarefaProcess
	* @param ItemBacklog itemBacklog
	* @param UsuarioEmpresa usuarioEmpresa
	* @param Sprint sprint
	* @return TarefaProcessFragment
	*/
	public static TarefaProcessFragment novaInstancia(ItemBacklog itemBacklog, UsuarioEmpresa usuarioEmpresa, Sprint sprint){
		Bundle args = new Bundle();
		args.putSerializable("itemBacklog", itemBacklog);
		args.putSerializable("usuarioEmpresa", usuarioEmpresa);
		args.putSerializable("sprint", sprint);
		TarefaProcessFragment tf = new TarefaProcessFragment();
		tf.setArguments(args);
		return tf;
	}
	
	public void atualizarLista(Tarefa tarefa){
		listaTarefaProcesso.add(tarefa);
		//AtualizarListaDeTarefa();
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
		//Cria a Lista do Menu Contexto
		registerForContextMenu(getListView());

		//Transforma o Home "Scrumming" em um botão
		ActionBar ab = ((ActionBarActivity)getActivity()).getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("Board");
		txtMensagemTarefaStatus.setVisibility(View.GONE);
		
		if (listaTarefaProcesso != null){
			progressTarefa.setVisibility(View.GONE);
			txtMensagemTarefa.setVisibility(View.GONE);
			AtualizarListaDeTarefa();

		} else {
			if (taskTarefa != null && taskTarefa.getStatus() == Status.RUNNING){
				mostrarProgress();

			} else {
				listaTarefaProcesso = new ArrayList<Tarefa>();
				iniciarDownload();
			}
		}
	}
	
	/**
	* Método utilizado para exibir uma imagem de Carregando enquanto os dados estiverem sendo baixados
	* @return void
	*/
	private void mostrarProgress() {
		progressTarefa.setVisibility(View.VISIBLE);
		txtMensagemTarefa.setVisibility(View.VISIBLE);
		txtMensagemTarefa.setText("Carregando...");
	}

	/**
	* Método utlilizado para realizar o download dos dados através de uma AsyncTask especifica
	* @return void
	*/
	private void iniciarDownload(){
		
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
			taskTarefa = new AsyncTaskTarefa();
			taskTarefa.execute(itemBacklog.getCodigo());

		} else {
			progressTarefa.setVisibility(View.GONE);
			txtMensagemTarefa.setVisibility(View.VISIBLE);
			txtMensagemTarefa.setText("Sem conexao com a Internet");
		}
	}
	
	/**
	* Método utilizado no momento que a View é criada
	* @param LayoutInflater inflater
	* @param ViewGroup container
	* @param Bundle savedInstanceState
	* @return View
	*/
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_tarefa, container, false);
		
		progressTarefa    = (ProgressBar)layout.findViewById(R.id.progressBarTarefa);
		txtMensagemTarefa = (TextView)layout.findViewById(R.id.txtMensagemTarefa);
		txtMensagemTarefaStatus = (TextView)layout.findViewById(R.id.txtMensagemTarefaStatus);
		
		//pega a sprint clicada no sprintFragment para listar os itensbacklog da sprint
		itemBacklog    = (ItemBacklog) getArguments().getSerializable("itemBacklog");
		usuarioEmpresa = (UsuarioEmpresa)getArguments().getSerializable("usuarioEmpresa");
		sprint		   = (Sprint)getArguments().getSerializable("sprint");
		
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
	* Método utilizado para atualizar a lista do fragment de Tarefas
	* @return void
	*/
	public void AtualizarListaDeTarefa() {
		TarefaAdapter adapter = new TarefaAdapter(getActivity(), listaTarefaProcesso);
		setListAdapter(adapter);
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
		if (getActivity() instanceof ClickedOnTarefaReporteItem) {
			((ClickedOnTarefaReporteItem)getActivity()).clicouNaTarefaReportItem(itemBacklog, usuarioEmpresa, 
														sprint, listaTarefaProcesso.get(position));
		}
	}
	
	/**
	* Método utilizado para usar um menu de contexto
	* @param ContextMenu menu
	* @param View v
	* @param ContextMenuInfo menuInfo
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
	* @param MenuItem item
	* @return boolean
	*/
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		
		tarefaSelecionada = (Tarefa)getListView().getItemAtPosition(info.position);
		
		switch (item.getItemId()) {
		case R.id.opcaoAlterarPlanejada:
			 
			 new Thread(new Runnable() {
			        public void run() {
			        	RestTarefa.salvarOuAtualizarTarefa(tarefaSelecionada.getCodigo(), 
										        			SituacaoTarefaEnum.PARA_FAZER, 
										        			usuarioEmpresa.getUsuario().getCodigo());
			        }
			    }).start();
			 for (int i = 0; i < listaTarefaProcesso.size(); i++) {
				if (listaTarefaProcesso.get(i).getCodigo() == tarefaSelecionada.getCodigo()) {
					listaTarefaProcesso.remove(i);
				}
			}
			 ((MudarParaProcesso)getActivity()).clicouTarefaPlanejada(tarefaSelecionada);
			 AtualizarListaDeTarefa();
			 mensagemTarefaAlterada();
			break;
			
		case R.id.opcaoAtribuir:
			if (tarefaSelecionada != null) {
				tarefaSelecionada.setUsuario(usuarioEmpresa.getUsuario());
				new Thread(new Runnable() {
					public void run() {
						RestTarefa.atribuirOuDesatribuirTarefa(tarefaSelecionada, 
								itemBacklog.getCodigo(), 
								usuarioEmpresa.getUsuario().getCodigo());
					}
				}).start();
				AtualizarListaDeTarefa();
				mensagemTarefaAtribuida();
			}
			
			break;
			
		case R.id.opcaoFavoritar:
			
			tarefaFavorita = new TarefaFavorita();
			tarefaFavorita.setTarefa(tarefaSelecionada);
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
				((ClickedOnTarefaReporteItem)getActivity()).clicouNaTarefaReportItem(itemBacklog, usuarioEmpresa, 
															sprint, tarefaSelecionada);
			}
			break;
			
		}
		return super.onContextItemSelected(item);
	}
	
		private void mensagemTarefaAlterada() {
			
			AlertDialog alertDialog = new AlertDialog.Builder(
					getActivity()).create();

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
					//Toast.makeText(getActivity(), "Operação Realizada com Sucesso", Toast.LENGTH_SHORT).show();
				}
			});

			// Showing Alert Message
			alertDialog.show();
	}

		private void mensagemTarefaFavoritada(){
			AlertDialog alertDialog = new AlertDialog.Builder(
					getActivity()).create();
	
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
	
		private void mensagemTarefaAtribuida(){
			AlertDialog alertDialog = new AlertDialog.Builder(
					getActivity()).create();
	
			// Setting Dialog Title
			alertDialog.setTitle("Alert Dialog");
	
			// Setting Dialog Message
			alertDialog.setMessage("Tarefa Atribuida a " + usuarioEmpresa.getUsuario().getNome());
	
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
	
		
	//InnerClass do AsyncTask da Empresa
	class AsyncTaskTarefa extends AsyncTask<Integer, Void, List<Tarefa>>{

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
		* @param Integer... params
		* @return Lista de Tarefas
		*/
		@Override
		protected List<Tarefa> doInBackground(Integer... params) {
			return RestTarefa.retornarTarefa(params[0]);
		}
		
		/**
		* Método proviniente da herança do AsyncTask para executar algo depois do DoInBackground 
		* @param Lista de Tarefas
		* @return void
		*/
		@Override
		protected void onPostExecute(List<Tarefa> tarefas) {
			super.onPostExecute(tarefas);
			if(tarefas != null) {
				for (int i = 0; i < tarefas.size(); i++) {
					if (tarefas.get(i).getSituacao() == SituacaoTarefaEnum.FAZENDO) {
						listaTarefaProcesso.add(tarefas.get(i));
					}
				}
				AtualizarListaDeTarefa();
				txtMensagemTarefa.setVisibility(View.GONE);
			}else {
				txtMensagemTarefa.setText("Não Existem Tarefas em Processo Cadastradas");
			}
			progressTarefa.setVisibility(View.GONE);
		}
	}
}
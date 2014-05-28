package br.com.scrumming.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.adapter.TarefaAdapter;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;
import br.com.scrumming.domain.TarefaDTO;
import br.com.scrumming.domain.TarefaReporte;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.interfaces.MudarParaProcesso;
import br.com.scrumming.rest.RestTarefa;

public class TarefaConcluidaFragment extends ListFragment {
	
	//Instanciação dos Objetos e variáveis
	List<TarefaDTO> listaTarefaConcluida;
	TarefaDTO tarefaSelecionada;
	AsyncTaskTarefa taskTarefa;
	ItemBacklog itemBacklog;
	UsuarioEmpresa usuarioEmpresa;
	Integer sprintID, usuarioID;
	Sprint sprint;
	SprintBacklog sprintBacklog;
	ProgressBar progressTarefa;
	TextView txtMensagemTarefa, txtMensagemTarefaStatus;
	
	/**
	* Método que gera uma nova instancia do fragment de TarefaConcluida
	* @param ItemBacklog itemBacklog
	* @param UsuarioEmpresa usuarioEmpresa
	* @param Sprint sprint
	* @return TarefaConcluidaFragment
	*/
	public static TarefaConcluidaFragment novaInstancia(ItemBacklog itemBacklog, UsuarioEmpresa usuarioEmpresa, Sprint sprint){
		Bundle args = new Bundle();
		args.putSerializable("itemBacklog", itemBacklog);
		args.putSerializable("usuarioEmpresa", usuarioEmpresa);
		args.putSerializable("sprint", sprint);
		TarefaConcluidaFragment tf = new TarefaConcluidaFragment();
		tf.setArguments(args);
		return tf;
	}
	
	public void atualizarLista(TarefaDTO tarefa){
		tarefa.getTarefa().setTempoEstimado(0);
		listaTarefaConcluida.add(tarefa);
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
		
		//Transforma o Home "Scrumming" em um botão
		ActionBar ab = ((ActionBarActivity)getActivity()).getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("SprintBacklog");
		
		txtMensagemTarefaStatus.setVisibility(View.GONE);
		
		if (listaTarefaConcluida != null){
			progressTarefa.setVisibility(View.GONE);
			txtMensagemTarefa.setVisibility(View.GONE);
			AtualizarListaDeTarefa();;

		} else {
			if (taskTarefa != null && taskTarefa.getStatus() == Status.RUNNING){
				mostrarProgress();

			} else {
				listaTarefaConcluida = new ArrayList<TarefaDTO>();
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
			getActivity().finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	* Método utilizado para atualizar a lista do fragment de Tarefas
	* @return void
	*/
	private void AtualizarListaDeTarefa() {
		for (int i = 0; i < listaTarefaConcluida.size(); i++) {
			listaTarefaConcluida.get(i).getTarefa().setTempoEstimado(0);
		}
		TarefaAdapter adapter = new TarefaAdapter(getActivity(), listaTarefaConcluida);
		setListAdapter(adapter);
	}
	
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
		
		tarefaSelecionada = (TarefaDTO)getListView().getItemAtPosition(info.position);
		
		switch (item.getItemId()) {
		case R.id.deConcluidaParaProcesso:
			 
			 new Thread(new Runnable() {
			        public void run() {
			        	RestTarefa.salvarOuAtualizarTarefa(tarefaSelecionada.getTarefa().getCodigo(), 
										        			SituacaoTarefaEnum.FAZENDO, 
										        			usuarioEmpresa.getUsuario().getCodigo());
			        }
			    }).start();
			 for (int i = 0; i < listaTarefaConcluida.size(); i++) {
				if (listaTarefaConcluida.get(i).getTarefa().getCodigo() == tarefaSelecionada.getTarefa().getCodigo()) {
					listaTarefaConcluida.remove(i);
				}
			}
			 ((MudarParaProcesso)getActivity()).clicouTarefaVoltarProcesso(tarefaSelecionada);
			 AtualizarListaDeTarefa();
			// mensagemTarefaAlterada();
			break;
		}
		return super.onContextItemSelected(item);
	}

	
	//InnerClass do AsyncTask da Empresa
	class AsyncTaskTarefa extends AsyncTask<Integer, Void, List<TarefaDTO>>{

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
		protected List<TarefaDTO> doInBackground(Integer... params) {
			return RestTarefa.retornarTarefa(params[0]);
		}
		
		/**
		* Método proviniente da herança do AsyncTask para executar algo depois do DoInBackground 
		* @param Lista de Tarefas
		* @return void
		*/
		@Override
		protected void onPostExecute(List<TarefaDTO> tarefas) {
			super.onPostExecute(tarefas);
			if(tarefas != null) {
				for (int i = 0; i < tarefas.size(); i++) {
					if (tarefas.get(i).getTarefa().getSituacao() == SituacaoTarefaEnum.FEITO) {
						listaTarefaConcluida.add(tarefas.get(i));
						
					}/*else{
						txtMensagemTarefaStatus.setVisibility(View.VISIBLE);
						txtMensagemTarefaStatus.setText("Não há tarefa planejada para esse item");
					}*/
				}
				
				AtualizarListaDeTarefa();
				txtMensagemTarefa.setVisibility(View.GONE);
			}else {
				txtMensagemTarefa.setText("Não Existem Tarefas Concluidas Cadastradas");
			}
			progressTarefa.setVisibility(View.GONE);
		}
	}
}
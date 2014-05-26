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
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.TarefaFavorita;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.interfaces.ClickedOnTarefaReporteItem;
import br.com.scrumming.rest.RestTarefa;
import br.com.scrumming.rest.RestTarefaFavorita;
import br.com.scrumming.rest.RestTarefaReport;

public class TarefaPlanejadaFragment extends ListFragment {
	
	List<Tarefa> listaTarefa;
	List<Tarefa> listaTarefasPlanejadas;
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
	
	public static TarefaPlanejadaFragment novaInstancia(ItemBacklog itemBacklog, UsuarioEmpresa usuarioEmpresa, Sprint sprint){
		Bundle args = new Bundle();
		args.putSerializable("itemBacklog", itemBacklog);
		args.putSerializable("usuarioEmpresa", usuarioEmpresa);
		args.putSerializable("sprint", sprint);
		TarefaPlanejadaFragment tf = new TarefaPlanejadaFragment();
		tf.setArguments(args);
		return tf;
	}
	
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
		ab.setTitle("SprintBacklog");
		txtMensagemTarefaStatus.setVisibility(View.GONE);
		
		if (listaTarefa != null){
			progressTarefa.setVisibility(View.GONE);
			txtMensagemTarefa.setVisibility(View.GONE);
			AtualizarListaDeTarefa();;

		} else {
			if (taskTarefa != null && taskTarefa.getStatus() == Status.RUNNING){
				mostrarProgress();

			} else {
				listaTarefa =   new ArrayList<Tarefa>();
				iniciarDownload();
				
			}
		}
		
	}
	
	private void mostrarProgress() {
		progressTarefa.setVisibility(View.VISIBLE);
		txtMensagemTarefa.setVisibility(View.VISIBLE);
		txtMensagemTarefa.setText("Carregando...");
	}

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
	
	private void AtualizarListaDeTarefa() {
		TarefaAdapter adapter = new TarefaAdapter(getActivity(), listaTarefa);
		setListAdapter(adapter);
		
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.menu_contexto, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		
		tarefaSelecionada = (Tarefa)getListView().getItemAtPosition(info.position);
		
		switch (item.getItemId()) {
		case R.id.opcaoAlterarProcessando:
			 
			 new Thread(new Runnable() {
			        public void run() {
			        	RestTarefa.salvarOuAtualizarTarefa(tarefaSelecionada.getCodigo(), 
										        			SituacaoTarefaEnum.FAZENDO, 
										        			usuarioEmpresa.getUsuario().getCodigo());
			        }
			    }).start();
			 for (int i = 0; i < listaTarefa.size(); i++) {
				if (listaTarefa.get(i).getCodigo() == tarefaSelecionada.getCodigo()) {
					listaTarefa.remove(i);
				}
				AtualizarListaDeTarefa();
			}
			 
		case R.id.opcaoAtribuir:

			tarefaSelecionada.setUsuario(usuarioEmpresa.getUsuario());
			new Thread(new Runnable() {
				public void run() {
					RestTarefa.atribuirOuDesatribuirTarefa(tarefaSelecionada, 
							itemBacklog.getCodigo(), 
							usuarioEmpresa.getUsuario().getCodigo());
				}
			}).start();
			AtualizarListaDeTarefa();
			break;
			
		case R.id.opcaoFavoritar:
			tarefaFavorita = new TarefaFavorita();
			tarefaFavorita.setTarefa(tarefaSelecionada);
			tarefaFavorita.setUsuario(usuarioEmpresa.getUsuario());
			new Thread(new Runnable() {
				public void run() {
					RestTarefaFavorita.favoritarTarefa(tarefaFavorita);
				}
			}).start();
			AtualizarListaDeTarefa();
			break;
		}
		
		return super.onContextItemSelected(item);
	}
	
	class AsyncTaskTarefa extends AsyncTask<Integer, Void, List<Tarefa>>{

		@Override
		protected void onPreExecute() {
			mostrarProgress();
		}
		
		@Override
		protected List<Tarefa> doInBackground(Integer... params) {
			return RestTarefa.retornarTarefa(params[0]);
		}
		
		@Override
		protected void onPostExecute(List<Tarefa> tarefas) {
			super.onPostExecute(tarefas);
			if(tarefas != null) {
				for (int i = 0; i < tarefas.size(); i++) {
					if (tarefas.get(i).getSituacao() == SituacaoTarefaEnum.PARA_FAZER) {
						listaTarefa.add(tarefas.get(i));
						
					}/*else{
						txtMensagemTarefaStatus.setVisibility(View.VISIBLE);
						txtMensagemTarefaStatus.setText("Não há tarefa planejada para esse item");
					}*/
				}
				
				AtualizarListaDeTarefa();
				txtMensagemTarefa.setVisibility(View.GONE);
			}else {
				txtMensagemTarefa.setText("Não Existe Projetos Cadastrados");
			}
			progressTarefa.setVisibility(View.GONE);
		}

	}

}

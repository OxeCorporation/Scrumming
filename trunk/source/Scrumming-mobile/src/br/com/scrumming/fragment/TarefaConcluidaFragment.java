package br.com.scrumming.fragment;

import java.util.ArrayList;
import java.util.List;

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
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.interfaces.MudarParaProcesso;
import br.com.scrumming.rest.RestTarefa;

public class TarefaConcluidaFragment extends ListFragment {
	
	//Instanciação dos Objetos e variáveis
	List<TarefaDTO> listaTarefaConcluida;
	TarefaDTO tarefaSelecionada;
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

	public void receberListaTarafaDTO(List<TarefaDTO> listaTarafaDTO){
		listaTarefaConcluida = new ArrayList<TarefaDTO>();
		for (int i = 0; i < listaTarafaDTO.size(); i++) {
			if (listaTarafaDTO.get(i).getTarefa().getSituacao() == SituacaoTarefaEnum.FEITO) {
				listaTarefaConcluida.add(listaTarafaDTO.get(i));
				
			}
		}

		AtualizarListaDeTarefa();
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
		ab.setTitle("Board");
		ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_principal));
		progressTarefa.setVisibility(View.GONE);
		txtMensagemTarefa.setVisibility(View.GONE);
		txtMensagemTarefaStatus.setVisibility(View.GONE);
		
		if (listaTarefaConcluida != null){
			progressTarefa.setVisibility(View.GONE);
			txtMensagemTarefa.setVisibility(View.GONE);
			AtualizarListaDeTarefa();;

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
			break;
		}
		return super.onContextItemSelected(item);
	}
}
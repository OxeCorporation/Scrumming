package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.TarefaDTO;
import br.com.scrumming.domain.TarefaReporte;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.TarefaConcluidaFragment;
import br.com.scrumming.fragment.TarefaImpedimentoFragment;
import br.com.scrumming.fragment.TarefaPlanejadaFragment;
import br.com.scrumming.fragment.TarefaProcessFragment;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnHomeBoard;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.interfaces.ClickedOnTarefa;
import br.com.scrumming.interfaces.ClickedOnTarefaReporteItem;
import br.com.scrumming.interfaces.MudarParaProcesso;

public class TarefaActivity extends ActionBarActivity implements
		ClickedOnLogout, ClickedOnHomeBoard, TabListener, ClickedOnTarefa,
		ClickedOnTarefaReporteItem, MudarParaProcesso, ClickedOnHome {

	// Instanciação dos Objetos e variáveis
	TarefaPlanejadaFragment tarefaPlanejadaFragment;
	TarefaProcessFragment tarefaProcessFragment;
	TarefaImpedimentoFragment tarefaImpedimentoFragment;
	TarefaConcluidaFragment tarefaConcluidaFragment;

	/**
	 * Método de criação da Activity
	 * 
	 * @param Bundle
	 *            savedInstanceState
	 * @return void
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);

		ItemBacklog itemBacklog = (ItemBacklog) getIntent()
				.getSerializableExtra("itemBacklog");
		UsuarioEmpresa usuarioEmpresa = (UsuarioEmpresa) getIntent()
				.getSerializableExtra("usuarioEmpresa");
		Sprint sprint = (Sprint) getIntent().getSerializableExtra("sprint");
		// Tarefa tarefa =
		// (Tarefa)getIntent().getSerializableExtra("tarefaSelecionada");

		tarefaPlanejadaFragment = (TarefaPlanejadaFragment) getSupportFragmentManager()
				.findFragmentByTag("tpf");
		tarefaProcessFragment = (TarefaProcessFragment) getSupportFragmentManager()
				.findFragmentByTag("tpcf");
		tarefaImpedimentoFragment = (TarefaImpedimentoFragment) getSupportFragmentManager()
				.findFragmentByTag("tif");
		tarefaConcluidaFragment = (TarefaConcluidaFragment) getSupportFragmentManager()
				.findFragmentByTag("tcf");

		if ((tarefaPlanejadaFragment == null)
				|| (tarefaProcessFragment == null)
				|| (tarefaImpedimentoFragment == null)
				|| (tarefaConcluidaFragment == null)) {

			tarefaPlanejadaFragment = TarefaPlanejadaFragment.novaInstancia(
					itemBacklog, usuarioEmpresa, sprint);
			tarefaProcessFragment = TarefaProcessFragment.novaInstancia(
					itemBacklog, usuarioEmpresa, sprint);
			tarefaImpedimentoFragment = TarefaImpedimentoFragment
					.novaInstancia(itemBacklog, usuarioEmpresa, sprint);
			tarefaConcluidaFragment = TarefaConcluidaFragment.novaInstancia(
					itemBacklog, usuarioEmpresa, sprint);

			getSupportFragmentManager().beginTransaction()
					.add(R.id.master, tarefaPlanejadaFragment, "tpf")
					.add(R.id.master, tarefaProcessFragment, "tpcf")
					.add(R.id.master, tarefaImpedimentoFragment, "tif")
					.add(R.id.master, tarefaConcluidaFragment, "tcf").commit();
		}
		// } else if (tarefaProcessFragment != null && tarefa != null) {
		// tarefaProcessFragment.modificarLista(tarefa);
		//
		// }

		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab tab1 = actionBar.newTab();
		tab1.setText("Planejada");
		tab1.setTabListener((TabListener) this);

		Tab tab2 = actionBar.newTab();
		tab2.setText("Processo");
		tab2.setTabListener((TabListener) this);

		Tab tab3 = actionBar.newTab();
		tab3.setText("Impedida");
		tab3.setTabListener((TabListener) this);

		Tab tab4 = actionBar.newTab();
		tab4.setText("Concluida");
		tab4.setTabListener((TabListener) this);

		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
		actionBar.addTab(tab3);
		actionBar.addTab(tab4);

		if (savedInstanceState != null) {
			actionBar.setSelectedNavigationItem(savedInstanceState
					.getInt("tab"));
		}
	}

	/**
	 * Método herdado da interface TabListener usado ao reselecionar uma aba
	 * 
	 * @param Tab
	 *            arg0
	 * @param FragmentTransaction
	 *            arg1
	 * @return void
	 */
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
	}

	/**
	 * Método herdado da interface TabListener usado ao selecionar uma aba
	 * 
	 * @param Tab
	 *            tab
	 * @param FragmentTransaction
	 *            ft
	 * @return void
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.getPosition() == 0) {
			ft.show(tarefaPlanejadaFragment).hide(tarefaProcessFragment)
					.hide(tarefaImpedimentoFragment)
					.hide(tarefaConcluidaFragment);
		} else if (tab.getPosition() == 1) {
			ft.show(tarefaProcessFragment).hide(tarefaPlanejadaFragment)
					.hide(tarefaImpedimentoFragment)
					.hide(tarefaConcluidaFragment);
		} else if (tab.getPosition() == 2) {
			ft.show(tarefaImpedimentoFragment).hide(tarefaProcessFragment)
					.hide(tarefaPlanejadaFragment)
					.hide(tarefaConcluidaFragment);
		} else if (tab.getPosition() == 3) {
			ft.show(tarefaConcluidaFragment).hide(tarefaProcessFragment)
					.hide(tarefaImpedimentoFragment)
					.hide(tarefaPlanejadaFragment);
		}
	}

	/**
	 * Método herdado da interface TabListener usado ao deselecionar uma aba
	 * 
	 * @param Tab
	 *            arg0
	 * @param FragmentTransaction
	 *            arg1
	 * @return void
	 */
	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
	}

	/**
	 * Método usado para salvar o estado da instancia da activity
	 * 
	 * @param Bundle
	 *            outState
	 * @return void
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getSupportActionBar()
				.getSelectedNavigationIndex());
	}

	/**
	 * Método proviniente da interface para exibir a activity responsável pelo
	 * reporte de horas da tarefa
	 * 
	 * @param ItemBacklog
	 *            itemBacklog
	 * @param UsuarioEmpresa
	 *            usuarioEmpresa
	 * @param Sprint
	 *            sprint
	 * @return void
	 */
	@Override
	public void clicouNaTarefa(ItemBacklog itemBacklog,
			UsuarioEmpresa usuarioEmpresa, Sprint sprint) {
		Intent intentTarefa = new Intent(this, TarefaReportActivity.class);
		intentTarefa.putExtra("itemBacklog", itemBacklog);
		intentTarefa.putExtra("usuarioEmpresa", usuarioEmpresa);
		intentTarefa.putExtra("sprint", sprint);
		startActivity(intentTarefa);
	}

	/**
	 * Método para aplicar logout e voltar para a tela de login
	 * 
	 * @param UsuarioEmpresa
	 *            usuarioEmpresa
	 * @return void
	 */
	@Override
	public void clicouNoLogout(UsuarioEmpresa usuarioEmpresa) {
		Intent intencao = new Intent(this, LoginActivity.class);
		intencao.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intencao);
	}

	/**
	 * Método para encerrar a activity de Tarefa
	 * 
	 * @param UsuarioEmpresa
	 *            usuarioEmpresa
	 * @return void
	 */
	@Override
	public void clicouNoHomeBoard(Sprint sprint, UsuarioEmpresa usuarioEmpresa) {
		Intent intencaoSprintBacklog = new Intent(this,
				SprintBacklogActivity.class);
		intencaoSprintBacklog.putExtra("usuarioEmpresa", usuarioEmpresa);
		intencaoSprintBacklog.putExtra("sprint", sprint);
		startActivity(intencaoSprintBacklog);
	}

	/**
	 * Método proviniente da interface para exibir a activity responsável pelo
	 * reporte de horas da tarefa
	 * 
	 * @param ItemBacklog
	 *            itemBacklog
	 * @param UsuarioEmpresa
	 *            usuarioEmpresa
	 * @param Sprint
	 *            sprint
	 * @param Tarefa
	 *            tarefa
	 * @return void
	 */
	@Override
	public void clicouNaTarefaReportItem(ItemBacklog itemBacklog,
			UsuarioEmpresa usuarioEmpresa, Sprint sprint, Tarefa tarefa) {
		Intent intentTarefa = new Intent(this, TarefaReportActivity.class);
		intentTarefa.putExtra("itemBacklog", itemBacklog);
		intentTarefa.putExtra("usuarioEmpresa", usuarioEmpresa);
		intentTarefa.putExtra("sprint", sprint);
		intentTarefa.putExtra("tarefa", tarefa);
		startActivity(intentTarefa);
	}

	@Override
	public void clicouTarefaIrProcesso(TarefaDTO tarefa) {
		// TODO Auto-generated method stub
		if (tarefaProcessFragment != null) {
			tarefaProcessFragment.atualizarLista(tarefa);
		}

	}

	@Override
	public void clicouTarefaVoltarPlanejada(TarefaDTO tarefa) {
		// TODO Auto-generated method stub
		if (tarefaPlanejadaFragment != null) {
			tarefaPlanejadaFragment.alterarLista(tarefa);
		}
	}

	@Override
	public void clicouTarefaVoltarProcesso(TarefaDTO tarefa) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clicouTarefaIrImpedida(TarefaDTO tarefa) {
		if (tarefaImpedimentoFragment != null) {
			tarefaImpedimentoFragment.atualizarLista(tarefa);
		}

	}

	@Override
	public void clicouTarefaIrConcluida(TarefaDTO tarefa) {
		if (tarefaConcluidaFragment != null) {
			tarefaConcluidaFragment.atualizarLista(tarefa);
		}

	}

	@Override
	public void clicouNoHome(UsuarioEmpresa usuarioEmpresa) {
		// TODO Auto-generated method stub
		finish();
	}

}
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
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.TarefaConcluidaFragment;
import br.com.scrumming.fragment.TarefaImpedimentoFragment;
import br.com.scrumming.fragment.TarefaPlanejadaFragment;
import br.com.scrumming.fragment.TarefaProcessFragment;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.interfaces.ClickedOnTarefa;

public class TarefaActivity extends ActionBarActivity implements ClickedOnLogout, ClickedOnHome, TabListener, ClickedOnTarefa{
	
	TarefaPlanejadaFragment tarefaPlanejadaFragment;
	TarefaProcessFragment tarefaProcessFragment;
	TarefaImpedimentoFragment tarefaImpedimentoFragment;
	TarefaConcluidaFragment tarefaConcluidaFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		ItemBacklog itemBacklog = (ItemBacklog)getIntent().getSerializableExtra("itemBacklog");
		UsuarioEmpresa usuarioEmpresa = (UsuarioEmpresa)getIntent().getSerializableExtra("usuarioEmpresa");
		Sprint sprint = (Sprint)getIntent().getSerializableExtra("sprint");
		
		tarefaPlanejadaFragment = (TarefaPlanejadaFragment)getSupportFragmentManager().findFragmentByTag("tpf");
		tarefaProcessFragment   = (TarefaProcessFragment)getSupportFragmentManager().findFragmentByTag("tpcf");
		tarefaImpedimentoFragment   = (TarefaImpedimentoFragment)getSupportFragmentManager().findFragmentByTag("tif");
		tarefaConcluidaFragment 	= (TarefaConcluidaFragment)getSupportFragmentManager().findFragmentByTag("tcf");
		
		if ((tarefaPlanejadaFragment == null) || (tarefaProcessFragment  == null) ||
						(tarefaImpedimentoFragment  == null) || (tarefaConcluidaFragment  == null)){
			
			tarefaPlanejadaFragment = TarefaPlanejadaFragment.novaInstancia(itemBacklog, usuarioEmpresa, sprint);
			tarefaProcessFragment = TarefaProcessFragment.novaInstancia(itemBacklog, usuarioEmpresa, sprint);
			tarefaImpedimentoFragment = TarefaImpedimentoFragment.novaInstancia(itemBacklog, usuarioEmpresa, sprint);
			tarefaConcluidaFragment = TarefaConcluidaFragment.novaInstancia(itemBacklog, usuarioEmpresa, sprint);
			
			getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.master, tarefaPlanejadaFragment, "tpf")
				.add(R.id.master, tarefaProcessFragment, "tpcf")
				.add(R.id.master, tarefaImpedimentoFragment, "tif")
				.add(R.id.master, tarefaConcluidaFragment, "tcf")
				.commit();
		}
		 
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
			actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab"));
		}
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.getPosition() == 0){
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
		}else if (tab.getPosition() == 3) {
			ft.show(tarefaConcluidaFragment).hide(tarefaProcessFragment)
											.hide(tarefaImpedimentoFragment)
											.hide(tarefaPlanejadaFragment);
		}
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getSupportActionBar().getSelectedNavigationIndex());
		
	}
	
	@Override
	public void clicouNaTarefa(ItemBacklog itemBacklog, UsuarioEmpresa usuarioEmpresa, Sprint sprint) {
		Intent intentTarefa = new Intent(this, TarefaReportActivity.class);
		intentTarefa.putExtra("itemBacklog", itemBacklog);
		intentTarefa.putExtra("usuarioEmpresa", usuarioEmpresa);
		intentTarefa.putExtra("sprint", sprint);
		startActivity(intentTarefa);
		
	}
	
	@Override
	public void clicouNoLogout(UsuarioEmpresa usuarioEmpresa) {
		Intent intencao = new Intent(this, LoginActivity.class);
		intencao.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intencao);
		
	}

	@Override
	public void clicouNoHome(UsuarioEmpresa usuarioEmpresa) {
		finish();
	}

		
}
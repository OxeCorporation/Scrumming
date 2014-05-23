package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.SprintBacklogFragment;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnItemBacklog;
import br.com.scrumming.interfaces.ClickedOnLogout;

public class SprintBacklogActivity extends ActionBarActivity implements ClickedOnItemBacklog, ClickedOnLogout, ClickedOnHome{

	SprintBacklogFragment sprinBacklogFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		Sprint sprint = (Sprint)getIntent().getSerializableExtra("sprint");
		UsuarioEmpresa usuarioEmpresa = (UsuarioEmpresa)getIntent().getSerializableExtra("usuarioEmpresa");
		
		sprinBacklogFragment = (SprintBacklogFragment)getSupportFragmentManager().findFragmentByTag("sbf");
		if (sprinBacklogFragment == null) {
			sprinBacklogFragment = SprintBacklogFragment.novaInstancia(sprint, usuarioEmpresa);
			
			getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.master, sprinBacklogFragment, "sbf")
				.commit();
		}
					
	}
	
	@Override
	public void itemBacklogFoiClicada(ItemBacklog itemBacklog, UsuarioEmpresa usuarioEmpresa, Sprint sprint) {
		Intent it4 = new Intent(this, TarefaActivity.class);
		it4.putExtra("itemBacklog", itemBacklog);
		it4.putExtra("usuarioEmpresa", usuarioEmpresa);
		it4.putExtra("sprint", sprint);
		startActivity(it4);
		
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

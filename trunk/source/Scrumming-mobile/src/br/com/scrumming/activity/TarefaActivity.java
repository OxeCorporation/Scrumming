package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.TarefaFragment;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;

public class TarefaActivity extends ActionBarActivity implements ClickedOnLogout, ClickedOnHome{
	
	TarefaFragment tarefaFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		ItemBacklog itemBacklog = (ItemBacklog)getIntent().getSerializableExtra("itemBacklog");
		UsuarioEmpresa usuarioEmpresa = (UsuarioEmpresa)getIntent().getSerializableExtra("usuarioEmpresa");
		Sprint sprint = (Sprint)getIntent().getSerializableExtra("sprint");
		
		tarefaFragment = (TarefaFragment)getSupportFragmentManager().findFragmentByTag("tf");
		if (tarefaFragment == null) {
			tarefaFragment = TarefaFragment.novaInstancia(itemBacklog, usuarioEmpresa, sprint);
			
			getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.master, tarefaFragment, "tf")
				.commit();
		}
					
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
package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.SprintBacklogFragment;
import br.com.scrumming.interfaces.ClickedOnItemBacklog;

public class SprintBacklogActivity extends FragmentActivity implements ClickedOnItemBacklog{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		Sprint sprint = (Sprint)getIntent().getSerializableExtra("sprint");
		UsuarioEmpresa usuarioEmpresa = (UsuarioEmpresa)getIntent().getSerializableExtra("usuarioEmpresa");
		SprintBacklogFragment sbf = SprintBacklogFragment.novaInstancia(sprint, usuarioEmpresa);
		
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.master, sbf)
			.commit();			
	}
	
	@Override
	public void itemBacklogFoiClicada(ItemBacklog itemBacklog, UsuarioEmpresa usuarioEmpresa) {
		Intent it4 = new Intent(this, TarefaActivity.class);
		it4.putExtra("itemBacklog", itemBacklog);
		it4.putExtra("usuarioEmpresa", usuarioEmpresa);
		startActivity(it4);
		
	}


}

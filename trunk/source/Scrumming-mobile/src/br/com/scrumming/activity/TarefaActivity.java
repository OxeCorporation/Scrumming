package br.com.scrumming.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.SprintBacklogFragment;
import br.com.scrumming.fragment.TarefaFragment;

public class TarefaActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		ItemBacklog itemBacklog = (ItemBacklog)getIntent().getSerializableExtra("itemBacklog");
		//UsuarioEmpresa usuarioEmpresa = (UsuarioEmpresa)getIntent().getSerializableExtra("usuarioEmpresa");
		TarefaFragment sbf = TarefaFragment.novaInstancia(itemBacklog);
		
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.master, sbf)
			.commit();			
	}
}

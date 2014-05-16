package br.com.scrumming.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.SprintBacklogFragment;

public class SprintBacklogActivity extends FragmentActivity{

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

	
	/**@Override
	 * public void sprintFoiClicada(Sprint sprint) {
		Intent it3 = new Intent(this, SprintBacklogActivity.class);
		it3.putExtra("sprint", sprint);
		startActivity(it3);
		
	}*/
}

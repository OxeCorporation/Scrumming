package br.com.scrumming.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.ProjetoFragment;

public class ProjetoActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		UsuarioEmpresa usuarioEmpresa = (UsuarioEmpresa)getIntent().getSerializableExtra("usuarioEmpresa");
		ProjetoFragment pf = ProjetoFragment.novaInstancia(usuarioEmpresa);
		
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.master, pf)
			.commit();			
	}
}

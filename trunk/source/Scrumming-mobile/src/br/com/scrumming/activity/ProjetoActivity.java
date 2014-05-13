package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.ProjetoFragment;
import br.com.scrumming.interfaces.ClickedOnProjeto;

public class ProjetoActivity extends FragmentActivity implements ClickedOnProjeto{

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

	@Override
	public void projetoFoiClicado(Projeto projeto) {
		Intent it2 = new Intent(this, SprintActivity.class);
		it2.putExtra("projeto", projeto);
		startActivity(it2);
		
	}
}

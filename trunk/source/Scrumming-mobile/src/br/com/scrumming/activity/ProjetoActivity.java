package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.ProjetoFragment;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.interfaces.ClickedOnProjeto;

public class ProjetoActivity extends ActionBarActivity implements ClickedOnProjeto, ClickedOnLogout, ClickedOnHome{

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
	public void projetoFoiClicado(Projeto projeto, UsuarioEmpresa usuarioEmpresa) {
		Intent it2 = new Intent(this, SprintActivity.class);
		it2.putExtra("projeto", projeto);
		it2.putExtra("usuarioEmpresa", usuarioEmpresa);
		startActivity(it2);
		
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

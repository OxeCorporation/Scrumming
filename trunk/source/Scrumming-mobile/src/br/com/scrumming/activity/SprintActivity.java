package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.SprintFragment;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.interfaces.ClickedOnSprint;

public class SprintActivity extends ActionBarActivity implements ClickedOnSprint, ClickedOnLogout, ClickedOnHome{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		Projeto projeto = (Projeto)getIntent().getSerializableExtra("projeto");
		UsuarioEmpresa usuarioEmpresa = (UsuarioEmpresa)getIntent().getSerializableExtra("usuarioEmpresa");
		SprintFragment sprintfragment = SprintFragment.novaInstancia(projeto, usuarioEmpresa);
		
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.master, sprintfragment)
			.commit();			
	}
	
	@Override
	public void sprintFoiClicada(Sprint sprint, UsuarioEmpresa usuarioEmpresa) {
		Intent it3 = new Intent(this, SprintBacklogActivity.class);
		it3.putExtra("sprint", sprint);
		it3.putExtra("usuarioEmpresa", usuarioEmpresa);
		startActivity(it3);
	}

	@Override
	public void clicouNoLogout(UsuarioEmpresa usuarioEmpresa) {
		Intent intencao = new Intent(this, LoginActivity.class);
		intencao.putExtra("usuarioEmpresa", usuarioEmpresa);
		intencao.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intencao);		
	}

	@Override
	public void clicouNoHome(UsuarioEmpresa usuarioEmpresa) {
		Intent intencao = new Intent(this, ProjetoActivity.class);
		intencao.putExtra("usuarioEmpresa", usuarioEmpresa);
		intencao.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intencao);
		
	}

	@Override
	public void clicouNoHome(UsuarioEmpresa usuarioEmpresa, Projeto projeto) {
		// TODO Auto-generated method stub
	}
	
	
}

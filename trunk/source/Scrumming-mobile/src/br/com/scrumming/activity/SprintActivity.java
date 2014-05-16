package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.SprintFragment;
import br.com.scrumming.interfaces.ClickedOnSprint;

public class SprintActivity extends FragmentActivity implements ClickedOnSprint{
	
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
}

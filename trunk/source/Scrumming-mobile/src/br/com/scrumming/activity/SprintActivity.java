package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
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
	
	//Instanciação dos Objetos e variáveis
	SprintFragment sprintFragment;
	
	/**
	* Método de criação da Activity
	* @param Bundle savedInstanceState
	* @return void
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		Projeto projeto = (Projeto)getIntent().getSerializableExtra("projeto");
		UsuarioEmpresa usuarioEmpresa = (UsuarioEmpresa)getIntent().getSerializableExtra("usuarioEmpresa");
		
		sprintFragment = (SprintFragment)getSupportFragmentManager().findFragmentByTag("sf");
		if (sprintFragment == null) {
			sprintFragment = SprintFragment.novaInstancia(projeto, usuarioEmpresa);
			getSupportFragmentManager().beginTransaction().add(R.id.master, sprintFragment, "sf").commit();	
		}
	}
	
	/**
	* Método proviniente da interface para exibir a activity com a lista de Itens de Backlog
	* @param Sprint sprint
	* @param UsuarioEmpresa usuarioEmpresa
	* @return void 
	*/
	@Override
	public void sprintFoiClicada(Sprint sprint, UsuarioEmpresa usuarioEmpresa) {
		Intent it3 = new Intent(this, SprintBacklogActivity.class);
		it3.putExtra("sprint", sprint);
		it3.putExtra("usuarioEmpresa", usuarioEmpresa);
		startActivity(it3);
	}

	/**
	* Método para aplicar logout e voltar para a tela de login
	* @param UsuarioEmpresa usuarioEmpresa
	* @return void
	*/
	@Override
	public void clicouNoLogout(UsuarioEmpresa usuarioEmpresa) {
		Intent intencao = new Intent(this, LoginActivity.class);
		intencao.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intencao);
		
	}

	/**
	* Método para encerrar a activity de Sprint
	* @param UsuarioEmpresa usuarioEmpresa
	* @return void
	*/
	@Override
	public void clicouNoHome(UsuarioEmpresa usuarioEmpresa) {
		finish();
	}
}
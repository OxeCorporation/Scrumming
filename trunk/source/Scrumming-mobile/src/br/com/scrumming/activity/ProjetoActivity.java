package br.com.scrumming.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.ProjetoFragment;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.interfaces.ClickedOnProjeto;

public class ProjetoActivity extends ActionBarActivity implements ClickedOnProjeto, ClickedOnLogout, ClickedOnHome{

	//Instanciação dos Objetos e variáveis
	ProjetoFragment projetoFragment;
	
	/**
	* Método de criação da Activity
	* @param Bundle savedInstanceState
	* @return void
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);

		UsuarioEmpresa usuarioEmpresa = (UsuarioEmpresa)getIntent().getSerializableExtra("usuarioEmpresa");
		
		projetoFragment = (ProjetoFragment)getSupportFragmentManager().findFragmentByTag("pf");
		if (projetoFragment == null) {
			projetoFragment = ProjetoFragment.novaInstancia(usuarioEmpresa);
			getSupportFragmentManager().beginTransaction().add(R.id.master, projetoFragment, "pf").commit();
		}
	}

	/**
	* Método proviniente da interface para exibir a activity com a lista de Sprints
	* @param Projeto projeto
	* @param UsuarioEmpresa usuarioEmpresa
	* @return void 
	*/
	@Override
	public void projetoFoiClicado(Projeto projeto, UsuarioEmpresa usuarioEmpresa) {
		Intent it2 = new Intent(this, SprintActivity.class);
		it2.putExtra("projeto", projeto);
		it2.putExtra("usuarioEmpresa", usuarioEmpresa);
		startActivity(it2);
		
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
	* Método para encerrar a activity de Projeto
	* @param UsuarioEmpresa usuarioEmpresa
	* @return void
	*/
	@Override
	public void clicouNoHome(UsuarioEmpresa usuarioEmpresa) {
		finish();
	}
}
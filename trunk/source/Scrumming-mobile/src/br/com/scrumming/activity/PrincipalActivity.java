package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.EmpresaFragment;
import br.com.scrumming.interfaces.ClickedOnEmpresa;
import br.com.scrumming.interfaces.ClickedOnLogout;

public class PrincipalActivity extends ActionBarActivity implements
		ClickedOnEmpresa, ClickedOnLogout {

	//Instancia��o dos Objetos e vari�veis
	TextView txtNomeUsuario;
	EmpresaFragment empresaFragment;

	/**
	* M�todo de cria��o da Activity
	* @param Bundle savedInstanceState
	* @return void
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		//new EmpresaFragment();
		
		Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		
		empresaFragment = (EmpresaFragment) getSupportFragmentManager().findFragmentByTag("f1");
		if (empresaFragment == null) {
			empresaFragment = EmpresaFragment.novaInstancia(usuario);
			getSupportFragmentManager().beginTransaction().add(R.id.master, empresaFragment, "f1").commit();
		}

	}

	/**
    * M�todo proviniente da interface para exibir a activity com a lista de projetos
	* @param UsuarioEmpresa usuarioEmpresa
	* @return void 
    */
	@Override
	public void empresaFoiClicada(UsuarioEmpresa usuarioEmpresa) {
		Intent it = new Intent(this, ProjetoActivity.class);
		it.putExtra("usuarioEmpresa", usuarioEmpresa);
		startActivity(it);
	}

	/**
	* M�todo para aplicar logout e voltar para a tela de login
	* @param UsuarioEmpresa usuarioEmpresa
	* @return void
	*/
	@Override
	public void clicouNoLogout(UsuarioEmpresa usuarioEmpresa) {
		Intent intencao = new Intent(this, LoginActivity.class);
		//intencao.putExtra("usuarioEmpresa", usuarioEmpresa);
		intencao.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intencao);
	}
}
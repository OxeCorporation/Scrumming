package br.com.scrumming.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.EmpresaFragment;
import br.com.scrumming.interfaces.ClickedOnEmpresa;
import br.com.scrumming.interfaces.ClickedOnLogout;

public class PrincipalActivity extends ActionBarActivity implements
		ClickedOnEmpresa, ClickedOnLogout {

	//Instanciação dos Objetos e variáveis
	TextView txtNomeUsuario;
	EmpresaFragment empresaFragment;

	/**
	* Método de criação da Activity
	* @param Bundle savedInstanceState
	* @return void
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		setarCorDoTitle();
		//new EmpresaFragment();
		Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		
		empresaFragment = (EmpresaFragment) getSupportFragmentManager().findFragmentByTag("f1");
		if (empresaFragment == null) {
			empresaFragment = EmpresaFragment.novaInstancia(usuario);
			getSupportFragmentManager().beginTransaction().add(R.id.master, empresaFragment, "f1").commit();
		}
		
		

	}
	
	private void setarCorDoTitle(){
    	int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
		    TextView title = (TextView) findViewById(actionBarTitleId);
		    if (title != null) {
		        title.setTextColor(Color.BLACK);
		    }
		}
    }
	
	/**
    * Método proviniente da interface para exibir a activity com a lista de projetos
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
	* Método para aplicar logout e voltar para a tela de login
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
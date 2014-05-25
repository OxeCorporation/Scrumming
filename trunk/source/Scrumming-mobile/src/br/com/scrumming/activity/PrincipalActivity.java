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

	TextView txtNomeUsuario;
	EmpresaFragment empresaFragment;

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

	@Override
	public void empresaFoiClicada(UsuarioEmpresa usuarioEmpresa) {
		Intent it = new Intent(this, ProjetoActivity.class);
		it.putExtra("usuarioEmpresa", usuarioEmpresa);
		startActivity(it);

	}

	@Override
	public void clicouNoLogout(UsuarioEmpresa usuarioEmpresa) {
		Intent intencao = new Intent(this, LoginActivity.class);
		//intencao.putExtra("usuarioEmpresa", usuarioEmpresa);
		intencao.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intencao);
		
	}
}
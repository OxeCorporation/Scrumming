package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.EmpresaFragment;
import br.com.scrumming.interfaces.ClickedOnEmpresa;

public class PrincipalActivity extends FragmentActivity implements
		ClickedOnEmpresa {

	TextView txtNomeUsuario;
	EmpresaFragment fragment1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		//new EmpresaFragment();

		Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		FragmentManager fm = getSupportFragmentManager();
		fragment1 = (EmpresaFragment) fm.findFragmentByTag("f1");
		fragment1 = EmpresaFragment.novaInstancia(usuario);

		fm.beginTransaction().add(R.id.master, fragment1, "f1").commit();

	}

	@Override
	public void empresaFoiClicada(UsuarioEmpresa usuarioEmpresa) {
		Intent it = new Intent(this, ProjetoActivity.class);
		it.putExtra("usuarioEmpresa", usuarioEmpresa);
		startActivity(it);

	}
}
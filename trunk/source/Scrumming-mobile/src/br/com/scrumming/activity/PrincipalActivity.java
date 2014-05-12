package br.com.scrumming.activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.BemVindoFragment;
import br.com.scrumming.fragment.ProjetoFragment;
import br.com.scrumming.interfaces.ClickedOnEmpresa;

//@SuppressLint({ "NewApi", "CommitTransaction" })
public class PrincipalActivity extends FragmentActivity implements ClickedOnEmpresa{

	TextView txtNomeUsuario;
	BemVindoFragment fragment1;
	ProjetoFragment projetoFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bemvindo);
		
		Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		fragment1 = (BemVindoFragment) getSupportFragmentManager().findFragmentByTag("f1");
		projetoFragment = (ProjetoFragment)getSupportFragmentManager().findFragmentByTag("f2");
		
		//if (fragment1 == null || projetoFragment == null){
			fragment1 = BemVindoFragment.novaInstancia(usuario);
			projetoFragment = new ProjetoFragment();
			
			getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.master, fragment1, "f1")
				.add(R.id.master, projetoFragment, "f2")
				.commit();
		//}

	}

	@Override
	public void empresaFoiClicada(UsuarioEmpresa usuarioEmpresa) {
		Intent it = new Intent(this, ProjetoActivity.class);
		it.putExtra("usuarioEmpresa", usuarioEmpresa);
		startActivity(it);
		
	}
}
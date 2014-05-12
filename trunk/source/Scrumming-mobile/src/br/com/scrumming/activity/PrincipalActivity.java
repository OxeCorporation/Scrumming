package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.EmpresaFragment;
import br.com.scrumming.fragment.ProjetoFragment;
import br.com.scrumming.interfaces.ClickedOnEmpresa;

//@SuppressLint({ "NewApi", "CommitTransaction" })
public class PrincipalActivity extends FragmentActivity implements ClickedOnEmpresa{

	TextView txtNomeUsuario;
	EmpresaFragment empresaFragment;
	ProjetoFragment projetoFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		empresaFragment = (EmpresaFragment) getSupportFragmentManager().findFragmentByTag("f1");
		projetoFragment = (ProjetoFragment)getSupportFragmentManager().findFragmentByTag("f2");
		
		//if (fragment1 == null || projetoFragment == null){
			empresaFragment = EmpresaFragment.novaInstancia(usuario);
			projetoFragment = new ProjetoFragment();
			
			getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.master, empresaFragment, "f1")
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
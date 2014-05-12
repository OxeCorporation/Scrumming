package br.com.scrumming.activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.fragment.BemVindoFragment;

@SuppressLint({ "NewApi", "CommitTransaction" })
public class BemVindoActivity extends FragmentActivity {

	TextView txtNomeUsuario;
	BemVindoFragment fragment1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bemvindo);
		new BemVindoFragment();

		Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		FragmentManager fm = getFragmentManager();
		fragment1 = (BemVindoFragment) fm.findFragmentByTag("f1");
		fragment1 = BemVindoFragment.novaInstancia(usuario);

		fm.beginTransaction().add(R.id.master, fragment1, "f1").commit();

	}
}
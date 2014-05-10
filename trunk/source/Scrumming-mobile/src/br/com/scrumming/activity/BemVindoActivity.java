package br.com.scrumming.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.scrumming.domain.Usuario;

public class BemVindoActivity extends FragmentActivity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Usuario usuario = (Usuario)getIntent().getSerializableExtra("usuario");
		
		BemVindoFragment bvf = BemVindoFragment.novaInstancia(usuario);
		
		getSupportFragmentManager()
			.beginTransaction()
			.add(android.R.id.content, bvf)
			.commit();
    }
}
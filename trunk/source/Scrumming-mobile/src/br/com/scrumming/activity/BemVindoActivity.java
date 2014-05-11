package br.com.scrumming.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.Usuario;

public class BemVindoActivity extends Activity {

	TextView txtNomeUsuario;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bemvindo);
		
        Usuario usuario = (Usuario)getIntent().getSerializableExtra("usuario");
        
		txtNomeUsuario = (TextView)findViewById(R.id.txtNome);
		txtNomeUsuario.setText(usuario.getNome());
		
    }
}
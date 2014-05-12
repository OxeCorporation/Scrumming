package br.com.scrumming.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.scrumming.R;
import br.com.scrumming.asynctask.AsyncTaskUsuario;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.interfaces.InterfaceUsuario;

public class LoginActivity extends Activity implements InterfaceUsuario {
	
	EditText textLogin, textSenha;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        textLogin = (EditText) findViewById(R.id.editTxtLogin);
		textSenha = (EditText) findViewById(R.id.editTxtSenha);
		Button btnLogar = (Button) findViewById(R.id.btnLogar);
		btnLogar.setOnClickListener(btnLogarOnClickListener);
    }
    
    private OnClickListener btnLogarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String login = textLogin.getText().toString();
			String senha = textSenha.getText().toString();
			new AsyncTaskUsuario(LoginActivity.this).execute(login, senha);
		}
	};
	
	@SuppressLint("ShowToast")
	@Override
	public void logarComUsuario(Usuario usuario) {
		Toast.makeText(this, usuario.getEmail(), Toast.LENGTH_LONG);
		Toast toast;
		if (usuario.getCodigo() == null) {
			toast = Toast.makeText(this, "Usuário não Existe", Toast.LENGTH_LONG);
			toast.show();
		}else{
			Intent it = new Intent(this, BemVindoActivity.class);
			it.putExtra("usuario", usuario);
			startActivity(it);
		}
	}
}
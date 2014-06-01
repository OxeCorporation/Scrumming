package br.com.scrumming.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.asynctask.AsyncTaskUsuario;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.interfaces.InterfaceUsuario;

@SuppressLint("NewApi")
public class LoginActivity extends ActionBarActivity implements
		InterfaceUsuario {

	// Instanciação dos Objetos e variáveis
	EditText textLogin, textSenha;
	String titulo, mensagem, login, senha;

	/**
	 * Método de criação da Activity
	 * 
	 * @param Bundle
	 *            savedInstanceState
	 * @return void
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setarCorDoTitle();
		
		textLogin = (EditText) findViewById(R.id.editTxtLogin);
		textSenha = (EditText) findViewById(R.id.editTxtSenha);
		Button btnLogar = (Button) findViewById(R.id.btnLogar);
		btnLogar.setOnClickListener(btnLogarOnClickListener);

		getSupportActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.bg_principal));

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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.menu_login, menu);

		return true;
	}

	/**
	 * Método de clicar no botão logar
	 * 
	 * @param View
	 *            v
	 * @return void
	 */
	private OnClickListener btnLogarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			 login = textLogin.getText().toString();
			 senha = textSenha.getText().toString();
			if (login != null && !senha.equals("")) {
				new AsyncTaskUsuario(LoginActivity.this).execute(login, senha);
			} else {
				if (login.equals("")) {
					titulo = "Informação";
					mensagem = "Informe o login";
				}
				if (senha.equals("")) {
					titulo = "Informação";
					mensagem = "Informe a senha";
				}
				if (login.equals("") && senha.equals("")) {
					titulo = "Informação";
					mensagem = "Informe login e senha";
				}
				alert(titulo, mensagem);
			}
		}
	};

	/**
	 * Método para exibir um alerta na tela
	 * 
	 * @param String
	 *            titulo
	 * @param String
	 *            mensagem
	 * @return void
	 */
	@SuppressWarnings("deprecation")
	public void alert(String titulo, String mensagem) {
		AlertDialog alerta = new AlertDialog.Builder(this).create();
		alerta.setTitle(titulo);
		alerta.setMessage(mensagem);
		alerta.setButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		alerta.show();
	}

	/**
	 * Método proviniente da interface para logar com o usuário retornado do
	 * service
	 * 
	 * @param Usuario
	 *            usuario
	 * @return void
	 */
	@SuppressLint("ShowToast")
	@Override
	public void logarComUsuario(Usuario usuario) {
		if (usuario == null) {
			titulo = "Infermação";
			mensagem = "Login ou senha inválida";
			alert(titulo, mensagem);
		} else {
			login = "";
			senha = "";
			Intent it = new Intent(this, PrincipalActivity.class);
			it.putExtra("usuario", usuario);
			startActivity(it);
		}
	}
}
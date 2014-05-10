package br.com.scrumming.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.scrumming.R;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.implementations.AsyncTaskUsuario;
import br.com.scrumming.implementations.InterfaceUsuario;

public class LoginFragment extends Fragment implements InterfaceUsuario {

	EditText textLogin, textSenha;
	InterfaceUsuario IUsuario;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container);
		textLogin = (EditText) view.findViewById(R.id.editTxtLogin);
		textSenha = (EditText) view.findViewById(R.id.editTxtSenha);
		Button btnLogar = (Button) view.findViewById(R.id.btnLogar);
		btnLogar.setOnClickListener(btnLogarOnClickListener);

		return view;
	}

	private OnClickListener btnLogarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String login = textLogin.getText().toString();
			String senha = textSenha.getText().toString();
			new AsyncTaskUsuario(LoginFragment.this).execute(login, senha);
			
		}
	};

	@SuppressLint("ShowToast")
	@Override
	public void logarComUsuario(Usuario usuario) {
		Toast.makeText(getActivity(), usuario.getEmail(), Toast.LENGTH_LONG);
		Toast toast;
		if (usuario.getCodigo() == null) {
			toast = Toast.makeText(getActivity(), "Usuário não Existe", Toast.LENGTH_LONG);
			toast.show();
		}else{
			Intent it = new Intent(getActivity(), BemVindoActivity.class);
			it.putExtra("usuario", usuario);
			startActivity(it);
		}
	}
}
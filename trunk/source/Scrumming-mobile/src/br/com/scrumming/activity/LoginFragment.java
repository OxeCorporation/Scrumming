package br.com.scrumming.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.scrumming.R;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.implementations.AsyncTaskUsuario;
import br.com.scrumming.implementations.InterfaceUsuario;

public class LoginFragment extends Fragment implements InterfaceUsuario {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Button btnLogar = (Button) getView().findViewById(R.id.btnLogar);
		btnLogar.setOnClickListener(btnLogarOnClickListener);
		
		return inflater.inflate(R.layout.fragment_login, container, false);
	}
	
	private OnClickListener btnLogarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			new AsyncTaskUsuario().execute("");
		}
	};
	
	@Override
	public void logarComUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
	}
}
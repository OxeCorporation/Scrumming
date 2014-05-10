package br.com.scrumming.implementations;

import android.os.AsyncTask;
import br.com.scrumming.domain.Usuario;

public class AsyncTaskUsuario extends AsyncTask<String, Void, Usuario> {

	private InterfaceUsuario interfaceUsuario;
	
	public AsyncTaskUsuario(){}
	
	public AsyncTaskUsuario(InterfaceUsuario interfaceUsuario) {
		this.interfaceUsuario = interfaceUsuario;
	}
	
	@Override
	protected Usuario doInBackground(String... params) {
		String log = params[0];
		String senha = params[1];
		return RestUsuario.retorneUsuario(log,senha);
	}
	
	@Override
	protected void onPostExecute(Usuario usuario) {
		interfaceUsuario.logarComUsuario(usuario);
	}
}
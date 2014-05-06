package br.com.scrumming.implementations;

import android.os.AsyncTask;
import br.com.scrumming.domain.Usuario;

public class AsyncTaskUsuario extends AsyncTask<String, Void, Usuario> {

	private InterfaceUsuario interfaceUsuario;
	
	public AsyncTaskUsuario(InterfaceUsuario interfaceUsuario) {
		this.interfaceUsuario = interfaceUsuario;
	}
	
//	public Usuario dadosUsuario(String login, String senha){
//		Usuario usuario = new Usuario();
//		usuario.setLogin(login);
//		usuario.setSenha(senha);
//		return usuario;
//	}
	
	@Override
	protected Usuario doInBackground(String... params) {
		return RestUsuario.retorneUsuario(params.toString(), params.toString());
	}
	
	@Override
	protected void onPostExecute(Usuario usuario) {
		interfaceUsuario.logarComUsuario(usuario);
	}
}
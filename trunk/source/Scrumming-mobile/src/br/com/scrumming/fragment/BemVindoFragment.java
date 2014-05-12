package br.com.scrumming.fragment;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.adapter.EmpresaAdapter;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.rest.RestEmpresa;

public class BemVindoFragment extends Fragment {

	Usuario usuario;
	TextView txtNome;
	List<Empresa> listaEmpresas;
	
	public static BemVindoFragment novaInstancia(Usuario usuario){
		Bundle args = new Bundle();
		args.putSerializable("usuario", usuario);
		
		BemVindoFragment bvf = new BemVindoFragment();
		bvf.setArguments(args);
		return bvf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_bemvindo, container);
		
		txtNome = (TextView)layout.findViewById(R.id.txtNome);
		
		usuario = (Usuario)getArguments().getSerializable("usuario");
		txtNome.setText(usuario.getNome());
		
		return layout;
	}
	
	private void AtualizarLista() {
		EmpresaAdapter adapter = new EmpresaAdapter(getActivity(), listaEmpresas);
		//setListAdapter(adapter);
	}
	
	class AsyncTaskEmpresa extends AsyncTask<Usuario, Void, List<Empresa>> {

		@Override
		protected List<Empresa> doInBackground(Usuario... params) {
			return RestEmpresa.retorneEmpresas(params[0]);
		}
		
		@Override
		protected void onPostExecute(List<Empresa> empresas) {
			super.onPostExecute(empresas);
			if(empresas != null) {
				listaEmpresas = empresas;
				AtualizarLista();
			}
		}
	}
}
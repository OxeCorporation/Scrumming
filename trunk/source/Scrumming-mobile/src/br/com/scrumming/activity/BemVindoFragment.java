package br.com.scrumming.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.Usuario;

public class BemVindoFragment extends Fragment {

	Usuario usuario;
	TextView txtNome;
	
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
		
		txtNome = (TextView)layout.findViewById(R.id.txtNomeUsuario);
		
		usuario = (Usuario)getArguments().getSerializable("usuario");
		txtNome.setText(usuario.getNome());
		
		return layout;
	}
}
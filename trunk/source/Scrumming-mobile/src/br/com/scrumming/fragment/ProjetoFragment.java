package br.com.scrumming.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.scrumming.R;
import br.com.scrumming.adapter.ProjetoAdapter;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.rest.RestProjeto;

@SuppressLint("NewApi")
public class ProjetoFragment extends ListFragment {

	AsyncTaskProjeto taskProjeto;
	List<Projeto> listaProjetos;
	UsuarioEmpresa usuarioEmpresa;
	
	Empresa empresa;
	
	public static ProjetoFragment novaInstancia(UsuarioEmpresa usuarioEmpresa){
		Bundle args = new Bundle();
		args.putSerializable("usuarioEmpresa", usuarioEmpresa);
		
		ProjetoFragment bvf = new ProjetoFragment();
		bvf.setArguments(args);
		return bvf;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		if (listaProjetos != null){
			AtualizarListaDeProjetos();

		} else {
			if (taskProjeto != null && taskProjeto.getStatus() == Status.RUNNING){
				//mostrarProgress();

			} else {
				taskProjeto = new AsyncTaskProjeto();
				taskProjeto.execute(usuarioEmpresa);
			}
		}
		
	}
	//EDITAR
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_projetos, container,false);
		
		
		usuarioEmpresa = (UsuarioEmpresa) getArguments().getSerializable("usuarioEmpresa");
		
		return layout;
	}
	
	private void AtualizarListaDeProjetos() {
		ProjetoAdapter adapter = new ProjetoAdapter(getActivity(), listaProjetos);
		setListAdapter(adapter);
	}
	
	
	
	class AsyncTaskProjeto extends AsyncTask<UsuarioEmpresa, Void, List<Projeto>>{
		
		@Override
		protected List<Projeto> doInBackground(UsuarioEmpresa... params) {
			return RestProjeto.retorneProjetosPorUsuario(params[0]);
		}

		@Override
		protected void onPostExecute(List<Projeto> projetos) {
			super.onPostExecute(projetos);
			if(projetos != null) {
				listaProjetos = projetos;
				AtualizarListaDeProjetos();
			}
		}
		
	}
}

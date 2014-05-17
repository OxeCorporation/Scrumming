package br.com.scrumming.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.support.v4.app.ListFragment;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.adapter.UsuarioEmpresaAdapter;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.interfaces.ClickedOnEmpresa;
import br.com.scrumming.rest.RestEmpresa;

@SuppressLint("NewApi")
public class EmpresaFragment extends ListFragment {

	Usuario usuario;
	TextView txtNome;
	List<UsuarioEmpresa> listaEmpresas;
	AsyncTaskEmpresa task;

	public static EmpresaFragment novaInstancia(Usuario usuario) {
		Bundle args = new Bundle();
		args.putSerializable("usuario", usuario);

		EmpresaFragment bvf = new EmpresaFragment();
		bvf.setArguments(args);
		return bvf;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		if (listaEmpresas != null) {
			AtualizarLista();

		} else {
			if (task != null && task.getStatus() == Status.RUNNING) {
				// mostrarProgress();

			} else {
				task = new AsyncTaskEmpresa();
				task.execute(usuario);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View layout = inflater.inflate(R.layout.fragment_empresas, container,
				false);

		usuario = (Usuario) getArguments().getSerializable("usuario");

		return layout;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (getActivity() instanceof ClickedOnEmpresa) {
			((ClickedOnEmpresa) getActivity()).empresaFoiClicada(listaEmpresas
					.get(position));
		}
	}

	private void AtualizarLista() {
		UsuarioEmpresaAdapter adapter = new UsuarioEmpresaAdapter(
				getActivity(), listaEmpresas);
		setListAdapter(adapter);
		if (listaEmpresas.size() == 1) {
			if (getActivity() instanceof ClickedOnEmpresa) {
				((ClickedOnEmpresa) getActivity())
						.empresaFoiClicada(listaEmpresas.get(0));
			}
		}
	}

	class AsyncTaskEmpresa extends
			AsyncTask<Usuario, Void, List<UsuarioEmpresa>> {

		@Override
		protected List<UsuarioEmpresa> doInBackground(Usuario... params) {
			return RestEmpresa.retorneEmpresas(params[0]);
		}

		@Override
		protected void onPostExecute(List<UsuarioEmpresa> usuarioEmpresa) {
			super.onPostExecute(usuarioEmpresa);
			if (usuarioEmpresa != null) {
				listaEmpresas = usuarioEmpresa;
				AtualizarLista();
			}
		}
	}
}
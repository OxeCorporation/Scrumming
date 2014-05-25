package br.com.scrumming.fragment;

import java.util.Date;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import br.com.scrumming.R;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.TarefaReporte;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.rest.RestTarefaReport;

public class TarefaReportFragment extends Fragment {

	AsyncTaskTarefaReport taskTarefa;
	ItemBacklog itemBacklog;
	UsuarioEmpresa usuarioEmpresa;
	EditText textDataReport, textTempoReport, textTempoRestante;
	TarefaReporte tarefaReport;
	Tarefa tarefa;
	Sprint sprint;

	public static TarefaReportFragment novaInstancia(ItemBacklog itemBacklog, Tarefa tarefa, Sprint sprint,
			UsuarioEmpresa usuarioEmpresa) {
		Bundle args = new Bundle();
		args.putSerializable("itemBacklog", itemBacklog);
		args.putSerializable("usuarioEmpresa", usuarioEmpresa);

		TarefaReportFragment bvf = new TarefaReportFragment();
		bvf.setArguments(args);
		return bvf;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);

		// Transforma o Home "Scrumming" em um bot�o
		ActionBar ab = ((ActionBarActivity) getActivity())
				.getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("Reporte");

		if (taskTarefa != null && taskTarefa.getStatus() == Status.RUNNING) {
			// mostrarProgress();

		} else {
			taskTarefa = new AsyncTaskTarefaReport();
			taskTarefa.execute(itemBacklog.getCodigo());
		}

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View layout = inflater.inflate(R.layout.fragment_tarefa_report,
				container, false);

		textDataReport = (EditText) layout.findViewById(R.id.editDataReport);
		textTempoReport = (EditText) layout.findViewById(R.id.editTempoReport);
		textTempoRestante = (EditText) layout
				.findViewById(R.id.editTempoRestante);
		Button btnReportar = (Button) layout.findViewById(R.id.btmReport);
		btnReportar.setOnClickListener(btnReportOnClickListener);
		Button btnCancelar = (Button) layout.findViewById(R.id.btmCancelar);
		btnCancelar.setOnClickListener(btnCancelarOnClickListener);

		itemBacklog = (ItemBacklog) getArguments().getSerializable(
				"itemBacklog");
		usuarioEmpresa = (UsuarioEmpresa) getArguments().getSerializable(
				"usuarioEmpresa");
		tarefa = (Tarefa) getArguments().getSerializable("tarefa");
		sprint = (Sprint) getArguments().getSerializable("sprint");

		return layout;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_fragment_telas, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.logout:
			if (getActivity() instanceof ClickedOnLogout) {
				((ClickedOnLogout) getActivity()).clicouNoLogout(null);
				;
			}
			break;

		case android.R.id.home:
			if (getActivity() instanceof ClickedOnHome) {
				((ClickedOnHome) getActivity()).clicouNoHome(usuarioEmpresa);
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	class AsyncTaskTarefaReport extends AsyncTask<Integer, Void, Void> {

		protected void doInBackgroundReport(TarefaReporte tarefaReport,
				Integer... params) {
			RestTarefaReport.retornarTarefaReport(tarefaReport, params[1], params[2]);
		}

		@Override
		protected Void doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	private OnClickListener btnReportOnClickListener = new OnClickListener() {
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			tarefaReport = new TarefaReporte();
			tarefaReport.setTarefa(tarefa);
			tarefaReport.setUsuario(usuarioEmpresa.getUsuario());
			tarefaReport.setDataReporte(new Date(textDataReport.toString()));
			tarefaReport.setTempoReportado(Integer.parseInt(textTempoReport.toString()));
			tarefaReport.setTempoRestante(Integer.parseInt(textTempoRestante.toString()));
			
			taskTarefa.doInBackgroundReport(tarefaReport, sprint.getCodigo(), usuarioEmpresa.getUsuario().getCodigo());
		}
	};
	private OnClickListener btnCancelarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			getActivity().finish();
		}
	};

}

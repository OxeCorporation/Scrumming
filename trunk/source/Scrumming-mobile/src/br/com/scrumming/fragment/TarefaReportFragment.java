package br.com.scrumming.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

	//Instanciação dos Objetos e variáveis
	ItemBacklog itemBacklog;
	UsuarioEmpresa usuarioEmpresa;
	EditText textTempoReport, textTempoRestante, textDataReport;
	TarefaReporte tarefaReport;
	Tarefa tarefa;
	Sprint sprint;

	/**
	* Método que gera uma nova instancia do fragment de TarefaReport
	* @param ItemBacklog itemBacklog
	* @param UsuarioEmpresa usuarioEmpresa
	* @param Sprint sprint
	* @return TarefaReportFragment
	*/
	public static TarefaReportFragment novaInstancia(ItemBacklog itemBacklog,
			Tarefa tarefa, Sprint sprint, UsuarioEmpresa usuarioEmpresa) {
		Bundle args = new Bundle();
		args.putSerializable("itemBacklog", itemBacklog);
		args.putSerializable("usuarioEmpresa", usuarioEmpresa);
		args.putSerializable("sprint", sprint);
		args.putSerializable("tarefa", tarefa);

		TarefaReportFragment bvf = new TarefaReportFragment();
		bvf.setArguments(args);
		return bvf;
	}

	/**
	* Método utilizado no momento que a Activity do fragment é criada
	* @param Bundle savedInstanceState
	* @return void
	*/
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);

		// Transforma o Home "Scrumming" em um botão
		ActionBar ab = ((ActionBarActivity) getActivity())
				.getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("Reporte");

	}

	/**
	* Método utilizado no momento que a View é criada
	* @param LayoutInflater inflater
	* @param ViewGroup container
	* @param Bundle savedInstanceState
	* @return View
	*/
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View layout = inflater.inflate(R.layout.fragment_tarefa_report,
				container, false);

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

		textDataReport = (EditText) layout.findViewById(R.id.editDataReport);

		textDataReport.addTextChangedListener(new TextWatcher() {

			boolean isUpdating;

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int after) {
				if (isUpdating) {
					isUpdating = false;
					return;
				}
				boolean hasMask = s.toString().indexOf('.') > -1
						|| s.toString().indexOf('-') > -1;

				String str = s.toString().replaceAll("[/]", "")
						.replaceAll("[/]", "");

				if (after > before) {
					if (str.length() > 5) {
						str = str.substring(0, 2) + '/' + str.substring(2, 4)
								+ '/' + str.substring(4);
					} else if (str.length() > 2) {
						str = str.substring(0, 2) + '/' + str.substring(2);
					}
					isUpdating = true;
					textDataReport.setText(str);
					textDataReport.setSelection(textDataReport.getText()
							.length());
				} else {
					isUpdating = true;
					textDataReport.setText(str);
					textDataReport.setSelection(Math.max(
							0,
							Math.min(hasMask ? start - before : start,
									str.length())));
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		return layout;
	}

	/**
	* Método utilizado para exibir as opçoes de menu
	* @param Menu menu
	* @param MenuInflater inflater
	* @return Void
	*/
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_fragment_telas, menu);
	}

	/**
	* Método utilizado ao clicar em uma opção no menu
	* @param MenuItem item
	* @return boolean
	*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.logout:
			if (getActivity() instanceof ClickedOnLogout) {
				((ClickedOnLogout) getActivity()).clicouNoLogout(null);
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

	/**
	* Método de clicar no botão de reportagem de horas
	* @param View v
	* @return void 
	*/
	private OnClickListener btnReportOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			setarTarefaReporte();
			 new Thread(new Runnable() {
			        public void run() {
			        	RestTarefaReport.retornarTarefaReport(tarefaReport, sprint.getCodigo(),
			        			itemBacklog.getCodigo(), tarefa.getCodigo());
			        }
			    }).start();
			 //EXIBI A MENSAGEM DE HORAS REPORTADAS COM SUCESSO
			 mensagemHorasReportadas();
			 getActivity().finish();
		}

		private void setarTarefaReporte() {
			tarefaReport = new TarefaReporte();
			tarefaReport.setTarefa(tarefa);
			tarefaReport.setUsuario(usuarioEmpresa.getUsuario());
			tarefaReport.setDataReporte(ConvertToDateBR(textDataReport
					.getText().toString()));
			tarefaReport.setTempoReportado(Integer.parseInt(textTempoReport
					.getText().toString()));
			tarefaReport.setTempoRestante(Integer.parseInt(textTempoRestante
					.getText().toString()));
		}

		@SuppressLint("SimpleDateFormat")
		private Date ConvertToDateBR(String dateString) {
			if (dateString != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date convertedDate;
				try {
					convertedDate = dateFormat.parse(dateString);
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				return convertedDate;
			}
			return null;
		}
	};
	
	/**
	 * @author Naftali
	 * método responsável por exibir o dialog de "Horas Reportadas com Sucesso"
	 * */
	private void mensagemHorasReportadas() {
		AlertDialog alertDialog = new AlertDialog.Builder(
				getActivity()).create();

		// Setting Dialog Title
		alertDialog.setTitle("Info");

		// Setting Dialog Message
		alertDialog.setMessage("Horas Reportadas com Sucesso");

		// Setting Icon to Dialog
		alertDialog.setIcon(android.R.drawable.ic_dialog_info);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to execute after dialog closed
				//Toast.makeText(getActivity(), "Operação Realizada com Sucesso", Toast.LENGTH_SHORT).show();
			}
		});

		// Showing Alert Message
		alertDialog.show();
		
	}
	
	private OnClickListener btnCancelarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			getActivity().finish();
		}
	};
}
package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.TarefaReportFragment;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;

public class TarefaReportActivity extends ActionBarActivity implements ClickedOnLogout, ClickedOnHome{

	TarefaReportFragment tarefaReportFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);

		ItemBacklog itemBacklog = (ItemBacklog) getIntent().getSerializableExtra("itemBacklog");
		UsuarioEmpresa usuarioEmpresa = (UsuarioEmpresa) getIntent().getSerializableExtra("usuarioEmpresa");
		Tarefa tarefa = (Tarefa) getIntent().getSerializableExtra("tarefa");
		Sprint sprint = (Sprint) getIntent().getSerializableExtra("sprint");

		tarefaReportFragment = (TarefaReportFragment)getSupportFragmentManager().findFragmentByTag("trf");
		if (tarefaReportFragment == null) {
			tarefaReportFragment = TarefaReportFragment.novaInstancia(itemBacklog, tarefa, sprint,  usuarioEmpresa);

			getSupportFragmentManager().beginTransaction().add(R.id.master, tarefaReportFragment, "trf").commit();
		}

	}
	
	@Override
	public void clicouNoLogout(UsuarioEmpresa usuarioEmpresa) {
		Intent intencao = new Intent(this, LoginActivity.class);
		intencao.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intencao);
		
	}

	@Override
	public void clicouNoHome(UsuarioEmpresa usuarioEmpresa) {
		finish();
	}
	
}
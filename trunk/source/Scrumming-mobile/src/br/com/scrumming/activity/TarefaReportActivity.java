package br.com.scrumming.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import br.com.scrumming.R;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.TarefaReporte;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.fragment.TarefaReportFragment;
import br.com.scrumming.interfaces.ClickedOnHome;
import br.com.scrumming.interfaces.ClickedOnLogout;
import br.com.scrumming.interfaces.FecharReporteTarefa;

public class TarefaReportActivity extends ActionBarActivity implements ClickedOnLogout, ClickedOnHome, FecharReporteTarefa{

	//Instanciação dos Objetos e variáveis
	TarefaReportFragment tarefaReportFragment;

	/**
	* Método de criação da Activity
	* @param Bundle savedInstanceState
	* @return void
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		setarCorDoTitle();
		
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
	
	private void setarCorDoTitle(){
    	int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
		    TextView title = (TextView) findViewById(actionBarTitleId);
		    if (title != null) {
		        title.setTextColor(Color.BLACK);
		    }
		}
    }
	
	/**
	* Método para aplicar logout e voltar para a tela de login
	* @param UsuarioEmpresa usuarioEmpresa
	* @return void
	*/
	@Override
	public void clicouNoLogout(UsuarioEmpresa usuarioEmpresa) {
		Intent intencao = new Intent(this, LoginActivity.class);
		intencao.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intencao);
		
	}

	/**
	* Método para encerrar a activity de TarefaReport
	* @param UsuarioEmpresa usuarioEmpresa
	* @return void
	*/
	@Override
	public void clicouNoHome(UsuarioEmpresa usuarioEmpresa) {
		finish();
	}

	@Override
	public void reproteTarefaFechada(TarefaReporte tarefaReport) {
		// TODO Auto-generated method stub
		  Intent data = new Intent();
		  data.putExtra("tarefaRetortada", tarefaReport);
		  // Activity finished ok, return the data
		  setResult(RESULT_OK, data);
		  finish();
	}
}
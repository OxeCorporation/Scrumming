package br.com.scrumming.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.fragment.SprintFragment;

public class SprintActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		Projeto projeto = (Projeto)getIntent().getSerializableExtra("projeto");
		SprintFragment sprintfragment = SprintFragment.novaInstancia(projeto);
		
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.master, sprintfragment)
			.commit();			
	}
}

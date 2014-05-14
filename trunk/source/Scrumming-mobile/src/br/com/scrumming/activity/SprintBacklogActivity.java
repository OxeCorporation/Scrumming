package br.com.scrumming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.scrumming.R;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.fragment.SprintBacklogFragment;
import br.com.scrumming.interfaces.ClickedOnSprint;

public class SprintBacklogActivity extends FragmentActivity implements ClickedOnSprint{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		Sprint sprint = (Sprint)getIntent().getSerializableExtra("sprint");
		SprintBacklogFragment sbf = SprintBacklogFragment.novaInstancia(sprint);
		
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.master, sbf)
			.commit();			
	}

	@Override
	public void sprintFoiClicada(Sprint sprint) {
		Intent it3 = new Intent(this, SprintBacklogActivity.class);
		it3.putExtra("sprint", sprint);
		startActivity(it3);
		
	}
}

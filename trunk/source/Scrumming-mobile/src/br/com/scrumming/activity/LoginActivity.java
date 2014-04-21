package br.com.scrumming.activity;

import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.os.Bundle;
import br.com.scrumming.R;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private void doSomething() {
    	RestTemplate restTemplate = new RestTemplate();
	}
}
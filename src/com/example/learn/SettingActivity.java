package com.example.learn;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;


public class SettingActivity extends Activity {
	EditText userName;
	EditText password;
	EditText server;
	Button confirm;
	RadioGroup rg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        userName = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        server = (EditText)findViewById(R.id.server);
        confirm = (Button)findViewById(R.id.confirm);
        rg = (RadioGroup)findViewById(R.id.radioGroup1);
        confirm.setOnClickListener(new Button.OnClickListener()
        {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SettingActivity.this, LearnActivity.class);
				startActivity(intent);
				SettingActivity.this.finish();
			}
        	
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }
}

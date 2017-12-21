package com.slabs.parkassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Sreekanth Putta on 22-12-2017.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView userName = (TextView) findViewById(R.id.userName);
        TextView password = (TextView) findViewById(R.id.password);
        TextView clickToRegister = (TextView) findViewById(R.id.clickToRegister);
        Button login = (Button) findViewById(R.id.login);

        clickToRegister.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.clickToRegister:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
                break;
            case R.id.login:

                break;

        }
    }
}

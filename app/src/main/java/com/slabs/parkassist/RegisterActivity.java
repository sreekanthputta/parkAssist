package com.slabs.parkassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Sreekanth Putta on 22-12-2017.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView fullName = (TextView) findViewById(R.id.fullName);
        TextView userName = (TextView) findViewById(R.id.userName);
        TextView password = (TextView) findViewById(R.id.password);
        TextView cnfmPassword = (TextView) findViewById(R.id.cnfmPassword);
        TextView mobile = (TextView) findViewById(R.id.mobile);
        TextView clickToLogin = (TextView) findViewById(R.id.clickToLogin);
        Button register = (Button) findViewById(R.id.register);

        clickToLogin.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clickToLogin:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
            case R.id.register:
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }
    }
}

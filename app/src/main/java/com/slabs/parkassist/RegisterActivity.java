package com.slabs.parkassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.slabs.parkassist.retrofit.ApiUtils;
import com.slabs.parkassist.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sreekanth Putta on 22-12-2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private RetrofitService mService;
    TextView fullName, userName, email, password, cnfmPassword, mobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mService = ApiUtils.getSOService();

        fullName = (TextView) findViewById(R.id.fullName);
        userName = (TextView) findViewById(R.id.userName);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        cnfmPassword = (TextView) findViewById(R.id.cnfmPassword);
        mobile = (TextView) findViewById(R.id.mobile);
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
                if(password.getText().toString().equals(cnfmPassword.getText().toString())) {
                    register();
                }else{
                    Toast.makeText(getApplicationContext(), "Passwords doesn't match", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    void register(){
        mService.userRegistration(fullName.getText().toString(), userName.getText().toString(), email.getText().toString(), password.getText().toString(), mobile.getText().toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Username or email already exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Network Error, try again later", Toast.LENGTH_LONG).show();
            }
        });
    }
}

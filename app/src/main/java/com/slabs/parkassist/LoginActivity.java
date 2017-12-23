package com.slabs.parkassist;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.slabs.parkassist.models.Login;
import com.slabs.parkassist.retrofit.ApiUtils;
import com.slabs.parkassist.retrofit.RetrofitClient;
import com.slabs.parkassist.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sreekanth Putta on 22-12-2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText userName, password;
    private RetrofitService mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mService = ApiUtils.getSOService();

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
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
                login();
                break;
        }
    }

    void login(){
        RetrofitClient.getClient().create(RetrofitService.class).userLogin(userName.getText().toString(), password.getText().toString()).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login = response.body();
                if(login.getStatus().equals("success")) {
                    editor.putString("userName", userName.getText().toString());
                    editor.putString("secretKey", login.getRandomKey());
                    editor.putString("name", login.getFullName());
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Account details error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Network Error, try again later", Toast.LENGTH_LONG).show();
            }
        });
    }
}

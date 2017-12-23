package com.slabs.parkassist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getString("userName", "").equals("")){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        setContentView(R.layout.activity_main);

        //startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        TextView bookRemote = (TextView) findViewById(R.id.bookadvance);
        bookRemote.setOnClickListener(this);
        TextView bookNow = (TextView) findViewById(R.id.booknow);
        bookNow.setOnClickListener(this);
        (new getTrueTime()).execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.booknow:
                startActivity(new Intent(getApplicationContext(), BookNowActivity.class));
                finish();
                break;
            case R.id.bookadvance:
//                startActivity(new Intent(getApplicationContext(), ));
//                finish();
                break;
        }
    }

    private class getTrueTime extends AsyncTask<Void, Void, String>{
        Long startTime;
        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            StringBuffer response = new StringBuffer();
            try {
                URL url = new URL("http://192.168.0.100/parkAssist/time.php");

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader isw = new InputStreamReader(in);

                int data = isw.read();
                while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    response.append(current);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return response.toString();
        }

        @Override
        protected void onPreExecute() {
            startTime = System.currentTimeMillis();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                Long serverTime = Long.parseLong(response) / 10;
                Long responseTime = System.currentTimeMillis() - startTime;
                Log.d("trueTime", responseTime / 2 + "  " + String.valueOf(serverTime - responseTime / 2 - startTime));
                editor.putLong("timeSync", (serverTime - responseTime / 2 - startTime));
            }catch (Exception ignored){}
            super.onPostExecute(response);
        }
    }
}

package com.jayasuriyat.mytimerapplication;

import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.io.BufferedReader;

import java.io.InputStreamReader;


import java.net.*;
import java.io.*;


public class MainActivity extends ActionBarActivity
{

    Long s;
    long t=10;
    TextView timer,num;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        t=10;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer=(TextView)findViewById(R.id.timer);
        num=(TextView)findViewById(R.id.number);
        Timer();



    }

    public void Timer()
    {

        String url="http://spider.nitt.edu/~vishnu/time.php";
        new mytask().execute(url);
        looper();

    }
    public void clicked(View v)
    {

        finish();
        System.exit(0);



    }
    public void looper()
    {
        new CountDownTimer( (1000*t+100),1000){
            @Override
            public void onTick(long UntilFinished) {
                timer.setText(String.valueOf(UntilFinished/1000));

            }

            @Override
            public void onFinish() {
                t=s;
                Timer();

            }
        }.start();
    }
    public class mytask extends AsyncTask<String,Integer,Long>
    {
        @Override
        protected Long doInBackground(String... params)
        {
            long t= 0;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                try {
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Accept-Encoding", "identity");
                    InputStreamReader input = new InputStreamReader(conn.getInputStream());
                    BufferedReader in =new BufferedReader(input);
                    String i=in.readLine();
                    Log.i("JS",i);
                    t=Long.parseLong(i);


                }
                finally {
                    conn.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return t;

        }

        @Override
        protected void onPostExecute(Long aLong) {
            num.setText(String.valueOf(aLong));
            s = aLong % 10;
        }
    }

}

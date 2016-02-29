package com.pichanguero.pichangueros;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.pichanguero.pichangueros.R;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;


public class GreetingProce_Activity extends Activity {

    JSONArray ja;
    String data,succ,mensajes;

    Handler h1 = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            try {


                mensajes=(ja.getString(0));
                succ=(ja.getString(1));

                if(succ.equals("capi")) {
                    Toast.makeText(getApplicationContext(), "Cargando...",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(GreetingProce_Activity.this, GreetingCapitanActivity.class);

                    Intent intename = getIntent();
                    String uname = (String) intename.getSerializableExtra("USERNAME");
                    i.putExtra("USERNAME",uname);
                    i.putExtra("MENNUMERO",mensajes);
                    String name = (String) intename.getSerializableExtra("NOMBRE");
                    i.putExtra("NOMBRE", name);

                    startActivity(i);


                }if(succ.equals("juga"))

                {
                    Toast.makeText(getApplicationContext(), "Cargando...",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(GreetingProce_Activity.this, GreetingJugadorActivity.class);
                    Intent intename = getIntent();
                    String uname = (String) intename.getSerializableExtra("USERNAME");
                    i.putExtra("USERNAME",uname);
                    i.putExtra("MENNUMERO",mensajes);
                    String name = (String) intename.getSerializableExtra("NOMBRE");
                    i.putExtra("NOMBRE",name);
                    startActivity(i);

                }if (succ.equals("norm"))
                {
                    Toast.makeText(getApplicationContext(), "Cargando...",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(GreetingProce_Activity.this, GreetingActivity.class);
                    Intent intename = getIntent();
                    String uname = (String) intename.getSerializableExtra("USERNAME");
                    i.putExtra("USERNAME",uname);
                    i.putExtra("MENNUMERO",mensajes);
                    String name = (String) intename.getSerializableExtra("NOMBRE");
                    i.putExtra("NOMBRE",name);
                    startActivity(i);

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }








        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greeting_loading);

        Intent intename = getIntent();
        final String uname2 = (String) intename.getSerializableExtra("USERNAME");






                    new Thread(new Runnable() {


                        public void run() {
                            // TODO Auto-generated method stub
                            ja = null;
                            data = httpGetData("http://10.0.2.2/mail/capitanproceso.php?id="+uname2+"");
                            if (data.length() > 0) {
                                try {
                                    ja = new JSONArray(data);
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }


                                h1.sendEmptyMessage(1);
                            }
                        }
                    }).start();








    }


    public String httpGetData(String mURL){

        String response="";
        mURL= mURL.replace(" ", "%20");
        HttpClient httpclient = new  DefaultHttpClient();
        HttpGet httpget = new HttpGet(mURL);

        ResponseHandler<String> responsehandler = new BasicResponseHandler();
        try {
            response = httpclient.execute(httpget, responsehandler);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String test=response.toString();
        return response;


    }



}
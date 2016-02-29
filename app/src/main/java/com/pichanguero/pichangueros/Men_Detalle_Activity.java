package com.pichanguero.pichangueros;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
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


public class Men_Detalle_Activity extends Activity {
    TextView text1, text2;
    Button eliminar, responder;
    String data,resul;
    JSONArray ja;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.men_detalle);
        eliminar = (Button) findViewById(R.id.eliminar);
        responder = (Button) findViewById(R.id.responder);
        text1 = (TextView) findViewById(R.id.asunto);
        text2 = (TextView) findViewById(R.id.body);


        Intent intename = getIntent();

        final String nombre = (String) intename.getSerializableExtra("NOMBRE2");

        final String centro = (String) intename.getSerializableExtra("CENTRO");
        final String body = (String) intename.getSerializableExtra("BODY");
        final String retador = (String) intename.getSerializableExtra("RETADOR");
        text1.setText("Asunto: "+nombre + " quiere jugar contigo en " + centro);
        text2.setText(body);

        final Handler h = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                try {
                    resul=(ja.getString(1));
                        if (resul.equals("succs")) {
                            Toast.makeText(getApplicationContext(), "Mensaje Eliminado",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Men_Detalle_Activity.this, GreetingProce_Activity.class);
                            Intent intename = getIntent();
                            final String uname = (String) intename.getSerializableExtra("USERNAME");
                            i.putExtra("USERNAME", uname);
                            String name = (String) intename.getSerializableExtra("NOMBRE");
                            i.putExtra("NOMBRE", name);
                            startActivity(i);

                        }else {

                            Toast.makeText(getApplicationContext(), "No se pudo eliminar intente mas tarde",Toast.LENGTH_LONG).show();


                        }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };




        eliminar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intename = getIntent();
                final String id = (String) intename.getSerializableExtra("ID");
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            ja = null;
                            data = httpGetData("http://10.0.2.2/mensaje/eliminar.php?id="+ id +"");
                            if (data.length() > 0) {

                                try {
                                    ja = new JSONArray(data);


                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }


                                h.sendEmptyMessage(1);

                            }
                        }
                    }).start();
                }
        });

        responder.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Men_Respuesta_Activity.class);
                Intent intename = getIntent();
                final String uname = (String) intename.getSerializableExtra("NOMBRE2");
                i.putExtra("NOMBRE2", uname);
                final String id = (String) intename.getSerializableExtra("ID");
                i.putExtra("ID", id);
                final String centro = (String) intename.getSerializableExtra("CENTRO");
                i.putExtra("CENTRO", centro);
                final String body = (String) intename.getSerializableExtra("BODY");
                i.putExtra("BODY", body);
                final String retador = (String) intename.getSerializableExtra("RETADOR");
                i.putExtra("RETADOR", retador);
                final String uname3 = (String) intename.getSerializableExtra("USERNAME");
                i.putExtra("USERNAME", uname3);
                String name = (String) intename.getSerializableExtra("NOMBRE");
                i.putExtra("NOMBRE",name);
                startActivity(i);

            }
        });






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

        return response;


    }

}


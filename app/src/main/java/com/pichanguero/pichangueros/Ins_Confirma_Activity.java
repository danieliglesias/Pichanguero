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


public class Ins_Confirma_Activity extends Activity {
    TextView text1,text2,text3,text4;
    Button confirmar;


    JSONArray ja;
    String data,succ;



    Handler h1 = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            try {



                succ=(ja.getString(1));

                if(succ.equals("succs")) {
                    Toast.makeText(getApplicationContext(), "Creacion De equipo Completa",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Ins_Confirma_Activity.this, GreetingCapitanActivity.class);
                    Intent intename = getIntent();
                    String uname = (String) intename.getSerializableExtra("USERNAME");
                    i.putExtra("USERNAME",uname);
                    String name = (String) intename.getSerializableExtra("NOMBRE");
                    i.putExtra("NOMBRE",name);
                    startActivity(i);

                }if(succ.equals("falta"))
                {


                    Toast.makeText(getApplicationContext(), "Problema con server",Toast.LENGTH_LONG).show();
                }if(succ.equals("leng"))

                {
                    Toast.makeText(getApplicationContext(), "ingresa el numero de digitos requeridos",Toast.LENGTH_LONG).show();

                }if(succ.equals("error"))

                {
                    Toast.makeText(getApplicationContext(), "Problema con servidor",Toast.LENGTH_LONG).show();

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

    };




    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirma_equipo);
        confirmar = (Button)findViewById(R.id.confirmar);
        text1 = (TextView) findViewById(R.id.textView7);
        text2 = (TextView) findViewById(R.id.textView8);
        text3 = (TextView) findViewById(R.id.textView9);
        text4 = (TextView) findViewById(R.id.textView10);

        Intent intename = getIntent();

        final String nombree = (String) intename.getSerializableExtra("NOMBREEQUIPO");

        final String nivel = (String) intename.getSerializableExtra("NIVEL");
        final String nombre = (String) intename.getSerializableExtra("NOMBRE");
        final String nivelid = (String) intename.getSerializableExtra("NIVELID");
        final String mail = (String) intename.getSerializableExtra("USERNAME");
        text1.setText("Nombre Equipo: " + nombree);

        text3.setText("Nivel: " + nivel);
        text4.setText("Capitan: " + nombre);




        confirmar.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {



                    new Thread(new Runnable() {


                        public void run() {
                            // TODO Auto-generated method stub
                            ja = null;
                            data = httpGetData("http://10.0.2.2/inscripcion/confirma2.php?nombre=" + nombree + "&nivelid=" + nivelid + "&mail=" + mail);

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
            String test=response.toString();
            return response;


        }

    }


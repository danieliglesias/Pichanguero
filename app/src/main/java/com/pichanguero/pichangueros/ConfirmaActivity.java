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


public class ConfirmaActivity extends Activity {
    TextView text1,text2,text3,text4,text5;
    Button confirmar;
    String h;

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
                    Toast.makeText(getApplicationContext(), "Reserva exitosa",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ConfirmaActivity.this, GreetingActivity.class);
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
        setContentView(R.layout.arrconfi);
        confirmar = (Button)findViewById(R.id.confirmar);
        text1 = (TextView) findViewById(R.id.textView7);
        text2 = (TextView) findViewById(R.id.textView8);
        text3 = (TextView) findViewById(R.id.textView9);
        text4 = (TextView) findViewById(R.id.textView10);
        text5 = (TextView) findViewById(R.id.textView11);
        Intent intename = getIntent();
        final String hora = (String) intename.getSerializableExtra("HORA");


        if(hora.equals("8") || hora.equals("9") || hora.equals("10")  || hora.equals("11")  || hora.equals("24") )
        {
            h="am";
        }else {
            h = "pm";
        }
        final String uname = (String) intename.getSerializableExtra("USERNAME");
        final String u = (String) intename.getSerializableExtra("CANCHA");
        final String dia = (String) intename.getSerializableExtra("DIA");
        final String centro = (String) intename.getSerializableExtra("CENTRO");
        final String tipo = (String) intename.getSerializableExtra("TIPO");
        final String centroid = (String) intename.getSerializableExtra("CENTROID");
        text1.setText("Centro Dep: " + centro);
        text2.setText("Tipo: " + tipo);
        text3.setText("Numero Cancha: " + u);
        text4.setText("Fecha: " + dia);
        text5.setText("Hora: " + hora + ":00 "+h);



        confirmar.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {


                if (uname != null && hora != null && u != null && dia != null && centroid != null) {
                    // TODO Auto-generated method stub

                    new Thread(new Runnable() {


                        public void run() {
                            // TODO Auto-generated method stub
                            ja = null;
                            data = httpGetData("http://10.0.2.2/arriendo/confirma.php?mail=" + uname + "&hora=" + hora + "&cancha=" + u + "&dia=" + dia + "&centroid=" + centroid);

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
                } else {

                    Toast.makeText(getApplicationContext(), "Servidor no responde", Toast.LENGTH_LONG).show();

                }


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


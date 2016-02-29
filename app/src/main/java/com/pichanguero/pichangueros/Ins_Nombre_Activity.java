package com.pichanguero.pichangueros;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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


public class Ins_Nombre_Activity extends Activity {
    /** Called when the activity is first created. */
    EditText nombre;
    Button btnCon;
    String name,data,names;
    JSONArray ja;
    private static final int LONG_DELAY = 3500; // 3.5 seconds
    private static final int SHORT_DELAY = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nombre_equipo);
        nombre = (EditText) findViewById(R.id.nom);

        btnCon = (Button) findViewById(R.id.btContinuar);




        final Handler h = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                try {


                    names=(ja.getString(1));
                    if(names.equals("3")) {
                        Toast.makeText(getApplicationContext(), "Tienes que ingresar un nombre",Toast.LENGTH_LONG).show();
                    }else
                    {
                        if (names.equals("2"))
                        {
                            Toast.makeText(getApplicationContext(), "El Nombre ya existe, Intenta con otro",Toast.LENGTH_LONG).show();
                        }else
                        {

                            if (names.equals("1")) {
                                String name = nombre.getText().toString();

                                Intent i = new Intent(Ins_Nombre_Activity.this,
                                        Ins_Confirma_Activity.class);
                                i.putExtra("NOMBREEQUIPO", name);
                                Intent intename = getIntent();
                                final String uname = (String) intename.getSerializableExtra("USERNAME");
                                i.putExtra("USERNAME", uname);
                                String names = (String) intename.getSerializableExtra("NOMBRE");
                                i.putExtra("NOMBRE", names);
                                String nivel = (String) intename.getSerializableExtra("NIVEL");
                                i.putExtra("NIVEL", nivel);
                                String nivelid = (String) intename.getSerializableExtra("NIVELID");
                                i.putExtra("NIVELID", nivelid);
                                startActivity(i);


                                overridePendingTransition(R.animator.animation1, R.animator.animation2);
                            }
                        }



                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }




            }



        };










        btnCon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            ja = null;
                            data = httpGetData("http://10.0.2.2/inscripcion/nombre.php?nombre=" + nombre.getText());
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
    }

    // Validar Mail




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

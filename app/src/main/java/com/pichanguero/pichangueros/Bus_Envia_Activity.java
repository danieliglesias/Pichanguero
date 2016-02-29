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


public class Bus_Envia_Activity extends Activity {
    EditText etid;
    TextView bienv;
    Button btn_envia;
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
                    Toast.makeText(getApplicationContext(), "Mensaje enviado exitosamente",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Bus_Envia_Activity.this, GreetingProce_Activity.class);
                    Intent intename = getIntent();
                    String uname = (String) intename.getSerializableExtra("USERNAME");
                    i.putExtra("USERNAME",uname);
                    String name = (String) intename.getSerializableExtra("NOMBRE");
                    i.putExtra("NOMBRE",name);
                    startActivity(i);

                }if(succ.equals("error"))

                {
                    Toast.makeText(getApplicationContext(), "Servidor no responde",Toast.LENGTH_LONG).show();

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
        setContentView(R.layout.envia);
        bienv = (TextView) findViewById(R.id.textView5);
        etid = (EditText)findViewById(R.id.et_id);
        btn_envia = (Button)findViewById(R.id.btn_envia);

        Intent intename = getIntent();
        final String uname = (String) intename.getSerializableExtra("TEAM");
        final String uname3=(String) intename.getSerializableExtra("CENTRO");
        final String uname2 = (String) intename.getSerializableExtra("USERNAME");
        bienv.setText("Asunto:Juguemos en " + uname3);
        final String uname4 = (String) intename.getSerializableExtra("CENTROID");




        btn_envia.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {


                final String email = etid.getText().toString();

                if (email.length() > 5) {
                    // TODO Auto-generated method stub

                    new Thread(new Runnable() {


                        public void run() {
                            // TODO Auto-generated method stub
                            ja = null;
                            data = httpGetData("http://10.0.2.2/mail/send.php?body=" + etid.getText() + "&teamenemy=" + uname + "&mail=" + uname2 + "&centroid=" + uname4);

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

                    Toast.makeText(getApplicationContext(), "minimo 5 caracteres", Toast.LENGTH_LONG).show();

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
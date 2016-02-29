package com.pichanguero.pichangueros;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    EditText nameEditCtrl, passEditCtrl;
    Button btnCtlr,btnRe;
    String data,name,names,passs,bname,pass2,capitan;
    JSONArray ja;
    private static final int LONG_DELAY = 3500; // 3.5 seconds
    private static final int SHORT_DELAY = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        nameEditCtrl = (EditText) findViewById(R.id.user);
        passEditCtrl = (EditText) findViewById(R.id.pass);
        btnCtlr = (Button) findViewById(R.id.button1);
        btnRe = (Button) findViewById(R.id.btRegistro);




        final Handler h = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                try {

                    String name = nameEditCtrl.getText().toString();


                    names=(ja.getString(1));
                    passs=(ja.getString(2));
                    bname=(ja.getString(3));
                    pass2=(ja.getString(4));


                    if((names.equals(name))&& (passs.equals(pass2))) {

                            Intent i = new Intent(MainActivity.this, GreetingProce_Activity.class);

                            i.putExtra("USERNAME",names);
                            i.putExtra("NOMBRE", bname);

                            startActivity(i);




                    }else
                    {


                        Toast.makeText(getApplicationContext(), "Usuario o contrase\u00f1a incorrecta",Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }




            }



        };



        btnRe.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
                overridePendingTransition(R.animator.animation1, R.animator.animation2);
            }
        });








        btnCtlr.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final String email = nameEditCtrl.getText().toString();
                if (!isValidEmail(email)) {
                    nameEditCtrl.setError(Html.fromHtml("<font color='red'>Mail no v\u00e1lido</font>"));
                }

                final String pass = passEditCtrl.getText().toString();
                if (!isValidPassword(pass)) {
                    passEditCtrl.setError(Html.fromHtml("<font color='red'>Contrase\u00f1a no valida</font>"));
                }else {

                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            ja = null;
                            data = httpGetData("http://10.0.2.2/test/consulta.php?id=" + nameEditCtrl.getText()+ "&pass=" + passEditCtrl.getText());
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
            }
        });
    }

    // Validar Mail
    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validando password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 5) {
            return true;
        }
        return false;
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

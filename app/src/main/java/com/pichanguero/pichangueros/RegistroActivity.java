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

public class RegistroActivity extends Activity {
    EditText etid, etnombre, ettelefono, etequipo, etpass1,etpass2;
    Button btn_registrar;
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
                    Toast.makeText(getApplicationContext(), "Registro exitoso",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                    startActivity(intent);

                }if(succ.equals("falta"))
                {


                    Toast.makeText(getApplicationContext(), "falta ingresar digitos",Toast.LENGTH_LONG).show();
                }if(succ.equals("leng"))

                {
                    Toast.makeText(getApplicationContext(), "ingresa el numero de digitos requeridos",Toast.LENGTH_LONG).show();

                }if(succ.equals("error"))

                {
                    Toast.makeText(getApplicationContext(), "Mail en uso",Toast.LENGTH_LONG).show();

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
        setContentView(R.layout.registro);

        etid = (EditText)findViewById(R.id.et_id);
        etnombre = (EditText)findViewById(R.id.et_nombre);
        ettelefono = (EditText)findViewById(R.id.et_fono);

        etpass1 = (EditText)findViewById(R.id.et_pass1);
        etpass2 = (EditText)findViewById(R.id.et_pass2);


        btn_registrar = (Button)findViewById(R.id.btn_registrar);

        btn_registrar.setOnClickListener(new OnClickListener() {






            public void onClick(View v) {
                String passvv = etpass2.getText().toString();
                String passvvv= etpass1.getText().toString();

                String fonos= ettelefono.getText().toString();
                String namev= etnombre.getText().toString();
                String idv= etid.getText().toString();

                final String email = etid.getText().toString();
                if (!isValidEmail(email)) {
                    etid.setError(Html.fromHtml("<font color='red'>Mail no valido</font>"));
                }
                final String nameTest = etnombre.getText().toString();
                if (!isValidName(nameTest)) {
                    etnombre.setError(Html.fromHtml("<font color='red'>Min 2 digitos</font>"));
                }
                final String fono = ettelefono.getText().toString();

                if (!isValidFono(fono)&&!isValidFono2(fono) ) {
                    ettelefono.setError(Html.fromHtml("<font color='red'>min 7 numeros</font>"));
                }


                final String pass = etpass1.getText().toString();
                if (!isValidPassword(pass)) {
                    etpass1.setError(Html.fromHtml("<font color='red'>Min 6 digitos</font>"));
                }
                final String pass2 = etpass2.getText().toString();
               if (!isValidPassword2(pass2)) {
                    etpass2.setError(Html.fromHtml("<font color='red'>Min 6 digitos</font>"));
                }
               if(passvv.equals(passvvv)&& passvv != null && passvv.length() > 5 && fonos!= null && namev!= null && idv != null)
               {
                   // TODO Auto-generated method stub

                   new Thread(new Runnable() {


                       public void run() {
                           // TODO Auto-generated method stub
                           ja = null;
                           data = httpGetData("http://10.0.2.2/test/registro.php?nombre=" + etnombre.getText() + "&tel=" + ettelefono.getText() + "&mail=" + etid.getText() + "&pass=" + etpass1.getText());

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

               else
                 {

                     etpass2.setError(Html.fromHtml("<font color='red'>Contraseñas incorrectas</font>"));

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
    // validando password
    private boolean isValidPassword2(String pass2) {
        if (pass2 != null && pass2.length() > 5) {
            return true;
        }
        return false;
    }

    private boolean isValidName(String nameTest) {
        if (nameTest != null && nameTest.length() > 1) {
            return true;
        }
        return false;
    }


    // validando password
    private boolean isValidFono(String fono) {
        if (fono != null && fono.length() > 6) {
            return true;
        }
        return false;
    }
    private boolean isValidFono2(String fono) {
        Pattern pattern = Pattern.compile(".*[^0-9].*");
        Matcher matcher = pattern.matcher(fono);
        return matcher.matches();
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
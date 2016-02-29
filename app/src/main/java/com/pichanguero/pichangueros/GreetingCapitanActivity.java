package com.pichanguero.pichangueros;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pichanguero.pichangueros.R;


public class GreetingCapitanActivity extends Activity {
    TextView bienv;
    Button btnBus, btnArr, btnAyuda, btnAdm, btnMen ,btnSol;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.greeting_capitan);
        bienv = (TextView) findViewById(R.id.textView1);
        btnBus = (Button) findViewById(R.id.buscar);
        btnArr = (Button) findViewById(R.id.arrendar);
        btnAdm = (Button) findViewById(R.id.administrar);
        btnMen = (Button) findViewById(R.id.mensajes);
        btnAyuda = (Button) findViewById(R.id.ayuda);
        btnSol = (Button) findViewById(R.id.solicitudes);
        Intent intename = getIntent();

        String name = (String) intename.getSerializableExtra("NOMBRE");
        bienv.setText("Bienvenido " + name);
        Toast.makeText(getApplicationContext(), "Bienvenido:  "+name,Toast.LENGTH_LONG).show();


        String nume = (String) intename.getSerializableExtra("MENNUMERO");
        int nume2=Integer.parseInt(nume);
        if(nume2>=1) {
            final int NOTIF_ID = 1234;

            NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification note = new Notification(R.mipmap.ic_launcher, "Nuevo Mensaje", System.currentTimeMillis());

            final PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(), 0);

            note.setLatestEventInfo(this, "Nuevo Mensaje", "Tiene "+ nume2 +" mensajes restantes", intent);

            notifManager.notify(NOTIF_ID, note);
        }

        btnBus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(GreetingCapitanActivity.this, Bus_Centro_Activity.class);
                Intent intename = getIntent();
                String uname = (String) intename.getSerializableExtra("USERNAME");
                i.putExtra("USERNAME", uname);
                String name = (String) intename.getSerializableExtra("NOMBRE");
                i.putExtra("NOMBRE", name);
                startActivity(i);
            }
        });
        btnArr.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(GreetingCapitanActivity.this, ArrendarActivity.class);
                Intent intename = getIntent();
                String uname = (String) intename.getSerializableExtra("USERNAME");
                i.putExtra("USERNAME",uname);
                String name = (String) intename.getSerializableExtra("NOMBRE");
                i.putExtra("NOMBRE",name);
                startActivity(i);
            }
        });
        btnAdm.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(GreetingCapitanActivity.this, AdministrarEquipo_Capitan_Activity.class);
                Intent intename = getIntent();
                String uname = (String) intename.getSerializableExtra("USERNAME");
                i.putExtra("USERNAME",uname);
                String name = (String) intename.getSerializableExtra("NOMBRE");
                i.putExtra("NOMBRE",name);
                startActivity(i);
            }
        });

        btnAyuda.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(GreetingCapitanActivity.this, AyudaActivity.class);
                startActivity(intent);
            }
        });

        btnMen.setOnClickListener(new View.OnClickListener(){

           public void onClick(View v){
               Intent i = new Intent(GreetingCapitanActivity.this, Men_Mismensajes_Activity.class);
               Intent intename = getIntent();
               String uname = (String) intename.getSerializableExtra("USERNAME");
               i.putExtra("USERNAME",uname);
               String name = (String) intename.getSerializableExtra("NOMBRE");
               i.putExtra("NOMBRE",name);
               startActivity(i);
           }

        });
        btnSol.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent i = new Intent(GreetingCapitanActivity.this, Sol_Capitan_Activity.class);
                Intent intename = getIntent();
                String uname = (String) intename.getSerializableExtra("USERNAME");
                i.putExtra("USERNAME",uname);
                String name = (String) intename.getSerializableExtra("NOMBRE");
                i.putExtra("NOMBRE",name);
                startActivity(i);
            }

        });




        };


    }



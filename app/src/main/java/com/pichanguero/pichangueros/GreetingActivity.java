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


public class GreetingActivity extends Activity {
    TextView bienv;
    Button btnBus, btnArr, btnAyuda, btnIns, btnMen,btnSolequipo;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greeting);
        bienv = (TextView) findViewById(R.id.textView1);
        btnBus = (Button) findViewById(R.id.buscar);
        btnArr = (Button) findViewById(R.id.arrendar);
        btnSolequipo = (Button) findViewById(R.id.buscarequipo);
        btnIns = (Button) findViewById(R.id.inscribir);
        btnMen = (Button) findViewById(R.id.mensajes);
        btnAyuda = (Button) findViewById(R.id.ayuda);
        Intent intename = getIntent();
        String name = (String) intename.getSerializableExtra("NOMBRE");
        bienv.setText("Bienvenido " + name);
        Toast.makeText(getApplicationContext(), "Bienvenido:  "+name,Toast.LENGTH_LONG).show();



        final int NOTIF_ID = 1234;

        NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification note = new Notification(R.mipmap.ic_launcher, "Nuevo Mensaje", System.currentTimeMillis());

        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, GreetingActivity.class), 0);

        note.setLatestEventInfo(this, "Nuevo Mensaje", "Revise Los mensajes de PichangApp", intent);

        notifManager.notify(NOTIF_ID, note);


        btnBus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(GreetingActivity.this, Bus_Centro_Activity.class);
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
                Intent i = new Intent(GreetingActivity.this, ArrendarActivity.class);
                Intent intename = getIntent();
                String uname = (String) intename.getSerializableExtra("USERNAME");
                i.putExtra("USERNAME",uname);
                String name = (String) intename.getSerializableExtra("NOMBRE");
                i.putExtra("NOMBRE",name);
                startActivity(i);
            }
        });
        btnIns.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(GreetingActivity.this, Ins_Nivel_Activity.class);
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
                Intent intent = new Intent(GreetingActivity.this, AyudaActivity.class);
                startActivity(intent);
            }
        });
        btnMen.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent i = new Intent(GreetingActivity.this, Men_Mismensajes_Activity.class);
                Intent intename = getIntent();
                String uname = (String) intename.getSerializableExtra("USERNAME");
                i.putExtra("USERNAME",uname);
                String name = (String) intename.getSerializableExtra("NOMBRE");
                i.putExtra("NOMBRE",name);
                startActivity(i);
            }

        });
        btnSolequipo.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent i = new Intent(GreetingActivity.this, Sol_Equipo_Activity.class);
                Intent intename = getIntent();
                String uname = (String) intename.getSerializableExtra("USERNAME");
                i.putExtra("USERNAME",uname);
                String name = (String) intename.getSerializableExtra("NOMBRE");
                i.putExtra("NOMBRE",name);
                startActivity(i);
            }

        });





    }

}

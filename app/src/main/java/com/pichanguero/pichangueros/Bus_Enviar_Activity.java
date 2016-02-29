package com.pichanguero.pichangueros;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pichanguero.pichangueros.R;


/**
 * Created by Walffen on 27/06/2015.
 */
public class Bus_Enviar_Activity extends Activity {

    TextView bienv;
    Button btn_enviar;












    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enviar);
        bienv = (TextView) findViewById(R.id.textView1);


        Intent intename = getIntent();
        final String uname = (String) intename.getSerializableExtra("TEAM");
        final String uname2 = (String) intename.getSerializableExtra("USERNAME");
        bienv.setText("Seguro desea retar a :" + uname);
       /* Toast.makeText(getApplicationContext(), "Enviar a  :" + uname, Toast.LENGTH_LONG).show();*/
        btn_enviar = (Button)findViewById(R.id.btn_enviar);


        btn_enviar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(Bus_Enviar_Activity.this, Bus_Envia_Activity.class);

                i.putExtra("TEAM",uname);
                i.putExtra("USERNAME",uname2);
                Intent intename = getIntent();
                final String cen = (String) intename.getSerializableExtra("CENTRO");
                i.putExtra("CENTRO", cen);
                final String con2 = (String) intename.getSerializableExtra("CENTROID");
                i.putExtra("CENTROID", con2);
                String name = (String) intename.getSerializableExtra("NOMBRE");
                i.putExtra("NOMBRE",name);

                startActivity(i);
            }
        });






    }


}

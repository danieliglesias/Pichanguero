package com.pichanguero.pichangueros;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.pichanguero.pichangueros.R;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Sol_Capitan_Activity extends ListActivity {
    // Connection detector




    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();

    ArrayList<HashMap<String, String>> albumsList;

    // albums JSONArray
    JSONArray albums = null;


    // ALL JSON node names
    private static final String TAG_ID = "Men_Id";
    private static final String TAG_NAME = "Usu_Nombre";
    private static final String TAG_BODY = "Men_Msj";
    private static final String TAG_SOL = "Id_Sol";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar);


        // Hashmap for ListView
        albumsList = new ArrayList<HashMap<String, String>>();

        // Loading Albums JSON in Background Thread
        new LoadAlbums().execute();

        // get listview
        ListView lv = getListView();

        /**
         * Listview item click listener
         * TrackListActivity will be lauched by passing album id
         * */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {
                // on selecting a single album
                // TrackListActivity will be launched to show tracks inside the album
                Intent i = new Intent(getApplicationContext(), Sol_CapitanAcepta_Activity.class);

                String album_name = ((TextView) view.findViewById(R.id.album_name)).getText().toString();
                String album_id = ((TextView) view.findViewById(R.id.album_id)).getText().toString();

                String album_body = ((TextView) view.findViewById(R.id.album_body)).getText().toString();
                String album_sol = ((TextView) view.findViewById(R.id.album_sol)).getText().toString();
                i.putExtra("NOMBRE2", album_name);
                i.putExtra("IDMEN", album_id);

                i.putExtra("BODY", album_body);
                i.putExtra("IDSOL", album_sol);

                Intent intename = getIntent();


                final String uname = (String) intename.getSerializableExtra("USERNAME");
                i.putExtra("USERNAME", uname);
                String name = (String) intename.getSerializableExtra("NOMBRE");
                i.putExtra("NOMBRE",name);
                startActivity(i);

            }
        });
    }

    /**
     * Background Async Task to Load all Albums by making http request
     * */
    class LoadAlbums extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Sol_Capitan_Activity.this);
            pDialog.setMessage("Cargando ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Albums JSON
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            Intent intename = getIntent();
            final String uname = (String) intename.getSerializableExtra("USERNAME");

            // getting JSON string from URL
            String json = httpGetData("http://10.0.2.2/equipo/solcapitan.php?mail="+uname);

            // Check your log cat for JSON reponse
            Log.d("Albums JSON: ", "> " + json);

            try {
                albums = new JSONArray(json);

                if (albums != null) {
                    // looping through All albums
                    for (int i = 0; i < albums.length(); i++) {
                        JSONObject c = albums.getJSONObject(i);

                        // Storing each json item values in variable
                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String body = c.getString(TAG_BODY);

                        String sol = c.getString(TAG_SOL);


                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, id);
                        map.put(TAG_NAME, "Solicitud de: "+name);
                        map.put(TAG_BODY, body);
                        map.put(TAG_SOL, sol);


                        // adding HashList to ArrayList
                        albumsList.add(map);
                    }
                }else{
                    Log.d("Albums: ", "null");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        public String httpGetData(String mURL){

            String response="";
            mURL= mURL.replace(" ", "%20");
            HttpClient httpclient = new DefaultHttpClient();
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
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all albums
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            Sol_Capitan_Activity.this, albumsList,
                            R.layout.list_sol, new String[] { TAG_ID,
                            TAG_NAME,TAG_BODY,TAG_SOL }, new int[] {
                            R.id.album_id, R.id.album_name,R.id.album_body,R.id.album_sol});

                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}

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
import android.widget.Toast;

import com.pichanguero.pichangueros.R;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CanchaActivity extends ListActivity {
    // Connection detector




    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();

    ArrayList<HashMap<String, String>> albumsList;

    // albums JSONArray
    JSONArray albums = null;

    // albums JSON url


    // ALL JSON node names
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "Cancha";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar);
        Intent intename = getIntent();
        final String tipo = (String) intename.getSerializableExtra("TIPO");
        Toast.makeText(getApplicationContext(), "Canchas de "+tipo,Toast.LENGTH_LONG).show();


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
                Intent i = new Intent(getApplicationContext(), HoraActivity.class);

                // send album id to tracklist activity to get list of songs under that album
                String album_id = ((TextView) view.findViewById(R.id.album_id)).getText().toString();
                i.putExtra("CANCHA", album_id);
                Intent intename = getIntent();

                final String uname = (String) intename.getSerializableExtra("USERNAME");
                i.putExtra("USERNAME", uname);

                final String dia = (String) intename.getSerializableExtra("DIA");
                i.putExtra("DIA", dia);

                final String centro = (String) intename.getSerializableExtra("CENTRO");
                i.putExtra("CENTRO", centro);

                final String tipo = (String) intename.getSerializableExtra("TIPO");
                i.putExtra("TIPO", tipo);

                final String tipoid = (String) intename.getSerializableExtra("TIPOID");
                i.putExtra("TIPOID", tipoid);

                final String centroid = (String) intename.getSerializableExtra("CENTROID");
                i.putExtra("CENTROID", centroid);

                String name = (String) intename.getSerializableExtra("NOMBRE");
                i.putExtra("NOMBRE", name);

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
            pDialog = new ProgressDialog(CanchaActivity.this);
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
            final String centroid = (String) intename.getSerializableExtra("CENTROID");
            final String tipoid = (String) intename.getSerializableExtra("TIPOID");

            final String URL_ALBUMS = "http://10.0.2.2/arriendo/cancha.php?centroid="+ centroid + "&tipoid=" + tipoid;
            // getting JSON string from URL
            String json = jsonParser.makeHttpRequest(URL_ALBUMS, "GET",
                    params);

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


                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, id);
                        map.put(TAG_NAME, name);


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
                            CanchaActivity.this, albumsList,
                            R.layout.list_cancha, new String[] { TAG_ID,
                            TAG_NAME }, new int[] {
                            R.id.album_id, R.id.album_name});

                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }


}
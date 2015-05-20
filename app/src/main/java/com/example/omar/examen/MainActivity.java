package com.example.omar.examen;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.omar.examen.ui.CustomAdapter;
import com.example.omar.examen.ui.WSTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import com.example.omar.examen.database.*;

public class MainActivity extends ActionBarActivity {

    DatabaseHandler dbHandler;

    private EditText searchField;
    private Button button1;
    private ListView lvData;
    private MyAsync async;
    private String url = "https://maps.googleapis.com/maps/api/place/search/json?location=37.787930,-122.4074990&radius=1000&sensor=false&key=AIzaSyByw-OOceD3soavFqo315PR3FBtkl2WhxI";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        dbHandler = new DatabaseHandler(MainActivity.this);

        if(dbHandler.countRecords()> 0){
            Toast.makeText(MainActivity.this,"REGISTROS en Base" + dbHandler.countRecords(), Toast.LENGTH_LONG).show();
        }


        initViews(this);
    }

    private void initViews(Context context) {

        searchField = (EditText) findViewById(R.id.search);
        button1 = (Button) findViewById(R.id.button1);
        lvData = (ListView) findViewById(R.id.listView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                async = new MyAsync();
                async.execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyAsync extends AsyncTask<String,Void,Void>{

        DatabaseHandler dbHand;

        String temp;
        ArrayList<POJO> resul = new ArrayList();

        @Override
        protected Void doInBackground(String... params) {
            WSTest ws = new WSTest();
            temp = ws.makeCall(url);

            if(temp != null) {
                dbHand = new DatabaseHandler(MainActivity.this);

                resul = parseGoogleParse(temp);

                dbHand.insertFast(resul.size());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(resul != null){

                CustomAdapter adapter = new CustomAdapter(context, resul);

                lvData.setAdapter(adapter);
            }

        }

        private ArrayList parseGoogleParse(final String response) {

            ArrayList resultList = new ArrayList();
            try {

                // Create a JSON object hierarchy from the results

                JSONObject jsonObj = new JSONObject(response);

                JSONArray predsJsonArray = jsonObj.getJSONArray("results");



                // Extract the Place descriptions from the results

                resultList = new ArrayList(predsJsonArray.length());

                for (int i = 0; i < predsJsonArray.length(); i++) {

                    System.out.println(predsJsonArray.getJSONObject(i).getString("name"));

                    System.out.println("============================================================");

                    //resultList.add(predsJsonArray.getJSONObject(i).getString("name"));
                       resultList.add(new POJO(predsJsonArray.getJSONObject(i).getString("id"),
                               predsJsonArray.getJSONObject(i).getString("name"),
                               predsJsonArray.getJSONObject(i).getString("vicinity")));
                }

            } catch (JSONException e) {

                Log.e("JSONERROR", "Cannot process JSON results", e);

            }

            return resultList;

        }
    }
}

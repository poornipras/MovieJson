package com.pooja.moviejson;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.pooja.moviejson.Adapter.Myadapter;
import com.pooja.moviejson.Model.MyData;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    RecyclerView recyclerView;
    List<MyData> model=new ArrayList<>();
    ConnectivityManager connectivityManager;
    boolean status=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        textView= (TextView) findViewById(R.id.text_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.load,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.load)
        {
            MyAsyncTask myAsyncTask=new MyAsyncTask();
            myAsyncTask.execute("http://api.themoviedb.org/3/tv/top_rated?api_key=8496be0b2149805afa458ab8ec27560c");

            }

        return super.onOptionsItemSelected(item);
    }
public boolean connectionstatus()
{
    try {
        connectivityManager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        status = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        return true;
        } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(MainActivity.this,"Check Connectivity Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }return false;
}

    class MyAsyncTask extends AsyncTask<String,Void,List<MyData>>
    {
        @Override
        protected void onPreExecute() {

            boolean status=connectionstatus();
            if(status==true)
            {
                Toast.makeText(MainActivity.this,"Connected", Toast.LENGTH_SHORT).show();
            }else if(status==false){
                Toast.makeText(MainActivity.this,"Not connected", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected List<MyData> doInBackground(String... strings) {

            HttpURLConnection connection=null;
            InputStream inputStream=null;
            BufferedReader reader=null;
            try {
                URL url=new URL(strings[0]);
                connection= (HttpURLConnection) url.openConnection();
                connection.connect();
                inputStream=connection.getInputStream();
                reader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer=new StringBuffer();
                String line="";
                while((line=reader.readLine())!=null)
                {
                   buffer.append(line);
                }
                String json=buffer.toString();
                JSONObject jsonObject=new JSONObject(json);
                JSONArray resultarray=jsonObject.getJSONArray("results");
               for(int i=0;i<resultarray.length();i++) {
                    MyData myData = new MyData();
                    myData.setName(resultarray.getJSONObject(i).getString("name"));
                    myData.setId(resultarray.getJSONObject(i).getInt("id"));
                    myData.setVote_count(resultarray.getJSONObject(i).getInt("vote_count"));
                    model.add(myData);
                }

                return model;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
              e.printStackTrace();
            }
            finally {
                if(connection!=null)
                {
                    connection.disconnect();
                }
                if(reader!=null)
                {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<MyData> model) {
            super.onPostExecute(model);
            if(model!=null)
            {
                Toast.makeText(MainActivity.this,"Displaying items"+model.size(),Toast.LENGTH_LONG).show();
                Myadapter myadapter=new Myadapter(MainActivity.this,model);
                recyclerView.setAdapter(myadapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }else
            {

                Toast.makeText(MainActivity.this,"Nothing to display!!!",Toast.LENGTH_LONG).show();
            }

            }

        }

    }



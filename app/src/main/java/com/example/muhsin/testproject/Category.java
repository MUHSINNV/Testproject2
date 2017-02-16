package com.example.muhsin.testproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MUHSIN on 2/14/2017.
 */

public class Category extends AppCompatActivity {

    public static final String ROOT_URL = "http://192.168.43.5/";
    //List view to show data
    private ListView listview;
    private String categoryidvalue;
    SharedPreferences pref ;
    private String values;
    String id ,name;
    List<String> id_m;
    List<String> listDataHeader;
    Customlist customList;
    GridView gv;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catergory_activity);
        pref = getApplicationContext().getSharedPreferences(MainActivity.MyPREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        categoryidvalue = pref.getString("idvalue", null);
        //Initializing the listview
        listview = (ListView) findViewById(R.id.listViewCategory);

        getCategory();
        //Setting onItemClickListener to listview
        //listview.setOnItemClickListener(this);

    }
    private void getCategory(){
        final RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        RetrofitApi api = adapter.create(RetrofitApi.class);


        api.getCategory(

                //Passing the values by getting it from editTexts

                categoryidvalue,


                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader = null;


                        //An string to store output from the server
                        String output = "";
                        id_m = new ArrayList<String>();
                        listDataHeader = new ArrayList<String>();
                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                            try{

                                //ArrayList<String> stringArray = new ArrayList<String>();
                                //ArrayList<String> idarray = new ArrayList<String>();
                                JSONObject jo=new JSONObject(output);
                                //Log.e("Test",jo.toString());
                                JSONArray ja = jo.getJSONArray("result");
                                for (int i=0;i<ja.length();i++){

                                    JSONObject jobj = ja.getJSONObject(i);
                                    //idd =jobj.getInt("id");
                                   //id = jobj.getString("id").toString();
                                    id=(jobj.getString("id"));
                                    //output =jobj.getString("name");
                                    name=(jobj.getString("name"));
                                    listDataHeader.add(name);
                                    id_m.add(id);

                                    customList = new Customlist(Category.this, id_m , listDataHeader);

                                }
                                listview.setAdapter(customList);
                                //gv.setAdapter(customList);
                            }
                            catch (JSONException j){
                                j.printStackTrace();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                        Toast.makeText(Category.this, output, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(Category.this, error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        );
    }



}




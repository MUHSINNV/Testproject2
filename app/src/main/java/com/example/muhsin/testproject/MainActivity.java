package com.example.muhsin.testproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Output;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    public String first="";
    public static final String idvalue = "idno";
    public static final String MyPREFERENCES = "MyPrefs" ;

    public static final String ROOT_URL = "http://192.168.43.5/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin= (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(this);

    }
    private void LoginUser(){
        final RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        RetrofitApi api = adapter.create(RetrofitApi.class);


    api.LoginUser(

            //Passing the values by getting it from editTexts

            editTextUsername.getText().toString(),
            editTextPassword.getText().toString(),


    //Creating an anonymous callback
    new Callback<Response>() {
        @Override
        public void success(Response result, Response response) {
            //On success we will read the server's output using bufferedreader
            //Creating a bufferedreader object
            BufferedReader reader = null;

            //An string to store output from the server
            String output = "";

            try {
                //Initializing buffered reader
                reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                //Reading the output in the string
                output = reader.readLine();
                try{
                    JSONObject jo=new JSONObject(output);
                    //Log.e("Test",jo.toString());
                    JSONArray ja = jo.getJSONArray("result");
                    for (int i=0;i<ja.length();i++){

                        JSONObject jobj = ja.getJSONObject(i);
                         //idd =jobj.getInt("id");
                            first = jobj.getString("id").toString();
                         output =jobj.getString("name");

                        }

                    }
                catch (JSONException j){
                    j.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            //Displaying the output as a toast
            //Toast.makeText(MainActivity.this, output, Toast.LENGTH_LONG).show();
           if (output.equals(editTextUsername.getText().toString()))
            {
                //Intent i = new Intent(getApplicationContext(), Category.class);
               //startActivity(i);
                //Toast.makeText(MainActivity.this, first, Toast.LENGTH_LONG).show();
                //Toast.makeText(MainActivity.this, first , Toast.LENGTH_LONG).show();
                SharedPreferences pref = getApplicationContext().getSharedPreferences(MyPREFERENCES, 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("idvalue",first);
                editor.commit();
                Intent i = new Intent(getApplicationContext(), Category.class);
                startActivity(i);

            }
            else
            {
                Toast.makeText(MainActivity.this, output , Toast.LENGTH_LONG).show();

            }
        }

        @Override
        public void failure(RetrofitError error) {
            //If any error occured displaying the error as toast
            Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_LONG).show();
        }
    }
    );
}
    @Override
    public void onClick(View v) {
        //Calling insertUser on button click
        LoginUser();
    }

}

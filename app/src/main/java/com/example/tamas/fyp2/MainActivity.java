package com.example.tamas.fyp2;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

final String LOG = "MainActivity";
    Button btnLogin;
    EditText etUsername, etPassword;
    TextView tvRegisterLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
       tvRegisterLink.setOnClickListener(this);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);





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

    @Override
    public void onClick(View v) {
       switch(v.getId()) {
         case R.id.tvRegisterLink:
              startActivity(new Intent(this, RegisterActivity.class));
             break;
       }
        HashMap postData = new HashMap();

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();


        postData.put("txt_username", username);
        postData.put("txt_password", password);

        String url = ("http://59.149.5.237/fyp.local/FYP/ci/index.php/api/User/login");



        PostResponseAsyncTask task1 = new PostResponseAsyncTask(MainActivity.this, new AsyncResponse() {
            @Override

            public void processFinish(String s) {

                HashMap postData = new HashMap();


                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                postData.put("txt_username", username);
                postData.put("txt_password", password);
                String url = ("http://59.149.5.237/fyp.local/FYP/ci/index.php/api/User/login");

                List<NameValuePair> paramList = new ArrayList<NameValuePair>(2);
              //  BasicNameValuePair param = new BasicNameValuePair("param1",paramValue);
               // paramList.add(param);
                paramList.add(new BasicNameValuePair("txt_username", username));
                paramList.add(new BasicNameValuePair("txt_password", password));


                try {


                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);
                    post.setEntity(new UrlEncodedFormEntity(paramList));
                    HttpResponse httpResponse = httpClient.execute(post);

                    HttpEntity resEntity = httpResponse.getEntity();

                    String result;

                    if (resEntity != null) {
                        result = EntityUtils.toString(resEntity);
                        Log.d(LOG, result);
                    }
                    Log.d(LOG, username);
                    Log.d(LOG, password);
                    //Log.d("YourTag", "YourOutput");
                    if(httpResponse.getStatusLine().getStatusCode() == 200) {

                        Toast.makeText(MainActivity.this, "Sucessfully Login", Toast.LENGTH_LONG).show();
                        Intent in = new Intent();
                        in.setClass(MainActivity.this, ListActivity.class);
                        startActivity(in);
                    }
                    else if(httpResponse.getStatusLine().getStatusCode() == 400){
                        Toast.makeText(MainActivity.this, "Try Again", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Try Again", Toast.LENGTH_LONG).show();

                    } Log.d(LOG, httpResponse.toString());
                }
                catch(Exception e){
                 //   Log.d("23gdfgfd", "54654654");
                  //  e.printStackTrace();
                }


            }
        });
        task1.execute(url);

    }
}

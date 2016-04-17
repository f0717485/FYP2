package com.example.tamas.fyp2;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

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

public class RegisterActivity extends ActionBarActivity implements View.OnClickListener {
final String LOG = "RegisterActivity";
    Button btnEnter;
    EditText etUsername, etPassword, etCpassword, etFname, etLname, etEmail, etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etCpassword = (EditText)findViewById(R.id.etCpassword);
        etFname = (EditText)findViewById(R.id.etFname);
        etLname = (EditText)findViewById(R.id.etLname);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPhone = (EditText)findViewById(R.id.etPhone);
        btnEnter = (Button)findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(this);
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
        String cpassword = etCpassword.getText().toString();
        String fname = etFname.getText().toString();
        String lname = etLname.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();


        postData.put("username", username);
        postData.put("password", password);
        postData.put("cpassword", cpassword);
        postData.put("fname", fname);
        postData.put("lname", lname);
        postData.put("email", email);
        postData.put("phone", phone);

        String url = ("http://59.149.5.237/fyp.local/FYP//ci/index.php/api/User/reg");
        PostResponseAsyncTask task1 = new PostResponseAsyncTask(RegisterActivity.this, new AsyncResponse() {
            @Override

            public void processFinish(String s) {

                HashMap postData = new HashMap();


                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String cpassword = etCpassword.getText().toString();
                String fname = etFname.getText().toString();
                String lname = etLname.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();


                postData.put("username", username);
                postData.put("password", password);
                postData.put("cpassword", cpassword);
                postData.put("fname", fname);
                postData.put("lname", lname);
                postData.put("email", email);
                postData.put("phone", phone);
                String url = ("http://59.149.5.237/fyp.local/FYP//ci/index.php/api/User/reg");

                List<NameValuePair> paramList = new ArrayList<NameValuePair>(2);
                //  BasicNameValuePair param = new BasicNameValuePair("param1",paramValue);
                // paramList.add(param);
                paramList.add(new BasicNameValuePair("username", username));
                paramList.add(new BasicNameValuePair("password", password));
                paramList.add(new BasicNameValuePair("cpassword", cpassword));
                paramList.add(new BasicNameValuePair("fname", fname));
                paramList.add(new BasicNameValuePair("lname", lname));
                paramList.add(new BasicNameValuePair("email", email));
                paramList.add(new BasicNameValuePair("phone", phone));
                try {


                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);
                    post.setEntity(new UrlEncodedFormEntity(paramList));
                    HttpResponse httpResponse = httpClient.execute(post);
                    String result;


                    //Log.d("YourTag", "YourOutput");
                    if(httpResponse.getStatusLine().getStatusCode() == 200) {

                        Toast.makeText(RegisterActivity.this, "Sucessfully Register", Toast.LENGTH_LONG).show();
                        Intent in = new Intent();
                        in.setClass(RegisterActivity.this, ListActivity.class);
                        startActivity(in);
                    }
                    else if(httpResponse.getStatusLine().getStatusCode() == 400){
                        Toast.makeText(RegisterActivity.this, "Try Again", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Sucessfully Register", Toast.LENGTH_LONG).show();
                        Intent in = new Intent();
                        in.setClass(RegisterActivity.this, MainActivity.class);
                        startActivity(in);
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


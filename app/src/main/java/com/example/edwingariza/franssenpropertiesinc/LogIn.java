package com.example.edwingariza.franssenpropertiesinc;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        final EditText USER_NAME = (EditText) findViewById(R.id.enter_username);
        final EditText USER_PASS= (EditText) findViewById(R.id.enter_pass);
        final Button LOGIN = (Button) findViewById(R.id.logIn_btn);
        final Button CREATE_BTN = (Button) findViewById(R.id.create_acc_btn);

        CREATE_BTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, Register.class);
                LogIn.this.startActivity(intent);
            }

        });

        LOGIN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String username = USER_NAME.getText().toString();
                final String password = USER_PASS.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                String  name = jsonResponse.getString("name");
                                String  unitNum = jsonResponse.getString("unitNum");

                                Intent intent = new Intent(LogIn.this, Tenant.class);
                                intent.putExtra("name", name);
                                intent.putExtra("unitNum", unitNum);

                                LogIn.this.startActivity(intent);

                            }
                            else{
                                AlertDialog.Builder  builder = new AlertDialog.Builder(LogIn.this);
                                builder.setMessage("Login failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LogIn.this);
                queue.add(loginRequest);
            }

        });

    }



}

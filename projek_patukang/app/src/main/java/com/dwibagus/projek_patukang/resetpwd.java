package com.dwibagus.projek_patukang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dwibagus.projek_patukang.Api.Url;
import com.dwibagus.projek_patukang.Session.SessionManager;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class resetpwd extends AppCompatActivity {

    TextInputLayout password,email,token;
    SessionManager sessionManager;

    private RequestQueue queue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpwd);

        queue = Volley.newRequestQueue(resetpwd.this);
        sessionManager = new SessionManager(resetpwd.this);

        email = findViewById(R.id.email);
        token = findViewById(R.id.token);
        password = findViewById(R.id.password);

        Button masuk = findViewById(R.id.masuk);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInputLogin();
            }
        });

    }


    private boolean validateEmail() {
        String emailInput = email.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            email.setError("Email harus diisi");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateToken() {
        String tokenInput = token.getEditText().getText().toString().trim();

        if (tokenInput.isEmpty()) {
            token.setError("Token harus diisi");
            return false;
        } else {
            token.setError(null);
            token.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = password.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            password.setError("Password harus diisi");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private void Reset(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_GANTI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("stat");

                            if (status.equals("false")) {
                                Toast.makeText(resetpwd.this,"Reset Passwword Gagal", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(resetpwd.this,"Reset Passwrod Sukses", Toast.LENGTH_SHORT).show();
                                Intent main = new Intent(resetpwd.this, MainActivity.class);
                                startActivity(main);
                                finish();


                            }
                        } catch (Exception e) {
                            Toast.makeText(resetpwd.this,"Error\n"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(resetpwd.this,"eror \n"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",email.getEditText().getText().toString().trim());
                params.put("token",token.getEditText().getText().toString().trim());
                params.put("password",password.getEditText().getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void confirmInputLogin() {
        if (validateToken() && validateEmail() && validatePassword()) {
            Reset();
        }
    }
}
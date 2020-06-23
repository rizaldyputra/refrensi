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


public class lupapwd extends AppCompatActivity {

    TextInputLayout email;
    SessionManager sessionManager;

    private RequestQueue queue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupapwd);

        queue = Volley.newRequestQueue(lupapwd.this);
        sessionManager = new SessionManager(lupapwd.this);

        email = findViewById(R.id.email);

        Button masuk = findViewById(R.id.kirim);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInput();
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


    private void reset(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_RESET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            if (status.equals("true")) {
                                Toast.makeText(lupapwd.this,"Cek Email anda", Toast.LENGTH_SHORT).show();
                                Intent main = new Intent(lupapwd.this, resetpwd.class);
                                startActivity(main);
                                finish();
                            } else {

                                Toast.makeText(lupapwd.this,"Reset Password Gagal", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(lupapwd.this,"Error\n"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(lupapwd.this,"Login eror ?\n"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",email.getEditText().getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void confirmInput() {
        if (validateEmail()) {
            reset();
        }
    }
}
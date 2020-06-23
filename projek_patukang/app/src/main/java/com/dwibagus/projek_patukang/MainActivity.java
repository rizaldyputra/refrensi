package com.dwibagus.projek_patukang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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


public class MainActivity extends AppCompatActivity {

    TextInputLayout password,email;
    SessionManager sessionManager;

    private RequestQueue queue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(MainActivity.this);
        sessionManager = new SessionManager(MainActivity.this);
        String id = sessionManager.getIdAkun();
        if (id!=null){
            Intent main = new Intent(MainActivity.this,beranda.class);
            startActivity(main);
            finish();
        }


        Button daftar = findViewById(R.id.daftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, daftar.class));

            }
        });


        TextView frgot = findViewById(R.id.frgt);
        frgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, lupapwd.class));

            }
        });

        email = findViewById(R.id.email);
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

    private void Login(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");

                            if (status.equals("false")) {
                                Toast.makeText(MainActivity.this,"Login gagal"+message.toString(), Toast.LENGTH_SHORT).show();
                            } else {
                                JSONObject data = jsonObject.getJSONObject("data");

                                String id_customer = data.getString("id_customer");
                                String nam_leng = data.getString("nam_leng");
                                String foto_diri = data.getString("foto_diri");
                                String statustukang = data.getString("status_tukang");

                                sessionManager.createSession(nam_leng,statustukang,id_customer,foto_diri);

                                Toast.makeText(MainActivity.this,"Login berhasil", Toast.LENGTH_SHORT).show();
                                Intent main = new Intent(MainActivity.this, beranda.class);
                                startActivity(main);
                                finish();


                            }
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this,"Error\n"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Login eror ?\n"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",email.getEditText().getText().toString().trim());
                params.put("password",password.getEditText().getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void confirmInputLogin() {
        if (validateEmail() && validatePassword()) {
            Login();
        }
    }
}
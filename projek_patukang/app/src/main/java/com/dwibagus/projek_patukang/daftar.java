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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class daftar extends AppCompatActivity {

    TextInputLayout passwordr,emailr,nam_lengr,alamatr,no_telpr;

    private RequestQueue queue;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        queue = Volley.newRequestQueue(daftar.this);

        nam_lengr = findViewById(R.id.nam_lengr);
        no_telpr = findViewById(R.id.no_telpr);
        alamatr = findViewById(R.id.alamatr);
        emailr = findViewById(R.id.emailr);
        passwordr = findViewById(R.id.passwordr);

        Button daftar = findViewById(R.id.daftarr);
        daftar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                confirmInputRegister();
            }
        });

    }
    private boolean validateNama() {
        String nam_leng = nam_lengr.getEditText().getText().toString().trim();

        if (nam_leng.isEmpty()) {
            nam_lengr.setError("Nama harus diisi");
            return false;
        } else {
            nam_lengr.setError(null);
            nam_lengr.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateAlamat() {
        String alamat = alamatr.getEditText().getText().toString().trim();

        if (alamat.isEmpty()) {
            alamatr.setError("Alamat harus diisi");
            return false;
        } else {
            alamatr.setError(null);
            alamatr.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateNotelp() {
        String no_telp = no_telpr.getEditText().getText().toString().trim();

        if (no_telp.isEmpty()) {
            no_telpr.setError("No telepon harus diisi");
            return false;
        } else {
            no_telpr.setError(null);
            no_telpr.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String email = emailr.getEditText().getText().toString().trim();

        if (email.isEmpty()) {
            emailr.setError("Email harus diisi");
            return false;
        } else {
            emailr.setError(null);
            emailr.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword() {
        String password = passwordr.getEditText().getText().toString().trim();

        if (password.isEmpty()) {
            passwordr.setError("Password harus diisi");
            return false;
        } else {
            passwordr.setError(null);
            passwordr.setErrorEnabled(false);
            return true;
        }
    }

    private void registerProcess() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("fail")) {
                        Toast.makeText(daftar.this, "Error " +message, Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(daftar.this,"Daftar berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(daftar.this, MainActivity.class);
                        startActivity(intent);

                    }
                } catch (Exception e) {
                    Toast.makeText(daftar.this,e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(daftar.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nam_leng", nam_lengr.getEditText().getText().toString().trim());
                params.put("no_telp", no_telpr.getEditText().getText().toString().trim());
                params.put("alamat", alamatr.getEditText().getText().toString().trim());
                params.put("email", emailr.getEditText().getText().toString().trim());
                params.put("password", passwordr.getEditText().getText().toString().trim());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        queue.add(stringRequest);
    }

    public void confirmInputRegister() {
        if ( validateNama() && validateNotelp() && validateAlamat() && validateEmail() && validatePassword()) {
            registerProcess();
        }
    }
}

package com.dwibagus.projek_patukang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dwibagus.projek_patukang.Api.Url;
import com.dwibagus.projek_patukang.Session.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class beri_rating extends AppCompatActivity {

    SessionManager sessionManager;
    private RequestQueue queue;
    ProgressDialog progressDialog;
    private AppCompatRatingBar RatingBar;
    TextView tukang, getRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beri_rating);
        queue = Volley.newRequestQueue(this);
        sessionManager = new SessionManager(this);

        getRating = findViewById(R.id.rat);
        RatingBar = findViewById(R.id.penilaian);
        getRating.setVisibility(View.GONE);

        tukang = findViewById(R.id.idtuk);
        tukang.setVisibility(View.GONE );

        ActionBar actionBar;
        actionBar = getSupportActionBar();assert actionBar != null;
        actionBar.setTitle("Rating Tukang");

        view();

        RatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(android.widget.RatingBar ratingBar, float rating, boolean fromUser) {
                getRating.setText(String.valueOf(rating));
            }
        });

        Button button = findViewById(R.id.btn_rating);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateRating();
            }
        });
    }


    private void view() {
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id_tukang");
        sessionManager = new SessionManager(beri_rating.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_TAMPIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject datacus = jsonObject.getJSONObject("cus");
                    JSONObject datatuk = jsonObject.getJSONObject("tukang");

                    String namasewa = datacus.getString("nam_leng");
                    String fototuk = datacus.getString("foto_diri");
                    String ahlituk = datatuk.getString("Keahlian");

                    setdata(namasewa,ahlituk,fototuk);

                } catch (Exception e) {
                    Toast.makeText(beri_rating.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(beri_rating.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_tukang", id);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void setdata(String namasewa,String ahlituk,String fototuk){

        TextView namav = findViewById(R.id.nam_tuk);
        TextView ratingv = findViewById(R.id.rating_tuk);

        namav.setText(namasewa);
        ratingv.setText(ahlituk);

        ImageView foto = findViewById(R.id.fot_tuk);

        Picasso.get().load(Url.ASSET_PROFIL+fototuk).into(foto);
    }

    private void berirating() {

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id_sewa");
        final String idtukang = intent.getStringExtra("id_tukang");
        sessionManager = new SessionManager(beri_rating.this);

        getRating = findViewById(R.id.rat);

        progressDialog = new ProgressDialog(beri_rating.this);
        progressDialog.setMessage("Proses");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_RATING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("stat");

                    progressDialog.dismiss();
                    if(status.equals("true")){

                        Toast.makeText(beri_rating.this, "Beri Rating Suksess", Toast.LENGTH_LONG).show();
                        Intent main = new Intent(beri_rating.this, beranda.class);
                        startActivity(main);
                        finish();

                    }
                    else{
                        Toast.makeText(beri_rating.this, "Error", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(beri_rating.this,"ERORR:\n"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(beri_rating.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_sewa", id);
                params.put("id_tukang", idtukang);
                params.put("rating", getRating.getText().toString());

                return params;
            }
        };

        queue.add(stringRequest);
    }

    private boolean validateRating() {
        String ratinggg = getRating.getText().toString().trim();

        if (ratinggg.isEmpty()) {
            Toast.makeText(beri_rating.this, "Rating Harus Diisi", Toast.LENGTH_LONG).show();
            return false;
        } else {
            berirating();
            return true;
        }
    }

}

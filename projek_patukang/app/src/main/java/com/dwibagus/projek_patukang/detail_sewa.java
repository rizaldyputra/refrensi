package com.dwibagus.projek_patukang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
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

public class detail_sewa extends AppCompatActivity {

    private RequestQueue queue;
    TextInputLayout AlamatSewa,luassewa,detailsewa;
    TextView tglsewa,hargav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sewa);

        queue = Volley.newRequestQueue(detail_sewa.this);

        ActionBar actionBar;
        actionBar = getSupportActionBar();assert actionBar != null;
        actionBar.setTitle("Detail Sewa");

        AlamatSewa = findViewById(R.id.alamat_sewa);
        luassewa = findViewById(R.id.luas_sewa);
        detailsewa = findViewById(R.id.detail_sewa);
        tglsewa = findViewById(R.id.tv_dateresult);
        hargav = findViewById(R.id.harga_disewa);

        view();

    }

    private void view() {
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id_sewa");
        SessionManager sessionManager = new SessionManager(detail_sewa.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_DETAIL_SEWACUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject("data");

                    String tanggalsewa = data.getString("tgl_sewa");
                    String alamatsewa = data.getString("alamat_sewa");
                    String luassewa = data.getString("luas_sewa");
                    String totalsewa = data.getString("total_sewa");
                    String detailsewa = data.getString("detail_sewa");
                    String statussewa = data.getString("status_sewa");

                    setdata(tanggalsewa,alamatsewa,luassewa,totalsewa,detailsewa,statussewa);

                } catch (Exception e) {
                    Toast.makeText(detail_sewa.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(detail_sewa.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_sewa", id);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void setdata(String tanggalsewa,
                        String alamatsewa,String luassewa,
                        String totalsewa,String detailsewa,String statussewa){

        TextView tangalv = findViewById(R.id.tgl_sewa2);
        TextView alamatv = findViewById(R.id.alamat_sewa2);
        TextView luasv = findViewById(R.id.luas_sewa2);
        TextView totalv = findViewById(R.id.total_sewa2);
        TextView detailv = findViewById(R.id.dtl_sewa2);

        tangalv.setText(tanggalsewa);
        alamatv.setText(alamatsewa);
        luasv.setText(luassewa);
        totalv.setText(totalsewa);
        detailv.setText(detailsewa);


    }
}

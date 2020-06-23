package com.dwibagus.projek_patukang;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dwibagus.projek_patukang.Adapater.AdapaterDataAlu;
import com.dwibagus.projek_patukang.Api.Url;
import com.dwibagus.projek_patukang.Model.ModelDataAlu;
import com.dwibagus.projek_patukang.Session.SessionManager;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class alumunium extends AppCompatActivity {

    String id;
    View view;
    List<ModelDataAlu> mItems;
    RecyclerView.LayoutManager mManager;
    RecyclerView.Adapter mAdapter;
    ProgressDialog pd;
    TextInputLayout nam_leng;

    private RequestQueue queue;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumunium);

        SessionManager sessionManager = new SessionManager(alumunium.this);
        queue = Volley.newRequestQueue(alumunium.this);
        ActionBar actionBar;
        actionBar = getSupportActionBar();assert actionBar != null;
        actionBar.setTitle("Kategori Tukang Alumunium");
        reload();
    }


    public void reload(){
        RecyclerView  mRecyclerview = findViewById(R.id.rcy_beranda_alu);
        pd = new ProgressDialog(alumunium.this);
        mItems = new ArrayList<>();

        loadHome();

        mManager = new GridLayoutManager(alumunium.this,1);
        mRecyclerview.setLayoutManager(mManager);

        mAdapter = new AdapaterDataAlu(this,mItems);
        mRecyclerview.setAdapter(mAdapter);
    }

    private void loadHome() {

        SessionManager sessionManager = new SessionManager(alumunium.this);
        id = sessionManager.getIdAkun();

        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest reqData = new StringRequest(Request.Method.POST, Url.API_BERANDA_ALU,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        Log.d("volley","response : " + response.toString());
                        try {
                            JSONArray j = new JSONArray(response);
                            for(int i = 0 ; i < response.length(); i++)
                            {
                                try {
                                    JSONObject data = j.getJSONObject(i);
                                    ModelDataAlu md = new ModelDataAlu();
                                    md.setId_tukang(data.getString("id_tukang"));
                                    md.setFoto_tukang(data.getString("foto_diri"));
                                    md.setNama_tukang(data.getString("nam_leng"));
                                    md.setKeahlian_tukang(data.getString("Keahlian"));
                                    md.setHarga_tukang(data.getString("harga_tukang"));
                                    mItems.add(md);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Log.d("volley", "error : " + error.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_customer", id);
                return params;
            }
        };

        queue.add(reqData);
    }
}

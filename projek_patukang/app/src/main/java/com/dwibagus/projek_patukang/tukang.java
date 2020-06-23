package com.dwibagus.projek_patukang;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

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
import com.dwibagus.projek_patukang.Adapater.AdapaterDataTukang;
import com.dwibagus.projek_patukang.Api.Url;
import com.dwibagus.projek_patukang.Model.ModelDataTukang;
import com.dwibagus.projek_patukang.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class tukang extends AppCompatActivity {

    String id;
    SessionManager sessionManager;

    List<ModelDataTukang> mtItems;
    RecyclerView mRecyclerview;
    RecyclerView.LayoutManager mtManager;
    RecyclerView.Adapter mtAdapter;
    ProgressDialog pd;

    private RequestQueue queue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tukang);

        ActionBar actionBar;
        actionBar = getSupportActionBar();assert actionBar != null;
        actionBar.setTitle("Pesanan Tukang Anda");

        SessionManager sessionManager = new SessionManager(tukang.this);
        queue = Volley.newRequestQueue(tukang.this);

        reload();

    }

    public void reload(){
        mRecyclerview = findViewById(R.id.row_pesanan_tukang);
        pd = new ProgressDialog(tukang.this);
        mtItems = new ArrayList<>();

        loadHome();

        mtManager = new GridLayoutManager(tukang.this,1);
        mRecyclerview.setLayoutManager(mtManager);

        mtAdapter = new AdapaterDataTukang(tukang.this,mtItems);
        mRecyclerview.setAdapter(mtAdapter);
    }

    private void loadHome() {
        SessionManager sessionManager = new SessionManager(tukang.this);
        id = sessionManager.getIdAkun();

        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest reqData = new StringRequest(Request.Method.POST, Url.API_PROFIL_TUKANG,
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
                                    ModelDataTukang md = new ModelDataTukang();
                                    md.setId_sewa(data.getString("id_sewa"));
                                    md.setNama_sewa(data.getString("nam_leng"));
                                    md.setTgl_sewa(data.getString("tgl_sewa"));
                                    md.setStatus_sewa(data.getString("status_sewa"));
                                    mtItems.add(md);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            mtAdapter.notifyDataSetChanged();
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

package com.dwibagus.projek_patukang.ui.menu2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dwibagus.projek_patukang.Adapater.AdapaterData;
import com.dwibagus.projek_patukang.Api.Url;
import com.dwibagus.projek_patukang.Model.ModelData;
import com.dwibagus.projek_patukang.R;
import com.dwibagus.projek_patukang.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class menu2Fragment extends Fragment {

    SessionManager sessionManager;
    private RequestQueue queue;

    List<ModelData> mItems;
    RecyclerView mRecyclerview;
    RecyclerView.LayoutManager mManager;
    RecyclerView.Adapter mAdapter;
    View view;
    Toolbar toolbar;

    ProgressDialog pd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu2, container, false);

        SessionManager sessionManager = new SessionManager(getActivity());
        queue = Volley.newRequestQueue(getActivity());

        reload();

        return view;
    }

    public void reload(){
        RecyclerView mRecyclerview = view.findViewById(R.id.row_pesanan_customer);
        pd = new ProgressDialog(getActivity());
        mItems = new ArrayList<>();

        loadHome();

        mManager = new GridLayoutManager(getActivity(),1);
        mRecyclerview.setLayoutManager(mManager);

        mAdapter = new AdapaterData(getActivity(),mItems);
        mRecyclerview.setAdapter(mAdapter);
    }

    private void loadHome() {
        SessionManager sessionManager = new SessionManager(getActivity());

        final String id_akun = sessionManager.getIdAkun();

        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest reqData = new StringRequest(Request.Method.POST, Url.API_PESANAN_CUSTOMER,
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
                                    ModelData md = new ModelData();
                                    md.setId_sewa(data.getString("id_sewa"));
                                    md.setId_tukang(data.getString("id_tukang"));
                                    md.setTgl_sewa(data.getString("tgl_sewa"));
                                    md.setStatus_sewa(data.getString("status_sewa"));
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
                params.put("id_customer", id_akun);
                return params;
            }
        };

        queue.add(reqData);
    }

}

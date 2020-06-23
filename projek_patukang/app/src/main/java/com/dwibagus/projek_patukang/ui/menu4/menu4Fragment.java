package com.dwibagus.projek_patukang.ui.menu4;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dwibagus.projek_patukang.Api.Url;
import com.dwibagus.projek_patukang.R;
import com.dwibagus.projek_patukang.Session.SessionManager;
import com.dwibagus.projek_patukang.tukang;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class menu4Fragment extends Fragment {

    SessionManager sessionManager;
    private RequestQueue queue;
    String id;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu4, container, false);

        queue = Volley.newRequestQueue(getActivity());
        sessionManager = new SessionManager(getActivity());
        viewProfile();

        Button pesanan_tukang = view.findViewById(R.id.pesanan_tukang);
        pesanan_tukang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), tukang.class));

            }
        });
    return view;
    }

    private void viewProfile() {

        id = sessionManager.getIdAkun();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_PROFIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject datatukang = jsonObject.getJSONObject("tukang");
                    JSONObject datarating = jsonObject.getJSONObject("rating");

                    String Keahlian = datatukang.getString("Keahlian");
                    String Harga_tukang = datatukang.getString("harga_tukang");
                    String Rating = datarating.getString("rating");


                    setdata(Keahlian,Harga_tukang,Rating);

                } catch (Exception e) {
                    Toast.makeText(getActivity(),"ERORR:\n"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"ERORR:\n"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id_customer",id);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void setdata(String Keahlian, String Harga_tukang, String Rating){

        TextView Keahlianv = view.findViewById(R.id.Keahlian);
        TextView Harga_tukangv = view.findViewById(R.id.harga_tukang);
        TextView Ratingv = view.findViewById(R.id.rating_tukang);

        Keahlianv.setText(Keahlian);
        Harga_tukangv.setText(Harga_tukang);
        Ratingv.setText(Rating);
    }

}

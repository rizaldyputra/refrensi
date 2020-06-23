package com.dwibagus.projek_patukang.ui.menu1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
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
import com.dwibagus.projek_patukang.Adapater.AdapaterDataBeranda;
import com.dwibagus.projek_patukang.Api.Url;
import com.dwibagus.projek_patukang.Model.ModelDataBeranda;
import com.dwibagus.projek_patukang.R;
import com.dwibagus.projek_patukang.Session.SessionManager;
import com.dwibagus.projek_patukang.alumunium;
import com.dwibagus.projek_patukang.atap;
import com.dwibagus.projek_patukang.cat;
import com.dwibagus.projek_patukang.hasil_cari;
import com.dwibagus.projek_patukang.keramik;
import com.dwibagus.projek_patukang.terendah;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class menu1Fragment extends Fragment {
    ViewFlipper viewFlipper;
    ImageView terendah,aluv,ker,catv,atapv;
    String id;
    View view;
    List<ModelDataBeranda> mItems;
    RecyclerView.LayoutManager mManager;
    RecyclerView.Adapter mAdapter;
    ProgressDialog pd;
    TextInputLayout nam_leng;

    private RequestQueue queue;
    Button button;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu1, container, false);

        SessionManager sessionManager = new SessionManager(getActivity());
        queue = Volley.newRequestQueue(getActivity());

        int images[] = {R.drawable.iklan,R.drawable.iklan2,R.drawable.iklan3};
        viewFlipper = view.findViewById(R.id.v_fliper);

        for (int image : images){
            flipperImages(image);
        }

        reload();

        terendah = view.findViewById(R.id.trndh);
        aluv = view.findViewById(R.id.alu);
        catv = view.findViewById(R.id.cat);
        ker = view.findViewById(R.id.ker);
        atapv = view.findViewById(R.id.atp);

        terendah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), terendah.class));

            }
        });
        aluv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), alumunium.class));

            }
        });
        catv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), cat.class));

            }
        });
        ker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), keramik.class));

            }
        });
        atapv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),atap.class));

            }
        });


        button = view.findViewById(R.id.btncari);

        nam_leng = view.findViewById(R.id.cari);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmcari();
            }
        });
        
        return view;
    }

    public void  flipperImages(int images){
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(images);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(getActivity(),android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getActivity(),android.R.anim.slide_out_right);

    }

    private boolean validateNama() {
        String nam_lengr = nam_leng.getEditText().getText().toString().trim();

        if (nam_lengr.isEmpty()) {
            nam_leng.setError("Nama harus diisi");
            return false;
        } else {
            nam_leng.setError(null);
            nam_leng.setErrorEnabled(false);
            return true;
        }
    }

    public void reload(){
        RecyclerView  mRecyclerview = view.findViewById(R.id.rcy_beranda);
        pd = new ProgressDialog(getActivity());
        mItems = new ArrayList<>();

        loadHome();

        mManager = new GridLayoutManager(getActivity(),1);
        mRecyclerview.setLayoutManager(mManager);

        mAdapter = new AdapaterDataBeranda(getContext(),mItems);
        mRecyclerview.setAdapter(mAdapter);
    }

    private void loadHome() {

        SessionManager sessionManager = new SessionManager(getActivity());
        id = sessionManager.getIdAkun();

        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest reqData = new StringRequest(Request.Method.POST, Url.API_BERANDA,
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
                                    ModelDataBeranda md = new ModelDataBeranda();
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


    private void prosescari() {
        SessionManager sessionManager = new SessionManager(getActivity());
        id = sessionManager.getIdAkun();

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Proses");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_CARI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("stat");

                    pd.dismiss();
                    if(status.equals("true")){

                        JSONObject cus = jsonObject.getJSONObject("cus");
                        JSONObject tukang = jsonObject.getJSONObject("tukang");

                        String id_customerv = cus.getString("id_customer");
                        String nam_lengv = cus.getString("nam_leng");
                        String fotov = cus.getString("foto_diri");
                        String Keahlianv = tukang.getString("Keahlian");
                        String hargav = tukang.getString("harga_tukang");


                        Intent update = new Intent(getActivity(), hasil_cari.class);
                        update.putExtra("id_customer",id_customerv);
                        update.putExtra("nam_leng",nam_lengv);
                        update.putExtra("foto_diri",fotov);
                        update.putExtra("Keahlian",Keahlianv);
                        update.putExtra("harga_tukang",hargav);
                        startActivity(update);

                    }
                    else{
                        nam_leng.setError("TUKANG YANG ANDA CARI TIDAK ADA ! SILAHKAN MASUKKAN NAMA YANG SESUAI");
                        Toast.makeText(getActivity(), "Pencarian Gagal", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    nam_leng.setError("TUKANG YANG ANDA CARI TIDAK ADA ! SILAHKAN MASUKKAN NAMA YANG SESUAI");
                    Toast.makeText(getActivity(), "Pencarian Gagal", Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(),"ERORR:\n"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nam_leng.setError("TUKANG YANG ANDA CARI TIDAK ADA ! SILAHKAN MASUKKAN NAMA YANG SESUAI");
                Toast.makeText(getActivity(), "Pencarian Gagal", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_customer", id);
                params.put("keyword", nam_leng.getEditText().getText().toString().trim());
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void confirmcari() {
        if ( validateNama()) {
            prosescari();
        }
    }
}

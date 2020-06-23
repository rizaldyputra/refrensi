package com.dwibagus.projek_patukang.ui.menu3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class menu3Fragment extends Fragment {

    SessionManager sessionManager;
    private RequestQueue queue;
    Button saveP,selectP;
    ImageView foto;
    String id;

    private String mBitmapName;
    Bitmap bitmap;
    ProgressDialog progressDialog;
    TextInputLayout namas,no_telps,alamats,emails;
    private final int IMG_REQUEST = 1;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu3, container, false);

        queue = Volley.newRequestQueue(getActivity());
        sessionManager = new SessionManager(getActivity());
        viewProfile();

        foto = view.findViewById(R.id.foto_cus);

        saveP = view.findViewById(R.id.savegambar);
        selectP = view.findViewById(R.id.pilihgambar);

        Button edit = view.findViewById(R.id.edit);

        edit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        }));

        selectP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        saveP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateFotoProfile();
            }
        });
        
        return view;
    }

    private void viewProfile() {
        
        SessionManager sessionManager = new SessionManager(getActivity());
        
        id = sessionManager.getIdAkun();
        
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_PROFIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject("data");

                    String pprofil = data.getString("foto_diri");
                    String nama = data.getString("nam_leng");
                    String alamat= data.getString("alamat");
                    String email = data.getString("email");
                    String no_telp = data.getString("no_telp");


                    setdata(nama,alamat,email,no_telp,pprofil);

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

    public void setdata(String nama, String alamat, String email,String no_telp, String pprofil){

        TextInputEditText namav = view.findViewById(R.id.txtnam_leng);
        TextInputEditText alamatv = view.findViewById(R.id.txtlokasi);
        TextInputEditText emailv = view.findViewById(R.id.txtemail);
        TextInputEditText no_telpv = view.findViewById(R.id.txtno_telp);

        ImageView pprofilv = view.findViewById(R.id.foto_cus);

        namav.setText(nama);
        alamatv.setText(alamat);
        emailv.setText(email);
        no_telpv.setText(no_telp);

        if (!pprofil.equals("")){
            Picasso.get().load(Url.ASSET_PROFIL+pprofil).into(pprofilv);
        }

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void updateProfile() {
        id = sessionManager.getIdAkun();
        namas = view.findViewById(R.id.nam_leng);
        alamats =view.findViewById(R.id.alamat);
        emails = view.findViewById(R.id.email);
        no_telps = view.findViewById(R.id.no_telp);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_UPDATE_PROFIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("stat");

                    progressDialog.dismiss();
                    if(status.equals("true")){

                        Toast.makeText(getActivity(), "Update Sukses", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"ERORR:\n"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_customer", id);
                params.put("nam_leng", namas.getEditText().getText().toString().trim());
                params.put("alamat", alamats.getEditText().getText().toString().trim());
                params.put("no_telp", no_telps.getEditText().getText().toString().trim());
                params.put("email", emails.getEditText().getText().toString().trim());
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void updateFotoProfile() {

        id = sessionManager.getIdAkun();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_UPDATE_PP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("stat");

                    progressDialog.dismiss();
                    if(status.equals("true")){

                        Toast.makeText(getActivity(), "Update Sukses", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"ERORR:\n"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_customer", id);
                if (bitmap!=null) {
                    params.put("foto_diri", imageToString(bitmap));
                }
                return params;
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
//            mengambil alamat file gambar
            Uri path = data.getData();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(path);
                mBitmapName = path.getPath();
                bitmap = BitmapFactory.decodeStream(inputStream);

                ImageView pprofilv = view.findViewById(R.id.foto_cus);
                pprofilv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}

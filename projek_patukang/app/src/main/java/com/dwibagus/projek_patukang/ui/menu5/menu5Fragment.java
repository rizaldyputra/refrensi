package com.dwibagus.projek_patukang.ui.menu5;

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
import android.widget.Spinner;
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
import com.dwibagus.projek_patukang.tukang;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class menu5Fragment extends Fragment {

    SessionManager sessionManager;
    private RequestQueue queue;
    Button saveP,selectP;
    ImageView foto;
    TextInputLayout norek,harga;
    String id;

    private String mBitmapName;
    Bitmap bitmap;
    ProgressDialog progressDialog;
    private final int IMG_REQUEST = 1;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu5, container, false);

        queue = Volley.newRequestQueue(getActivity());
        sessionManager = new SessionManager(getActivity());

        norek =  view.findViewById(R.id.no_rekr);
        harga = view.findViewById(R.id.harga_tukangr);

        foto = view.findViewById(R.id.imageUpload);

        saveP = view.findViewById(R.id.daftar_tukanggg);
        selectP = view.findViewById(R.id.pilih_ktp);

        selectP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        saveP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                confirmInputUpload();
            }
        });

        return view;
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

    private void updateFotoProfile() {

        final Spinner Keahlian = view.findViewById(R.id.sp_name);
        final TextInputLayout harga_tukang = view.findViewById(R.id.harga_tukangr);
        final TextInputLayout no_rek = view.findViewById(R.id.no_rekr);

        id = sessionManager.getIdAkun();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_DAFTAR_TUKANG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("stat");

                    progressDialog.dismiss();
                    if(status.equals("true")){

                        Toast.makeText(getActivity(), "Daftar Tukang Sukses", Toast.LENGTH_LONG).show();
                        Intent main = new Intent(getActivity(), tukang.class);
                        startActivity(main);
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
                params.put("Keahlian", Keahlian.getSelectedItem().toString().trim());
                params.put("harga_tukang", harga_tukang.getEditText().getText().toString().trim());
                params.put("no_rek", no_rek.getEditText().getText().toString().trim());
                params.put("foto_ktp", imageToString(bitmap));
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

                ImageView pprofilv = view.findViewById(R.id.imageUpload);
                pprofilv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validatenorek() {
        String norekInput =  norek.getEditText().getText().toString().trim();

        if (norekInput.isEmpty()) {
            norek.setError("Nomer Reening Wajib Diisi");
            return false;
        } else {
            norek.setError(null);
            norek.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateharga() {
        String hargaInput = harga.getEditText().getText().toString().trim();

        if (hargaInput.isEmpty()) {
            harga.setError("Harga harus diisi");
            return false;
        } else {
            harga.setError(null);
            harga.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateFoto() {
        if (bitmap==null) {
            Toast.makeText(getActivity(), "Gambar harus dipilih", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
    public void confirmInputUpload() {
        if (validateharga() && validatenorek() &&  validateFoto()) {
            updateFotoProfile();
        }
    }
}

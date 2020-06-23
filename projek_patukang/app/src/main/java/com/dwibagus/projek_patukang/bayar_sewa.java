package com.dwibagus.projek_patukang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class bayar_sewa extends AppCompatActivity {

    SessionManager sessionManager;
    private RequestQueue queue;
    Button saveP,selectP;
    ImageView foto;
    TextInputLayout norek;

    private String mBitmapName;
    Bitmap bitmap;
    ProgressDialog progressDialog;
    private final int IMG_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_sewa);

        queue = Volley.newRequestQueue(bayar_sewa.this);
        sessionManager = new SessionManager(bayar_sewa.this);

        norek = findViewById(R.id.no_rekbayar);
        foto = findViewById(R.id.imageUpload);
        ActionBar actionBar;
        actionBar = getSupportActionBar();assert actionBar != null;
        actionBar.setTitle("Pembayaran");
        view();

        saveP = findViewById(R.id.btn_bayar);
        selectP = findViewById(R.id.pilih_foto);

        selectP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        saveP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                confirmBayar();
            }
        });


    }

    private void view() {
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id_sewa");
        SessionManager sessionManager = new SessionManager(bayar_sewa.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_PEMBAYARAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject("datasewa");
                    JSONObject datacus = jsonObject.getJSONObject("data");

                    String namasewa = datacus.getString("nam_leng");
                    String alamatsewa = data.getString("alamat_sewa");
                    String tanggalsewa = data.getString("tgl_sewa");
                    String luassewa = data.getString("luas_sewa");
                    String totalsewa = data.getString("total_sewa");
                    String detailsewa = data.getString("detail_sewa");

                    setdata(namasewa,tanggalsewa,alamatsewa,luassewa,totalsewa,detailsewa);

                } catch (Exception e) {
                    Toast.makeText(bayar_sewa.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(bayar_sewa.this, error.toString(), Toast.LENGTH_LONG).show();
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

    public void setdata(String namasewa,String tanggalsewa,
                        String alamatsewa,String luassewa,
                        String totalsewa,String detailsewa){

        TextView namav = findViewById(R.id.atasnama);
        TextView tangalv = findViewById(R.id.tglsewa);
        TextView alamatv = findViewById(R.id.alamatsewa);
        TextView luasv = findViewById(R.id.luassewa);
        TextView totalv = findViewById(R.id.totalsewa);
        TextView detailv = findViewById(R.id.detailsewa);

        namav.setText(namasewa);
        tangalv.setText(tanggalsewa);
        alamatv.setText(alamatsewa);
        luasv.setText(luassewa);
        totalv.setText(totalsewa);
        detailv.setText(detailsewa);
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

    private void Bayar() {

        final Spinner norek = findViewById(R.id.sp_name);
        final TextInputLayout no_rek = findViewById(R.id.no_rekbayar);
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id_sewa");

        progressDialog = new ProgressDialog(bayar_sewa.this);
        progressDialog.setMessage("Proses");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_CEK_PEMBAYARAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("stat");
                    progressDialog.dismiss();
                    if(status.equals("true")){

                        Toast.makeText(bayar_sewa.this, "Pembayaran Sukses ! Ayo Lakukan Penyewaan Lagi", Toast.LENGTH_LONG).show();
                        Intent main = new Intent(bayar_sewa.this, beranda.class);
                        startActivity(main);
                        finish();
                    }
                    else{
                        Toast.makeText(bayar_sewa.this, "Error", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(bayar_sewa.this,"ERORR:\n"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(bayar_sewa.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_sewa", id);
                params.put("no_rek_admin", norek.getSelectedItem().toString().trim());
                params.put("no_rek_cus", no_rek.getEditText().getText().toString().trim());
                params.put("foto_transaksi", imageToString(bitmap));
                return params;
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == bayar_sewa.this.RESULT_OK && data != null) {
//            mengambil alamat file gambar
            Uri path = data.getData();

            try {
                InputStream inputStream = bayar_sewa.this.getContentResolver().openInputStream(path);
                mBitmapName = path.getPath();
                bitmap = BitmapFactory.decodeStream(inputStream);

                ImageView pprofilv = findViewById(R.id.imageUpload);
                pprofilv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Toast.makeText(bayar_sewa.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validatenorek() {
        String hargaInput = norek.getEditText().getText().toString().trim();

        if (hargaInput.isEmpty()) {
            norek.setError("No. Rekening Harus diisi");
            return false;
        } else {
            norek.setError(null);
            norek.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateFoto() {
        if (bitmap==null) {
            Toast.makeText(bayar_sewa.this, "Gambar harus dipilih", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public void confirmBayar() {
        if (validatenorek() &&  validateFoto()) {
            Bayar();
        }
    }
}

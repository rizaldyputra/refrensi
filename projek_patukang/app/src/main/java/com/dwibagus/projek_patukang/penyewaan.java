package com.dwibagus.projek_patukang;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class penyewaan extends AppCompatActivity {

    private RequestQueue queue;

    ProgressDialog progressDialog;
    TextInputLayout AlamatSewa,luassewa,detailsewa;
    TextView tglsewa,hargav;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateResult;
    private Button btDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyewaan);

        queue = Volley.newRequestQueue(penyewaan.this);

        ActionBar actionBar;
        actionBar = getSupportActionBar();assert actionBar != null;
        actionBar.setTitle("Penyewaan Tukang");

        AlamatSewa = findViewById(R.id.alamat_sewa);
        luassewa = findViewById(R.id.luas_sewa);
        detailsewa = findViewById(R.id.detail_sewa);
        tglsewa = findViewById(R.id.tv_dateresult);
        hargav = findViewById(R.id.harga_disewa);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

        tvDateResult = findViewById(R.id.tv_dateresult);
        btDatePicker = findViewById(R.id.bt_datepicker);
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDateDialog();
            }
        });

        Button sewa = findViewById(R.id.btn_penyewaan);
        sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmSewa();
            }
        });

        view();

    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvDateResult.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

        private void view() {
            Intent intent = getIntent();
            final String id = intent.getStringExtra("id_tukang");
            SessionManager sessionManager = new SessionManager(penyewaan.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_PENYEWAAN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject data = jsonObject.getJSONObject("datatukang");
                        JSONObject datacustomer = jsonObject.getJSONObject("datacustomer");
                        JSONObject rating = jsonObject.getJSONObject("rating");

                        String nama_tukang = datacustomer.getString("nam_leng");
                        String keahlian_tukang = data.getString("Keahlian");
                        String harga_tukang = data.getString("harga_tukang");
                        String rating_tukang = rating.getString("rating");

                        String gambar = datacustomer.getString("foto_diri");

                        setdata(nama_tukang,keahlian_tukang,harga_tukang,rating_tukang,gambar);
                    } catch (Exception e) {
                        Toast.makeText(penyewaan.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(penyewaan.this, error.toString(), Toast.LENGTH_LONG).show();
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

        public void setdata(String nama_tukang,String keahlian_tukang,String harga_tukang,String rating_tukang,String gambar){

            TextView namav = findViewById(R.id.nama_disewa);
            TextView keahlianv = findViewById(R.id.Keahlian_disewa);
            hargav = findViewById(R.id.harga_disewa);
            TextView ratingv = findViewById(R.id.rating_disewa);

            ImageView gambarv = findViewById(R.id.foto_disewa);

            namav.setText(nama_tukang);
            keahlianv.setText(keahlian_tukang);
            hargav.setText(harga_tukang);
            ratingv.setText(rating_tukang);

            Picasso.get().load(Url.ASSET_PROFIL+gambar).into(gambarv);

        }

    private void sewa_tukang() {

        final TextInputLayout AlamatSewa = findViewById(R.id.alamat_sewa);
        final TextInputLayout luassewa = findViewById(R.id.luas_sewa);
        final TextView tanggalsewa = findViewById(R.id.tv_dateresult);
        final TextInputLayout detailsewa = findViewById(R.id.detail_sewa);

        final SessionManager sessionManager = new SessionManager(penyewaan.this);
        Intent intent = getIntent();
        final String id_tukang = intent.getStringExtra("id_tukang");
        final String id = sessionManager.getIdAkun();

        progressDialog = new ProgressDialog(penyewaan.this);
        progressDialog.setMessage("Proses");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.API_CEK_SEWA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    progressDialog.dismiss();
                    if(status.equals("true")){

                        Toast.makeText(penyewaan.this, "Penyewaan Tukang Berhasil", Toast.LENGTH_LONG).show();
                        Intent main = new Intent(penyewaan.this, beranda.class);
                        startActivity(main);
                        finish();
                    }
                    else{
                        Toast.makeText(penyewaan.this, "Sewa Gagal ! Pastikan Tanggal Valid", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(penyewaan.this,"ERORR:\n"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(penyewaan.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_customer", id);
                params.put("id_tukang", id_tukang);
                params.put("alamat_sewa", AlamatSewa.getEditText().getText().toString().trim());
                params.put("luas_sewa", luassewa.getEditText().getText().toString().trim());
                params.put("tgl_sewa", tanggalsewa.getText().toString().trim());
                params.put("detail_sewa", detailsewa.getEditText().getText().toString().trim());
                params.put("harga_sewa", hargav.getText().toString().trim());
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private boolean validateAlamat() {
        String alamatSewa =  AlamatSewa.getEditText().getText().toString().trim();

        if (alamatSewa.isEmpty()) {
            AlamatSewa.setError("Alamat Wajib Diisi");
            return false;
        } else {
            AlamatSewa.setError(null);
            AlamatSewa.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateLuas() {
        String luasSewa = luassewa.getEditText().getText().toString().trim();

        if (luasSewa.isEmpty()) {
            luassewa.setError("Luas Sewa harus diisi");
            return false;
        } else {
            luassewa.setError(null);
            luassewa.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateTanggal() {
        String tglSewa = tglsewa.getText().toString().trim();

        if (tglSewa.isEmpty()) {
            Toast.makeText(penyewaan.this, "Tanggal harus dipilih", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateDetail() {
        String detailSewa = detailsewa.getEditText().getText().toString().trim();

        if (detailSewa.isEmpty()) {
            detailsewa.setError("Harga harus diisi");
            return false;
        } else {
            detailsewa.setError(null);
            detailsewa.setErrorEnabled(false);
            return true;
        }
    }


    public void confirmSewa() {
        if (validateAlamat() && validateLuas() &&  validateTanggal() && validateDetail()) {
            sewa_tukang();
        }
    }
}

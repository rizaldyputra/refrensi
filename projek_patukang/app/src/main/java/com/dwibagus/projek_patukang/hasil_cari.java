package com.dwibagus.projek_patukang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.dwibagus.projek_patukang.Api.Url;
import com.squareup.picasso.Picasso;

public class hasil_cari extends AppCompatActivity {

    TextView nama_tukang,keahlian_tukang,harga_tukang;
    Button toSewa;
    String id,nm,hr,kh,ft;
    ImageView foto_tukang;
    RelativeLayout hsltr;
    LinearLayout hslnul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_cari);

        nama_tukang = findViewById(R.id.nam);
        keahlian_tukang = findViewById(R.id.ahl);
        harga_tukang = findViewById(R.id.hrg);
        foto_tukang = findViewById(R.id.fot);
        hsltr = findViewById(R.id.hsltr);
        hslnul = findViewById(R.id.hslnul);
        ActionBar actionBar;
        actionBar = getSupportActionBar();assert actionBar != null;
        actionBar.setTitle("Hasil Pencarian");
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            id = extras.getString("id_customer");
            nm = extras.getString("nam_leng");
            ft = extras.getString("foto_diri");
            kh = extras.getString("Keahlian");
            hr = extras.getString("harga_tukang");
            hslnul.setVisibility(View.GONE);
        }else {
            hsltr.setVisibility(View.GONE);
        }

        Picasso.get().load(Url.ASSET_PROFIL+ft).into(foto_tukang);

        toSewa = findViewById(R.id.btn_sewaa);

        nama_tukang.setText(nm);
        keahlian_tukang.setText(kh);
        harga_tukang.setText(hr);

        toSewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(hasil_cari.this, penyewaan.class);
                update.putExtra("id_tukang",id);
                startActivity(update);
            }
        });


    }
}

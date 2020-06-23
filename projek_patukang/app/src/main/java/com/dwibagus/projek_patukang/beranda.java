package com.dwibagus.projek_patukang;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dwibagus.projek_patukang.Api.Url;
import com.dwibagus.projek_patukang.Session.SessionManager;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class beranda extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    MenuItem navtukang,navdaftar;
    SessionManager sessionManager;
    private RequestQueue queue;
    String id, nama,foto,status;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        sessionManager = new SessionManager(this);
        queue = Volley.newRequestQueue(beranda.this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_menu1,
                R.id.nav_menu2,
                R.id.nav_menu3,
                R.id.nav_menu4,
                R.id.nav_menu5)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(beranda.this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(beranda.this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        MenuItem navLogOutItem = navigationView.getMenu().findItem(R.id.nav_log_out);
        navLogOutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                sessionManager.logout();
                return false;
            }
        });

            id = sessionManager.getIdAkun();
            status = sessionManager.getStatus();
            foto = sessionManager.getFotoDiri();
            nama = sessionManager.getNamLeng();

        navtukang = navigationView.getMenu().findItem(R.id.nav_menu4);
        navdaftar = navigationView.getMenu().findItem(R.id.nav_menu5);


        if (status.equals("2")){
            navdaftar.setVisible(false);
        }else{
            navtukang.setVisible(false);
        }

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.namaku);
        navUsername.setText(nama);

        ImageView fotouser = headerView.findViewById(R.id.fotoku);
        Picasso.get().load(Url.ASSET_PROFIL+foto).into(fotouser);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_beranda_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
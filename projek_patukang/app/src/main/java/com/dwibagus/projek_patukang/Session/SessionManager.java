package com.dwibagus.projek_patukang.Session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.dwibagus.projek_patukang.MainActivity;
import com.dwibagus.projek_patukang.beranda;

public class SessionManager {
    SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public Context context;

    public static final String PREF_NAME = "LOGIN";
    public static final String LOGIN_STATUS = "false";
    public static final String NAM_LENG = "NAM_LENG";
    public static final String STATUS = "STATUS";
    public static final String ID_CUSTOMER = "ID_CUSTOMER";
    public static final String FOTO_DIRI = "FOTO_DIRI";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createSession(String nam_leng, String statustukang, String id_customer,String foto_diri) {
        editor.putString(NAM_LENG, nam_leng);
        editor.putString(STATUS, statustukang);
        editor.putString(ID_CUSTOMER, id_customer);
        editor.putString(FOTO_DIRI, foto_diri);
        editor.apply();
    }

    public boolean isLogin(){
        return pref.getBoolean(LOGIN_STATUS, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();

        Intent login = new Intent(context, MainActivity.class);
        context.startActivity(login);
        ((beranda)context).finish();
    }

    public String getNamLeng() { return pref.getString(NAM_LENG, null); }

    public String getStatus() {
        return pref.getString(STATUS, null);
    }

    public String getIdAkun() {
        return pref.getString(ID_CUSTOMER, null);
    }

    public String getFotoDiri() {
        return pref.getString(FOTO_DIRI, null);
    }
}

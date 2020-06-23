package com.dwibagus.projek_patukang.Api;

public class Url {
    //    api endpoint untuk request volley
    public static final String BASE = "http://patukang.mif-project.com/";
    public static final String API_LOGIN = BASE+"Api_login/login";
    public static final String API_REGISTER = BASE+"Api_login/register";
    public static final String API_PROFIL = BASE+"Api_profil/profil";
    public static final String API_PROFIL_TUKANG = BASE+"Api_profil/profil_tukang";
    public static final String API_DAFTAR_TUKANG = BASE+"Api_profil/daftar_tukang";
    public static final String API_UPDATE_PROFIL = BASE+"Api_profil/update_profil";
    public static final String API_UPDATE_PP = BASE+"Api_profil/update_gambar_profil";
    public static final String ASSET_PROFIL = BASE+"upload/foto_cus/";
    public static final String API_PESANAN_CUSTOMER = BASE+"Api_profil/pesanan_customer";
    public static final String API_BERANDA = BASE+"Api_home/home";
    public static final String API_PENYEWAAN = BASE+"Api_penyewaan/sewatukang";
    public static final String API_TERIMA_PENYEWAAN = BASE+"Api_penyewaan/terima_sewa";
    public static final String API_TOLAK_PENYEWAAN = BASE+"Api_penyewaan/tolak_sewa";
    public static final String API_CEK_SEWA = BASE+"Api_penyewaan/cek_sewa";
    public static final String API_DETAIL_SEWATUKANG = BASE+"Api_penyewaan/detail_sewa_tukang";
    public static final String API_DETAIL_SEWACUS = BASE+"Api_penyewaan/detail_sewa_cus";
    public static final String API_PEMBAYARAN = BASE+"Api_pembayaran/bayar_sewa";
    public static final String API_TAMPIL = BASE+"Api_pembayaran/tampil_rating";
    public static final String API_CEK_PEMBAYARAN = BASE+"Api_pembayaran/cek_bayar";
    public static final String API_RATING = BASE+"Api_pembayaran/beri_rating";
    public static final String API_CARI = BASE+"Api_search/cari";
    public static final String API_RESET = BASE+"Api_rstpwd/cekemail";
    public static final String API_GANTI = BASE+"Api_rstpwd/reset_password";
    public static final String API_BERANDA_ALU = BASE+"Api_home/home_alumunium";
    public static final String API_BERANDA_TERENDAH = BASE+"Api_home/home_terendah";
    public static final String API_BERANDA_KER = BASE+"Api_home/home_keramik";
    public static final String API_BERANDA_CAT = BASE+"Api_home/home_cat";
    public static final String API_BERANDA_ATAP = BASE+"Api_home/home_atap";
}

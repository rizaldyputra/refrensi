package com.dwibagus.projek_patukang.Adapater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dwibagus.projek_patukang.Model.ModelData;
import com.dwibagus.projek_patukang.R;
import com.dwibagus.projek_patukang.bayar_sewa;
import com.dwibagus.projek_patukang.beri_rating;
import com.dwibagus.projek_patukang.detail_sewa;

import java.util.List;

public class AdapaterData extends RecyclerView.Adapter<AdapaterData.HolderData> {
    private List<ModelData> mItems ;
    private Context context;

    public AdapaterData(Context context, List<ModelData> items)
    {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pemesanan_cus,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        ModelData md  = mItems.get(position);
        holder.tanggal_sewa.setText(md.getTgl_sewa());
        String status = md.getStatus_sewa();
        final String id = md.getId_sewa();
        final String idtukang = md.getId_tukang();

        if (status.equals("0")){
            holder.toBarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, detail_sewa.class);
                    update.putExtra("id_sewa",id);
                    context.startActivity(update);
                }
            });
            holder.status_sewa.setText("PENDING");
        }else if(status.equals("1")){
            holder.toBarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, bayar_sewa.class);
                    update.putExtra("id_sewa",id);
                    context.startActivity(update);
                }
            });
            holder.status_sewa.setText("DITERIMA");
        }else if(status.equals("2")){
            holder.toBarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, detail_sewa.class);
                    update.putExtra("id_sewa",id);
                    context.startActivity(update);
                }
            });
            holder.status_sewa.setText("DITOLAK");
        }else if(status.equals("3")){
            holder.toBarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, detail_sewa.class);
                    update.putExtra("id_sewa",id);
                    context.startActivity(update);
                }
            });
            holder.status_sewa.setText("BELUM DIBAYAR");
        }else if(status.equals("4")){
            holder.toBarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, beri_rating.class);
                    update.putExtra("id_sewa",id);
                    update.putExtra("id_tukang",idtukang);
                    context.startActivity(update);
                }
            });
            holder.status_sewa.setText("TERBAYAR ! BERI RATING SEKARANG");
        }else if(status.equals("5")){
            holder.toBarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, detail_sewa.class);
                    update.putExtra("id_sewa",id);
                    context.startActivity(update);
                }
            });
            holder.status_sewa.setText("SUKSESS");
        }
      
        holder.md = md;


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView tanggal_sewa, status_sewa;
        Button toBarang;
        ModelData md;

        public  HolderData (View view)
        {
            super(view);
            tanggal_sewa = view.findViewById(R.id.tgl_sewa);
            status_sewa = view.findViewById(R.id.status_sewa_customer);
            toBarang = view.findViewById(R.id.btn_aksi);
        }
    }
}
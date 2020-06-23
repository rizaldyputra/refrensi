package com.dwibagus.projek_patukang.Adapater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dwibagus.projek_patukang.Model.ModelDataTukang;
import com.dwibagus.projek_patukang.R;
import com.dwibagus.projek_patukang.detail_tukang;

import java.util.List;

public class AdapaterDataTukang extends RecyclerView.Adapter<AdapaterDataTukang.HolderData> {
    private List<ModelDataTukang> mtItems ;
    private Context context;

    public AdapaterDataTukang(Context context, List<ModelDataTukang> items)
    {
        this.mtItems = items;
        this.context = context;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pemesanan_tukang,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        ModelDataTukang mdt  = mtItems.get(position);
        holder.tanggal_sewa.setText(mdt.getTgl_sewa());
        holder.nama_sewa.setText(mdt.getNama_sewa());
        final String id = mdt.getId_sewa();

        String status = mdt.getStatus_sewa();

        if (status.equals("0")){
            holder.statuss.setText("PENDING");
        }else if(status.equals("1")){
            holder.statuss.setText("DITERIMA");
        }else if(status.equals("2")){
            holder.statuss.setText("DITOLAK");
        }else if(status.equals("3")){
            holder.statuss.setText("BELUM DIBAYAR");
        }else if(status.equals("4")){
            holder.statuss.setText("TERBAYAR");
        }else if(status.equals("5")){
            holder.statuss.setText("SUKSESS");
        }


        holder.lihatsewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(context, detail_tukang.class);
                update.putExtra("id_sewa",id);
                context.startActivity(update);
            }
        });

        holder.mdt = mdt;


    }

    @Override
    public int getItemCount() {
        return mtItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView tanggal_sewa, nama_sewa,statuss;
        Button lihatsewa;
        ModelDataTukang mdt;

        public  HolderData (View view)
        {
            super(view);
            tanggal_sewa = view.findViewById(R.id.tgl_sewa2);
            nama_sewa = view.findViewById(R.id.nama_sewa2);
            statuss = view.findViewById(R.id.status_sewa2);
            lihatsewa = view.findViewById(R.id.lihat_customer);
        }
    }
}
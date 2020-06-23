package com.dwibagus.projek_patukang.Adapater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dwibagus.projek_patukang.Api.Url;
import com.dwibagus.projek_patukang.Model.ModelDataAlu;
import com.dwibagus.projek_patukang.R;
import com.dwibagus.projek_patukang.penyewaan;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapaterDataAlu extends RecyclerView.Adapter<AdapaterDataAlu.HolderData> {
    private List<ModelDataAlu> mItems ;
    private Context context;

    public AdapaterDataAlu(Context context, List<ModelDataAlu> items)
    {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_beranda_alu,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        ModelDataAlu md  = mItems.get(position);

        Picasso.get().load(Url.ASSET_PROFIL+md.getFoto_tukang()).into(holder.foto_tukang);
        holder.nama_tukang.setText(md.getNama_tukang());
        holder.keahlian_tukang.setText(md.getKeahlian_tukang());
        holder.harga_tukang.setText(md.getHarga_tukang());

        holder.md = md;

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView nama_tukang,keahlian_tukang,harga_tukang;
        Button toBarang;
        ModelDataAlu md;
        ImageView foto_tukang;

        public  HolderData (View view)
        {
            super(view);

            nama_tukang = view.findViewById(R.id.namatukang);
            keahlian_tukang = view.findViewById(R.id.Keahliantukang);
            harga_tukang = view.findViewById(R.id.hargatukang);
            foto_tukang = view.findViewById(R.id.foto_tukang);

            toBarang = view.findViewById(R.id.btn_sewa);

            toBarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, penyewaan.class);
                    update.putExtra("id_tukang",md.getId_tukang());
                    context.startActivity(update);
                }
            });
        }
    }
}
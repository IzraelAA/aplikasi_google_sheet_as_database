package com.google.ai.googlesheet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private ArrayList<DataSheet> Data;
    public ListAdapter(ArrayList<DataSheet> list){
        this.Data = list;
    }
    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, int position) {
        final DataSheet dataSheet = Data.get(position);

        holder.tvUmur.setText(dataSheet.getUmur());
        holder.tvId.setText(dataSheet.getId());
        holder.tvNama.setText(dataSheet.getNama());
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId;
        public TextView tvNama;
        public TextView tvUmur;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.textid);
            tvNama = itemView.findViewById(R.id.textnama);
            tvUmur = itemView.findViewById(R.id.umur);
        }
    }
}

package com.azhar.pendaftaranvaksin.ui.inputdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.azhar.pendaftaranvaksin.R;

import java.util.ArrayList;

/**
 * Created by Azhar Rivaldi on 20-10-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<ModelInput> modelInputList = new ArrayList<>();
    private Context mContext;

    public DataAdapter(Context context) {
        this.mContext = context;
    }

    public void setDataAdapter(ArrayList<ModelInput> items) {
        modelInputList.clear();
        modelInputList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_vaksin, parent, false);
        return new DataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        final ModelInput data = modelInputList.get(position);

        holder.tvDate.setText(data.getTglvaksin());
        holder.tvNama.setText(data.getNama());
        holder.tvNik.setText(data.getNik());
        holder.tvTanggalLahir.setText(data.getTtl());

    }

    @Override
    public int getItemCount() {
        return modelInputList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDate;
        public TextView tvNama;
        public TextView tvNik;
        public TextView tvTanggalLahir;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvNik = itemView.findViewById(R.id.tvNik);
            tvTanggalLahir = itemView.findViewById(R.id.tvTanggalLahir);
        }
    }

}

package com.azhar.pendaftaranvaksin.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.azhar.pendaftaranvaksin.R;
import com.azhar.pendaftaranvaksin.networking.ApiClient;
import com.azhar.pendaftaranvaksin.ui.inputdata.InputDataActivity;
import com.azhar.pendaftaranvaksin.utils.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

/**
 * Created by Azhar Rivaldi on 16-10-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<ModelMain> modelMainList;
    private Context mContext;

    public MainAdapter(Context context, List<ModelMain> modelMain) {
        this.mContext = context;
        this.modelMainList = modelMain;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelMain data = modelMainList.get(position);

        if (data.getStrPhoto() != null) {
            holder.tvStatus.setText("Available");
            holder.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            Glide.with(mContext)
                    .load(ApiClient.PHOTO + data.getStrPhoto() + ApiClient.API_KEY)
                    .transform(new CenterCrop(), new RoundedCorners(25))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imagePlace);

            holder.cvListMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.namaRS = modelMainList.get(position).getStrName();
                    Intent intent = new Intent(mContext, InputDataActivity.class);
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.tvStatus.setText("Not Available");
            holder.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            Glide.with(mContext)
                    .load(R.drawable.ic_hospital)
                    .transform(new CenterCrop(), new RoundedCorners(25))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imagePlace);

            holder.cvListMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Vaksinasi Tidak Tersedia Disini!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        holder.tvPlaceName.setText(data.getStrName());
        holder.tvVicinity.setText(data.getStrVicinity());

    }

    @Override
    public int getItemCount() {
        return modelMainList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cvListMain;
        public ImageView imagePlace;
        public TextView tvPlaceName;
        public TextView tvVicinity;
        public TextView tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            cvListMain = itemView.findViewById(R.id.cvListMain);
            imagePlace = itemView.findViewById(R.id.imagePlace);
            tvPlaceName = itemView.findViewById(R.id.tvPlaceName);
            tvVicinity = itemView.findViewById(R.id.tvVicinity);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }

}

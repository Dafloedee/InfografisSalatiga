package com.dafloe.infografissalatiga.Kost;

import android.printservice.CustomPrinterIconCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dafloe.infografissalatiga.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//https://www.youtube.com/watch?v=bhhs4bwYyhc
public class DetailKostAdapter extends RecyclerView.Adapter<DetailKostAdapter.kostViewHolder> {

    private ArrayList<ProfilKostDetail> mPorfilKost;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public static class kostViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;


        public kostViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.gambar_detail_kost);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public DetailKostAdapter(ArrayList<ProfilKostDetail>profilKost){
        mPorfilKost = profilKost;
    }
    @NonNull
    @Override
    public kostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent,false);
        kostViewHolder kvh = new kostViewHolder(v, mListener);
        return kvh;
    }

    @Override
    public void onBindViewHolder(@NonNull kostViewHolder holder, int position) {
        ProfilKostDetail sekarang = mPorfilKost.get(position);
        Picasso.get().load(sekarang.getsGambarKost()).into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mPorfilKost.size();
    }
}

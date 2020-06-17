package com.dafloe.infografissalatiga.Kost;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dafloe.infografissalatiga.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//https://www.youtube.com/watch?v=bhhs4bwYyhc
public class KostAdapter extends RecyclerView.Adapter<KostAdapter.kostViewHolder> implements Filterable {

    private ArrayList<ProfilKost> mPorfilKost;
    private ArrayList<ProfilKost> mProfilKostFull;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public static class kostViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;


        public kostViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.gambar_kost);
            mTextView1 = itemView.findViewById(R.id.nama_kost);
            mTextView2 = itemView.findViewById(R.id.alamat_kost);

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

    public KostAdapter(ArrayList<ProfilKost>profilKost){
        mPorfilKost = profilKost;
        mProfilKostFull = new ArrayList<>(profilKost);
    }
    @NonNull
    @Override
    public kostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_kost, parent,false);
        kostViewHolder kvh = new kostViewHolder(v, mListener);
        return kvh;
    }

    @Override
    public void onBindViewHolder(@NonNull kostViewHolder holder, int position) {
        ProfilKost sekarang = mPorfilKost.get(position);
        Picasso.get().load(sekarang.getmImageResourceKost()).into(holder.mImageView);
        holder.mTextView1.setText(sekarang.getmTextKostJudul());
        holder.mTextView2.setText(sekarang.getmTextKostAlamat());


    }

    @Override
    public int getItemCount() {
        return mPorfilKost.size();
    }

    @Override
    public Filter getFilter() {
        return cobaFilter;
    }

    private Filter cobaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ProfilKost> listFilter = new ArrayList<>();
            if (constraint == null || constraint.length() == 0 ){
                listFilter.addAll(mProfilKostFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ProfilKost item : mProfilKostFull){
                    if(item.getmTextKostJudul().toLowerCase().contains(filterPattern)){
                        listFilter.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = listFilter;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mPorfilKost.clear();
            mPorfilKost.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}


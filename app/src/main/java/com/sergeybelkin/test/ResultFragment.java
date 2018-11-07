package com.sergeybelkin.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sergeybelkin.test.pojo.Product;

import java.util.List;

public class ResultFragment extends Fragment {

    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);

        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private LayoutInflater mInflater;
        private List<Product> mProducts;

        MyAdapter(Context context, List<Product> products){
            mInflater = LayoutInflater.from(context);
            mProducts = products;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = mInflater.inflate(R.layout.item, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
            String title = mProducts.get(position).getTitle();
            String url = mProducts.get(position).getImages().get(0).getUrl170x135();
            //if url doesn't work, try uri
            String uri = url.replace("\\/", "/");

            //bind your views here
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mItemTitle;
        ImageView mItemImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemTitle = itemView.findViewById(R.id.itemTitle);
            mItemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}

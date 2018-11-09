package com.sergeybelkin.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sergeybelkin.test.pojo.Product;

import java.util.List;

public class ResultFragment extends Fragment implements Observer{

    RecyclerView mRecyclerView;

    ResultPresenter mResultPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBHelper dbHelper = DBHelper.getInstance(getContext());

        Model model = new Model(dbHelper);
        mResultPresenter = new ResultPresenter(model);
        mResultPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return view;
    }

    public void showProducts(List<Product> products){
        ProductAdapter adapter = new ProductAdapter(getContext(), products);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void handleEvent(String keywords, String category) {
        mResultPresenter.requestSubmitted(keywords, category);
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{

        private LayoutInflater mInflater;
        private List<Product> mProducts;
        private Context mContext;

        ProductAdapter(Context context, List<Product> products){
            mInflater = LayoutInflater.from(context);
            mContext = context;
            mProducts = products;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = mInflater.inflate(R.layout.item, viewGroup, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {
            String title = mProducts.get(position).getTitle();
            String url = mProducts.get(position).getImages().get(0).getUrl170x135();

            productViewHolder.mItemTitle.setText(title);

            Glide.with(mContext)
                .load(url)
                .into(productViewHolder.mItemImage);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView mItemTitle;
        ImageView mItemImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemTitle = itemView.findViewById(R.id.itemTitle);
            mItemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}

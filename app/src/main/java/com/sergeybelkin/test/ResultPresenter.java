package com.sergeybelkin.test;

import android.support.v4.app.Fragment;

import com.sergeybelkin.test.pojo.Product;

import java.util.List;

public class ResultPresenter {

    private ResultFragment mResultFragment;
    private final Model mModel;

    public ResultPresenter(Model model) {
        mModel = model;
    }

    public void attachView(Fragment fragment) {
        mResultFragment = (ResultFragment) fragment;
    }

    public void detachView() {
        mResultFragment = null;
    }

    public void loadProducts(String keywords){
        mModel.loadProducts(keywords, new Model.LoadProductsCallback(){
            @Override
            public void onLoad(List<Product> products) {

            }
        });
    }

    public void requestSubmitted(String keywords){
        loadProducts(keywords);
    }
}

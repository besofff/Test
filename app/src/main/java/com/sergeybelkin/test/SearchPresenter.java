package com.sergeybelkin.test;

import android.support.v4.app.Fragment;

import com.sergeybelkin.test.pojo.Category;

import java.util.List;

public class SearchPresenter {

    private SearchFragment mSearchFragment;
    private final Model mModel;

    public SearchPresenter(Model model) {
        mModel = model;
    }

    public void attachView(Fragment fragment) {
        mSearchFragment = (SearchFragment) fragment;
    }

    public void detachView() {
        mSearchFragment = null;
    }

    public void loadCategories(){
        mModel.loadCategories(new Model.LoadCategoriesCallback() {
            @Override
            public void onLoad(List<Category> categories) {
                mSearchFragment.showCategories(categories);
            }
        });
    }

    public void viewIsReady(){
        loadCategories();
    }
}

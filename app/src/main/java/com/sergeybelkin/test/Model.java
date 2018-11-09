package com.sergeybelkin.test;

import android.util.Log;

import com.sergeybelkin.test.pojo.Category;
import com.sergeybelkin.test.pojo.CategoryList;
import com.sergeybelkin.test.pojo.Product;
import com.sergeybelkin.test.pojo.ProductList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model {

    public static final String BASE_URL = "https://openapi.etsy.com/v2/";
    public static final String API_KEY = "3dhc5qyddrc8p8sbr526m9pc";

    private final DBHelper mDBHelper;

    public Model(DBHelper dbHelper){
        mDBHelper = dbHelper;
    }

    public void loadCategories(final LoadCategoriesCallback callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<CategoryList> categoryListCall = api.getCategories(API_KEY);
        categoryListCall.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                List<Category> categories = response.body().getCategories();
                callback.onLoad(categories);
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
                Log.i("log", "loadCategories failure \n" + t.getMessage());
            }
        });
    }

    public void loadProducts(String keywords, String category, final LoadProductsCallback callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<ProductList> productListCall = api.getProducts(API_KEY, keywords, category);
        productListCall.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                List<Product> products = response.body().getProducts();
                callback.onLoad(products);
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                Log.i("log", "loadProducts failure \n" + t.getMessage());
            }
        });
    }

    interface LoadCategoriesCallback {
        void onLoad(List<Category> categories);
    }

    interface LoadProductsCallback {
        void onLoad(List<Product> products);
    }
}

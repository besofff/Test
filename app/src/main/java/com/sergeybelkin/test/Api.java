package com.sergeybelkin.test;

import com.sergeybelkin.test.pojo.CategoryList;
import com.sergeybelkin.test.pojo.ProductList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("taxonomy/categories?")
    Call<CategoryList> getCategories(@Query("api_key") String apiKey);

    @GET("listings/active?includes=Images:1:0")
    Call<ProductList> getProducts(@Query("api_key") String apiKey,
                                  @Query("keywords") String keywords,
                                  @Query("category") String category);

    @GET("listings/active?includes=Images:1:0")
    Call<ProductList> getProducts(@Query("api_key") String apiKey,
                                  @Query("keywords") String keywords,
                                  @Query("category") String category,
                                  @Query("page") int page);
}

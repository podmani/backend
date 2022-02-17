package org.example.restapi;

import okhttp3.ResponseBody;
import org.example.DTO.GetCategoryResponse;
import org.example.DTO.Product;
import retrofit2.Call;
import retrofit2.http.*;

public interface ProductService {

    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @GET("products")
    Call<Product> getProduct();

    @GET("products/{id}")
    Call<Product> getProductId(@Path("id") int id);

    @PUT("products")
    Call<Product> putProduct(@Body Product createProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

}

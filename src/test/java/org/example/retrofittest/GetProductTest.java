package org.example.retrofittest;

import lombok.SneakyThrows;
import org.example.DTO.GetCategoryResponse;
import org.example.DTO.Product;
import org.example.restapi.CategoryService;
import org.example.restapi.ProductService;
import org.example.utils.RetrofitUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.example.retrofittest.GetCategoryTest.categoryService;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetProductTest {

    static ProductService productService;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @SneakyThrows
    @Test
    void getProductPositiveTest() {
        Response<Product> response = productService.getProduct().execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(1));
        assertThat(response.body().getTitle(), equalTo("Bread"));

    }

}

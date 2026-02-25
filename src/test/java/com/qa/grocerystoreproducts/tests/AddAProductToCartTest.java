package com.qa.grocerystoreproducts.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.ProductsDetails;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;

public class AddAProductToCartTest extends BaseTest {

    String cartId;

    @BeforeMethod
    public void getANewCartId(){
        Response response=restClient.post(GROCERY_STORE_BASEURL_REDIRECTION,GROCERY_STORE_CARTS_ENDPOINTS,null,null,  AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertTrue(Boolean.parseBoolean(response.jsonPath().getString("created")));
        cartId=response.jsonPath().getString("cartId");
    }

    @Test
    public void addAnItemUsingJsonFileTest(){
        //String cartId=getANewCartId();
        File filePath= new File("./src/test/resources/jsons/product.json");
        restClient.post(GROCERY_STORE_BASEURL_REDIRECTION,GROCERY_STORE_CARTS_ENDPOINTS+"/"+cartId+GROCERY_STORE_ADD_ITEM_ENDPOINTS, filePath,null, null,  AuthType.NO_AUTH, ContentType.JSON);
    }

    @Test
    public void addAnItemUsingPOJOTest(){
        //String cartId=getANewCartId();
        //Add a product
        ProductsDetails.Items productDetails = ProductsDetails.Items.builder().productId(8753).quantity(1).build();
        Response response = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION,GROCERY_STORE_CARTS_ENDPOINTS+"/"+cartId+GROCERY_STORE_ADD_ITEM_ENDPOINTS, productDetails,null, null,  AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertTrue(response.statusLine().contains("201"));

    }

    @Test
    public void addAnItemUsingJSONStringTest(){
        //String cartId=getANewCartId();
        String json="{\n" +
                "  \"productId\":8753\n" +
                "}";
        Response response = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION,GROCERY_STORE_CARTS_ENDPOINTS+"/"+cartId+GROCERY_STORE_ADD_ITEM_ENDPOINTS, json,null, null,  AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertTrue(response.statusLine().contains("201"));
    }


}

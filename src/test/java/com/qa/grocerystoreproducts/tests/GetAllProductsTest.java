package com.qa.grocerystoreproducts.tests;

import com.qa.api.constants.AuthType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import  com.qa.api.base.BaseTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllProductsTest extends BaseTest{

    @Test
    public void getAllProductsTest(){
        Response response= restClient.get(GROCERY_STORE_BASEURL, GROCERY_STORE_PRODUCTS_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertTrue(response.statusLine().contains("OK"));
    }

    @Test
    public void getProductsForQueryParamTest(){
        Map<String, Object> queryParam= new HashMap<String, Object>();
        queryParam.put("category","dairy");

        Response response= restClient.get(GROCERY_STORE_BASEURL, GROCERY_STORE_PRODUCTS_ENDPOINTS, queryParam, null, AuthType.NO_AUTH, ContentType.JSON);

        Assert.assertTrue(response.statusLine().contains("OK"));

        List<String> category=response.then().extract().path("category");
        Assert.assertEquals(category.size(),4);

        for(int i=0;i<category.size();i++){
            Assert.assertTrue(category.get(i).contains("dairy"));
        }
    }

    @Test
    public void getAProductUsingIDTest(){
        int userid=9482;
        Response response= restClient.get(GROCERY_STORE_BASEURL, GROCERY_STORE_PRODUCTS_ENDPOINTS+"/"+userid, null, null, AuthType.NO_AUTH, ContentType.JSON);

        Assert.assertTrue(response.statusLine().contains("OK"));
        Assert.assertEquals(response.jsonPath().getInt("id"), userid);
    }
}

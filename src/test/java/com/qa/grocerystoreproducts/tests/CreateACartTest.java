package com.qa.grocerystoreproducts.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateACartTest extends BaseTest {

    @Test
    public void createANewCartTest(){
        Response response=restClient.post(GROCERY_STORE_BASEURL_REDIRECTION,GROCERY_STORE_CARTS_ENDPOINTS,null,null,  AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertTrue(response.statusLine().contains("Created"));
    }
}

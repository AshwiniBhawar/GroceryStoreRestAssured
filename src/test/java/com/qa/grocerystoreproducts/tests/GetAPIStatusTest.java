package com.qa.grocerystoreproducts.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetAPIStatusTest extends BaseTest {

    @Test
    public void getStatusTest(){
        Response response= restClient.get(GROCERY_STORE_BASEURL, GROCERY_STORE_STATUS_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(response.then().extract().path("status"), "UP");
        Assert.assertTrue(response.statusLine().contains("OK"));
    }
}

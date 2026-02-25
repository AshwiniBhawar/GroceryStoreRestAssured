package com.qa.grocerystoreorders.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.RegisterNewClient;
import com.qa.api.utils.StringUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterNewClientTest extends BaseTest {

    String tokenId;

    @Test
    public void generateToken() {
        RegisterNewClient newClient= RegisterNewClient.builder().clientName("Ash B").clientEmail(StringUtil.getRandomEmailId()).build();
        Response postRes=restClient.post(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_NEW_CLIENT_ENDPOINTS,newClient, null, null, AuthType.NO_AUTH, ContentType.JSON);

        Assert.assertEquals(postRes.statusCode(),201);
        Assert.assertTrue(postRes.statusLine().contains("Created"));
        tokenId= postRes.jsonPath().getString("accessToken");
        System.out.println("Access token is :"+tokenId);
    }
}

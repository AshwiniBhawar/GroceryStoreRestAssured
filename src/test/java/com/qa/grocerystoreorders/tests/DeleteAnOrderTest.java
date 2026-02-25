package com.qa.grocerystoreorders.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.config.ConfigManager;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.ProductsDetails;
import com.qa.api.pojo.RegisterNewClient;
import com.qa.api.pojo.CreateOrder;
import com.qa.api.utils.StringUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class DeleteAnOrderTest extends BaseTest {

    RegisterNewClient clientLoginDetails;

    @BeforeMethod
    public void registerAClient(){
        //Register a client
        clientLoginDetails = RegisterNewClient.builder().clientName("Ash B").clientEmail(StringUtil.getRandomEmailId()).build();
        Response postResponse = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_NEW_CLIENT_ENDPOINTS, clientLoginDetails, null, null, AuthType.NO_AUTH, ContentType.JSON);

        Assert.assertEquals(postResponse.statusCode(), 201);
        Assert.assertTrue(postResponse.statusLine().contains("Created"));
        String tokenId = postResponse.jsonPath().getString("accessToken");
        System.out.println("Access token is :" + tokenId);

        //set token in prop file
        ConfigManager.setProp("bearertoken", tokenId);
    }

    @Test
    public void deleteAnOrderTest() {

        Response createCartResponse = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_CARTS_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(createCartResponse.statusCode(), 201);
        Assert.assertTrue(createCartResponse.statusLine().contains("Created"));
        String cartId = createCartResponse.jsonPath().getString("cartId");

        //Add a product
        ProductsDetails.Items productDetails = ProductsDetails.Items.builder().productId(8753).quantity(1).build();
        Response addProductResponse = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_CARTS_ENDPOINTS + "/" + cartId + GROCERY_STORE_ADD_ITEM_ENDPOINTS, productDetails, null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(addProductResponse.statusCode(), 201);
        Assert.assertTrue(addProductResponse.statusLine().contains("Created"));

        //create an order
        CreateOrder createOrderDetails = CreateOrder.builder().customerName("Ash B").cartId(cartId).build();
        Response createOrderResponse = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_ORDERS_ENDPOINTS, createOrderDetails, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(createOrderResponse.statusCode(),201);
        Assert.assertTrue(createOrderResponse.statusLine().contains("Created"));
        String orderId = createOrderResponse.jsonPath().getString("orderId");
        System.out.println("Order id is :" + orderId);

        //get an order
        Map<String, Object> pathParam= new HashMap<>();
        pathParam.put("orderId", orderId);

        Response getOrderResponse = restClient.get(GROCERY_STORE_BASEURL_REDIRECTION,GROCERY_STORE_ORDER_ENDPOINTS,null, pathParam,  AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getOrderResponse.statusCode(),200);
        Assert.assertTrue(getOrderResponse.statusLine().contains("OK"));
        Assert.assertEquals(getOrderResponse.jsonPath().getString("id"), orderId);
        Assert.assertEquals(getOrderResponse.jsonPath().getString("customerName"), clientLoginDetails.getClientName());

        //update an order
        CreateOrder updateOrderDetails = CreateOrder.builder().customerName("Ashly B").comment("Updated name").build();
        Response updateOrderResponse=restClient.patch(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_ORDER_ENDPOINTS, updateOrderDetails, null, pathParam, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(updateOrderResponse.statusCode(),204);
        Assert.assertTrue(updateOrderResponse.statusLine().contains("No Content"));

        //get an updated order
        Response getUpdatedOrderResponse = restClient.get(GROCERY_STORE_BASEURL_REDIRECTION,GROCERY_STORE_ORDER_ENDPOINTS,null, pathParam,  AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getUpdatedOrderResponse.statusCode(),200);
        Assert.assertTrue(getUpdatedOrderResponse.statusLine().contains("OK"));
        Assert.assertEquals(getUpdatedOrderResponse.jsonPath().getString("id"), orderId);
        Assert.assertEquals(getUpdatedOrderResponse.jsonPath().getString("customerName"), updateOrderDetails.getCustomerName());
        Assert.assertEquals(getUpdatedOrderResponse.jsonPath().getString("comment"), updateOrderDetails.getComment());

        //delete an order
        Response deleteOrderResponse=restClient.delete(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_ORDER_ENDPOINTS, null, pathParam, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(deleteOrderResponse.statusCode(),204);
        Assert.assertTrue(deleteOrderResponse.statusLine().contains("No Content"));

        //get a deleted order
        Response getDeletedOrderResponse = restClient.get(GROCERY_STORE_BASEURL_REDIRECTION,GROCERY_STORE_ORDER_ENDPOINTS,null, pathParam,  AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getDeletedOrderResponse.statusCode(),404);
        Assert.assertTrue(getDeletedOrderResponse.statusLine().contains("Not Found"));
        Assert.assertEquals(getDeletedOrderResponse.jsonPath().getString("error"), "No order with id "+orderId+".");
    }
}

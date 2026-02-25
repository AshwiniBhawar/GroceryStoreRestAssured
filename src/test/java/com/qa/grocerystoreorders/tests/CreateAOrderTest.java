package com.qa.grocerystoreorders.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.config.ConfigManager;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.ProductsDetails;
import com.qa.api.pojo.RegisterNewClient;
import com.qa.api.pojo.CreateOrder;
import com.qa.api.utils.ExcelUtil;
import com.qa.api.utils.StringUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CreateAOrderTest extends BaseTest {
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

    @DataProvider
    public Object[][] getDataFromExcel(){
        return ExcelUtil.getData(AppConstants.EXCEL_FILE_NAME,AppConstants.EXCEL_SHEET_NAME);
    }

    @Test(dataProvider="getDataFromExcel")
    public void createAnOrderUsingDPExcelTest(String productId, String quantity) {

        Response createCartResponse = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_CARTS_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(createCartResponse.statusCode(), 201);
        Assert.assertTrue(createCartResponse.statusLine().contains("Created"));
        String cartId = createCartResponse.jsonPath().getString("cartId");

        //Add a product
        ProductsDetails.Items productDetails = ProductsDetails.Items.builder().productId(Integer.valueOf(productId)).quantity(Integer.valueOf(quantity) ).build();
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
    }

    @DataProvider
    public Object[][] getData(){
        return new Object[][] {
                {5478,2},{4641,3}
        };
    }

    @Test(dataProvider="getData")
    public void createAnOrderUsingDPTest(int productId, int quantity) {

        Response createCartResponse = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_CARTS_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(createCartResponse.statusCode(), 201);
        Assert.assertTrue(createCartResponse.statusLine().contains("Created"));
        String cartId = createCartResponse.jsonPath().getString("cartId");

        //Add a product
        ProductsDetails.Items productDetails = ProductsDetails.Items.builder().productId(productId).quantity(quantity).build();
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
    }

    @Test
    public void createAnOrdeTest() {

        Response createCartResponse = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_CARTS_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(createCartResponse.statusCode(), 201);
        Assert.assertTrue(createCartResponse.statusLine().contains("Created"));
        String cartId = createCartResponse.jsonPath().getString("cartId");

        //Add a product
        ProductsDetails.Items productDetails = ProductsDetails.Items.builder().productId(5478).quantity(1).build();
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

    }

}

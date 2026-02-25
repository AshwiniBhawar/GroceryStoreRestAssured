package com.qa.schema.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.config.ConfigManager;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.CreateOrder;
import com.qa.api.pojo.ProductsDetails;
import com.qa.api.pojo.RegisterNewClient;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class GetAllOrdersSchemaValidateTest extends BaseTest {
    public static List<ProductsDetails.Items> itemsList = new ArrayList<>();

    @BeforeMethod
    public void registerAClient() {
        //Register a client
        RegisterNewClient clientLoginDetails = RegisterNewClient.builder().clientName("Ash B").clientEmail(StringUtil.getRandomEmailId()).build();
        Response postResponse = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_NEW_CLIENT_ENDPOINTS, clientLoginDetails, null, null, AuthType.NO_AUTH, ContentType.JSON);

        Assert.assertEquals(postResponse.statusCode(), 201);
        Assert.assertTrue(postResponse.statusLine().contains("Created"));
        String tokenId = postResponse.jsonPath().getString("accessToken");
        System.out.println("Access token is :" + tokenId);

        //set token in prop file
        ConfigManager.setProp("bearertoken", tokenId);
    }

    @Test
    public void AddAProductAndGetAllOrdersTest() {

        //create a cart
        Response createCartResponse = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_CARTS_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(createCartResponse.statusCode(), 201);
        Assert.assertTrue(createCartResponse.statusLine().contains("Created"));
        String cartId = createCartResponse.jsonPath().getString("cartId");

        //Add a product1
        ProductsDetails.Items itemDetails = ProductsDetails.Items.builder().productId(5478).quantity(2).build();
        Response addProductResponse = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_CARTS_ENDPOINTS + "/" + cartId + GROCERY_STORE_ADD_ITEM_ENDPOINTS, itemDetails, null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(addProductResponse.statusCode(), 201);
        Assert.assertTrue(addProductResponse.statusLine().contains("Created"));

        //create an order
        CreateOrder createOrderDetails = CreateOrder.builder().customerName("Ash B").cartId(cartId).build();

        Response createOrderResponse = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_ORDERS_ENDPOINTS, createOrderDetails, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(createOrderResponse.statusCode(), 201);
        Assert.assertTrue(createOrderResponse.statusLine().contains("Created"));
        String orderId = createOrderResponse.jsonPath().getString("orderId");
        System.out.println("Order id is :" + orderId);

        //get all orders response
        Response getAllOrdersResponse = restClient.get(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_ORDERS_ENDPOINTS, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getAllOrdersResponse.statusCode(), 200);
        Assert.assertTrue(getAllOrdersResponse.statusLine().contains("OK"));
        Assert.assertTrue(SchemaValidator.validateJSONSchema(getAllOrdersResponse, "schema/orders.json"));
    }
}
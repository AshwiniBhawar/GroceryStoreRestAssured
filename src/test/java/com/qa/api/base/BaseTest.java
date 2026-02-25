package com.qa.api.base;

import com.qa.api.client.RestClient;
import com.qa.api.config.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

//@Listeners(ChainTestListener.class)
public class BaseTest {
    protected RestClient restClient;
    /************************************API Base URLs************************************/
    protected final static String GROCERY_STORE_BASEURL= "https://simple-grocery-store-api.glitch.me";
    protected final static String GROCERY_STORE_BASEURL_REDIRECTION="https://simple-grocery-store-api.click";
    protected final static String BASIC_AUTH_URL="https://the-internet.herokuapp.com";
    protected final static String GOREST_USER_XML_URL="https://gorest.co.in";

    /************************************API EndPoints URLs************************************/
    protected final static String GROCERY_STORE_STATUS_ENDPOINTS= "/status";
    protected final static String GROCERY_STORE_PRODUCTS_ENDPOINTS= "/products";
    protected final static String GROCERY_STORE_CARTS_ENDPOINTS= "/carts";
    protected final static String GROCERY_STORE_ADD_ITEM_ENDPOINTS= "/items";
    protected final static String GROCERY_STORE_ORDERS_ENDPOINTS="/orders";
    protected final static String GROCERY_STORE_ORDER_ENDPOINTS="/orders/{orderId}";
    protected final static String GROCERY_STORE_NEW_CLIENT_ENDPOINTS="/api-clients";
    protected final static String GOREST_USER_XML_ENDPOINTS="/public/v2/users.xml";
    protected final static String BASIC_AUTH_ENDPOINTS="/basic_auth";

    @BeforeSuite
    public void setUpAllureReport(){
        RestAssured.filters(new AllureRestAssured());
    }

    @BeforeTest
    public void setUp(){
        restClient= new RestClient();
        ConfigManager.initProp();
    }
}

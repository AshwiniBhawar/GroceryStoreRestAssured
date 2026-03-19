package com.qa.api.base;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.qa.api.client.RestClient;
import com.qa.api.config.ConfigManager;
import com.qa.api.mocking.WireMockSetup;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

//@Listeners(ChainTestListener.class)
public class BaseTest {
    protected RestClient restClient;

    /************************************API Base URLs Initialization************************************/
    protected static String GROCERY_STORE_BASEURL;
    protected static String GROCERY_STORE_BASEURL_REDIRECTION;
    protected static String BASIC_AUTH_URL;
    protected static String GOREST_USER_XML_URL;
    protected static String MOCK_BASE_URL;

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
    protected final static String MOCK_USERS_ENDPOINTS="/api/users";

    @BeforeSuite
    public void setUpAllureReport(){
        RestAssured.filters(new AllureRestAssured());
    }

    @BeforeTest
    public void setUp(){
        restClient= new RestClient();
        ConfigManager.initProp();
        WireMockSetup.startWireMockServer();

        /************************************API Base URLs Declaration************************************/
       GROCERY_STORE_BASEURL= ConfigManager.getProp("grocery_store_url");
       GROCERY_STORE_BASEURL_REDIRECTION=ConfigManager.getProp("grocery_store_redirection_url");
       BASIC_AUTH_URL=ConfigManager.getProp("basic_auth_url");
       GOREST_USER_XML_URL=ConfigManager.getProp("gorest_user_xml_url");
       MOCK_BASE_URL=ConfigManager.getProp("mock_base_url");
    }

    @AfterTest
    public void stopWireMockServer(){
        WireMockSetup.stopWireMockServer();
    }
}

package com.qa.mocking.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.mocking.APIMocks;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

public class MockGetUserTest extends BaseTest {

    @Test
    public void getDummyUserMockAPITest(){
        APIMocks.defineGetUserMock();
        Response response=restClient.get(MOCK_BASE_URL, MOCK_USERS_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.ANY);
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void getDummyUserMockUsingJSONAPITest(){
        APIMocks.defineGetUserMockWithJSON();
        Response response=restClient.get(MOCK_BASE_URL, MOCK_USERS_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.ANY);
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void getDummyUserMockUsingQueryParamAPITest(){
        APIMocks.defineGetUserMockWithQueryParam();
        Map<String, Object> queryParam= new HashMap<>();
        queryParam.put("name","ash");
        Response response=restClient.get(MOCK_BASE_URL, MOCK_USERS_ENDPOINTS, queryParam, null, AuthType.NO_AUTH, ContentType.ANY);
        response.then().assertThat().statusCode(200);
    }
}

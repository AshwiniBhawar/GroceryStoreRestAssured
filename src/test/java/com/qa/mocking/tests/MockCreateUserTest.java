package com.qa.mocking.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.mocking.APIMocks;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

public class MockCreateUserTest extends BaseTest {

    @Test
    public void createDummyUserAPITest(){
        APIMocks.defineCreateUserMock();
        File filePath= new File("./src/test/resources/jsons/mockuser.json");
        Response response=restClient.post(MOCK_BASE_URL,MOCK_USERS_ENDPOINTS, filePath,null,null, AuthType.NO_AUTH, ContentType.JSON);
        response.then().assertThat().statusCode(201);
    }
}

package com.qa.schema.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.SchemaValidator;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoRestUsersSchemaValidateTest extends BaseTest {

     @Test
     public void getUserDataTest() {
         Response response = restClient.get(GOREST_USER_XML_URL, GOREST_USER_XML_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.ANY);
         Assert.assertEquals(response.statusCode(),200);
         Assert.assertTrue(response.statusLine().contains("OK"));
         SchemaValidator.validateXMLSchema(response, "schema/gorestusers.xml");
    }
}

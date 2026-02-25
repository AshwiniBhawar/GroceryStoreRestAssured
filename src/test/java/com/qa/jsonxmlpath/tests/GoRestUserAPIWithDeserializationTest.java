package com.qa.jsonxmlpath.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.UserXML;
import com.qa.api.utils.XmlUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GoRestUserAPIWithDeserializationTest extends BaseTest {

    //deserialization-fetch data
    @Test
    public void getUserDataTest() {
        Response response = restClient.get(GOREST_USER_XML_URL, GOREST_USER_XML_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.ANY);
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertTrue(response.statusLine().contains("OK"));
        UserXML userXml=XmlUtil.deserialize(response, UserXML.class);
        System.out.println("Object type is:"+userXml.getType());
        System.out.println("-------------------------------------------------------------");
        List<UserXML.ObjectData> obj=userXml.getObjects();

        for(int i=0;i<obj.size();i++){
            System.out.println("Id is:"+obj.get(i).getId());
            System.out.println("Id type is:"+obj.get(i).getId().getType());
            System.out.println("Name is:"+obj.get(i).getName());
            System.out.println("Email is:"+obj.get(i).getEmail());
            System.out.println("Gender is:"+obj.get(i).getGender());
            System.out.println("Status is:"+obj.get(i).getStatus());
            System.out.println("-------------------------------------------------------------");
        }

    }
}

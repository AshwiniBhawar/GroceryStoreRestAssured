package com.qa.jsonxmlpath.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.XMLPathValidatorUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GoRestUserAPIXmlPathTest extends BaseTest {

    //fetch data using xml query
    @Test
    public void getUserDataTest() {
        Response response = restClient.get(GOREST_USER_XML_URL, GOREST_USER_XML_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.ANY);
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertTrue(response.statusLine().contains("OK"));
        String type = XMLPathValidatorUtil.read(response, "objects.@type");
        System.out.println("Object type:" + type);

        List<Integer> idList = XMLPathValidatorUtil.readList(response, "objects.object.id");
        System.out.println(idList);

        for (int i=0;i<idList.size();i++) {
            System.out.println("Id: "+idList.get(i));
            System.out.println("Name: "+(XMLPathValidatorUtil.readList(response, "objects.object.name").get(i)));
            System.out.println("Email: "+(XMLPathValidatorUtil.readList(response, "objects.object.email").get(i)));
            System.out.println("Gender: "+(XMLPathValidatorUtil.readList(response, "objects.object.gender").get(i)));
            System.out.println("Status: "+(XMLPathValidatorUtil.readList(response, "objects.object.status").get(i)));
            System.out.println("-------------------------------------------------------------------------------");
        }
    }

    @Test
    public void getUserDataUsingQueryTest() {
        Response response = restClient.get(GOREST_USER_XML_URL, GOREST_USER_XML_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.ANY);
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertTrue(response.statusLine().contains("OK"));
        String type = XMLPathValidatorUtil.read(response, "objects.@type");
        System.out.println("Object type:" + type);

        List<Integer> idList = XMLPathValidatorUtil.readList(response, "objects.object.id");
        System.out.println(idList);

        String name = XMLPathValidatorUtil.read(response, "**.find{it.id==8374589 }.name");
        System.out.println(name);

        List<String> activeNamesList = XMLPathValidatorUtil.readList(response, "**.findAll{it.status=='active' }.name");
        System.out.println("Active Names: "+activeNamesList);

        List<String> inactiveNamesList = XMLPathValidatorUtil.readList(response, "**.findAll{it.status=='inactive' }.name");
        System.out.println("In-Active Names: "+inactiveNamesList);

        List<String> activeList = XMLPathValidatorUtil.readList(response, "**.findAll{it.status=='active' }");
        System.out.println("Active List: "+activeList);

        List<String> typeIdList = XMLPathValidatorUtil.readList(response, "objects.object.id.findAll{it.@type=='integer' }");
        System.out.println("Type Ids List: "+typeIdList);

    }
}

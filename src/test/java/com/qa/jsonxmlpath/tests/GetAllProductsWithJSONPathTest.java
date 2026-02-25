package com.qa.jsonxmlpath.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidatorUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;

public class GetAllProductsWithJSONPathTest extends BaseTest{

    @Test
    public void getAllProductsTest(){
        Response response= restClient.get(GROCERY_STORE_BASEURL, GROCERY_STORE_PRODUCTS_ENDPOINTS, null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertTrue(response.statusLine().contains("OK"));

        List<Integer> ids= JsonPathValidatorUtil.readList(response, "$.[*].id");
        System.out.println(ids);

        List<Map<String,Object>> list1= JsonPathValidatorUtil.readListOfMaps(response, "$.[*].['id','name']");
        System.out.println(list1);
        for(Map<String,Object> result:list1){
            System.out.println("Id is:"+result.get("id")+" ==== Name is:"+result.get("name"));
        }

        System.out.println("=====================================================================");
        List<Map<String,Object>> list2= JsonPathValidatorUtil.readListOfMaps(response, "$.[*]");
        System.out.println(list2);
        for(Map<String,Object> result:list2){
            System.out.println("Id is:"+result.get("id")+" ==== Category is:"+result.get("category")+" ==== Name is:"+result.get("name")+" ==== InStock:"+result.get("inStock"));
        }
        System.out.println("=====================================================================");

        List<Map<String,Object>> list3= JsonPathValidatorUtil.readListOfMaps(response, "$[?(@.category=='candy')].['id','name']");
        System.out.println(list3);
        for(Map<String,Object> result:list3){
            System.out.println("Id is:"+result.get("id")+" ==== Name is:"+result.get("name"));
        }
        System.out.println("=====================================================================");

        List<Map<String,Object>> list4= JsonPathValidatorUtil.readListOfMaps(response, "$.[?(@.inStock==false)]");
        System.out.println(list4);

        System.out.println("=====================================================================");

        List<Object> list5= JsonPathValidatorUtil.readList(response, "$.[?(@.name=~ /^Cream.*/i)]");
        System.out.println(list5);

        System.out.println("=====================================================================");

        List<Object> list6= JsonPathValidatorUtil.readList(response, "$.[?(@.name=~ /.*Cheese$/i)]");
        System.out.println(list6);

        System.out.println("=====================================================================");

        List<Map<String,Object>> list7= JsonPathValidatorUtil.readListOfMaps(response, "$.[?(@.category=~ /.*dairy.*/i)]");
        System.out.println(list7);

    }
}

package com.qa.api.utils;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class JsonPathValidatorUtil {

    private static ReadContext getReadContext(Response response){
        ReadContext ctx= JsonPath.parse(response.getBody().asString());
        return ctx;
    }

    public static <T> T read(Response response, String jsonPathExpression){
        ReadContext ctx=getReadContext(response);
        return ctx.read(jsonPathExpression);
    }

    public static <T> List<T> readList(Response response, String jsonPathExpression){
        ReadContext ctx=getReadContext(response);
        return ctx.read(jsonPathExpression);
    }
    public static <T> List<Map<String,T>> readListOfMaps(Response response, String jsonPathExpression){
        ReadContext ctx=getReadContext(response);
        return ctx.read(jsonPathExpression);
    }

}

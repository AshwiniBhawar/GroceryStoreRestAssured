package com.qa.api.utils;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.codehaus.stax2.validation.XMLValidationSchema;

public class SchemaValidator {

    public static boolean validateJSONSchema(Response response, String schemaFileName){
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaFileName));
            System.out.println("Schema validation is passed:"+schemaFileName);
            return true;
        }catch(Exception e){
            System.out.println("Schema validation is failed:"+schemaFileName);
            throw new RuntimeException("Schema validation is failed"+e.getMessage());
        }
    }

    public static boolean validateXMLSchema(Response response, String schemaFileName){
        try {
            response.then().assertThat().body(RestAssuredMatchers.matchesXsdInClasspath(schemaFileName));
            System.out.println("Schema validation is passed:"+schemaFileName);
            return true;
        }catch(Exception e){
            System.out.println("Schema validation is failed:"+schemaFileName);
            throw new RuntimeException("Schema validation is failed"+e.getMessage());
        }
    }
}

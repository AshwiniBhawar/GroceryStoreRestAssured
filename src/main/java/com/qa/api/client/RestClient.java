package com.qa.api.client;

import com.qa.api.config.ConfigManager;
import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.APIException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.io.File;
import java.util.Base64;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class RestClient {

    private RequestSpecification reqSpec;

    //define all response spec
    private ResponseSpecification resSpec200=expect().statusCode(200);
    private ResponseSpecification resSpec201=expect().statusCode(201);
    private ResponseSpecification resSpec200or201=expect().statusCode(anyOf(equalTo(200),equalTo(201)));
    private ResponseSpecification resSpec400=expect().statusCode(400);
    private ResponseSpecification resSpec404=expect().statusCode(404);
    private ResponseSpecification resSpec200or404=expect().statusCode(anyOf(equalTo(200),equalTo(404)));
    private ResponseSpecification resSpec204= expect().statusCode(204);
    private ResponseSpecification resSpec200or204= expect().statusCode(anyOf(equalTo(200),equalTo(204)));
    private ResponseSpecification resSpec201or204= expect().statusCode(anyOf(equalTo(201),equalTo(204)));

   //setup request
    private RequestSpecification setUpRequest(String baseURl, AuthType authType, ContentType contentType){
        RequestSpecification reqSpec=RestAssured.given().log().all()
                .urlEncodingEnabled(false)
                .baseUri(baseURl)
                .contentType(contentType)
                .accept(contentType);

        switch (authType){
            case BEARER_TOKEN:
                reqSpec.header("Authorization", "Bearer "+ConfigManager.getProp("bearertoken") );
                break;
            case BASIC_AUTH:
                reqSpec.header("Authorization", "Basic "+generateAuthToken());
                break;
            case API_KEY:
                reqSpec.header("x-api-key", "apiKey" );
                break;
            case NO_AUTH:
                System.out.println("Auth is not required");
                break;

            default:
                System.out.println("This auth is not supported. Please pass the right auth type.");
                throw new APIException("===================Invalid Auth===================");
        }

        return reqSpec;
    }

    //generate basic authtoken
    public String generateAuthToken(){
        String credentials=ConfigManager.getProp("basicauthusername")+":"+ConfigManager.getProp("basicauthpassword");
        return Base64.getEncoder().encodeToString(credentials.getBytes());
        //Base64.getDecoder().decode("");
    }

    //apply query and path param
    private void applyParams(RequestSpecification request, Map<String, Object> queryParams, Map<String, Object> pathParams){
       if(queryParams!= null) {
           request.queryParams(queryParams);
       }

       if(pathParams!= null) {
            request.pathParams(pathParams);
       }
    }

    //CRUD-GET

    /**
     * This method is used to call GET APIs
     * @param baseUrl
     * @param endPoint
     * @param queryParams
     * @param pathParams
     * @param authType
     * @param contentType
     * @return It return the GET API call response
     */

    public Response get(String baseUrl, String endPoint, Map<String, Object> queryParams,
                        Map<String, Object> pathParams, AuthType authType, ContentType contentType){
        RequestSpecification reqSpec=setUpRequest(baseUrl, authType, contentType);
        applyParams(reqSpec, queryParams, pathParams);
        Response response= reqSpec.get(endPoint).then().log().all()
                                    .spec(resSpec200or404).extract().response();
        response.prettyPrint();
        return response;
    }

    //CRUD-POST-String Body
    public <T> Response post(String baseUrl, String endPoint, T body, Map<String, Object> queryParams,
                        Map<String, Object> pathParams, AuthType authType, ContentType contentType){
        RequestSpecification reqSpec=setUpRequest(baseUrl, authType, contentType);
        applyParams(reqSpec, queryParams, pathParams);
        Response response= reqSpec.body(body).post(endPoint).then().log().all()
                .spec(resSpec201).extract().response();
        response.prettyPrint();
        return response;
    }

    //CRUD-POST-File Body
    public Response post(String baseUrl, String endPoint, File file, Map<String, Object> queryParams,
                                     Map<String, Object> pathParams, AuthType authType, ContentType contentType){
        RequestSpecification reqSpec=setUpRequest(baseUrl, authType, contentType);
        applyParams(reqSpec, queryParams, pathParams);
        Response response= reqSpec.body(file).post(endPoint).then().log().all()
                .spec(resSpec201or204).extract().response();
        response.prettyPrint();
        return response;
    }

    //CRUD-POST-Without Body
    public Response post(String baseUrl, String endPoint, Map<String, Object> queryParams,
                         Map<String, Object> pathParams, AuthType authType, ContentType contentType){
        RequestSpecification reqSpec=setUpRequest(baseUrl, authType, contentType);
        applyParams(reqSpec, queryParams, pathParams);
        Response response= reqSpec.post(endPoint).then().log().all()
                .spec(resSpec201).extract().response();
        response.prettyPrint();
        return response;
    }

    //CRUD-POST-OAuth details
    public Response post(String baseUrl, String endPoint, String clientId, String clientSecret, String grantType, ContentType contentType){
        Response response = RestAssured.given()
                .contentType(contentType)
                .formParam("client_id",clientId)
                .formParam("client_secret",clientSecret)
                .formParam("grant_type", grantType)
                .when()
                .post(baseUrl+endPoint);
        return response;
    }

    //CRUD-PUT
    public <T> Response put(String baseUrl, String endPoint, T body, Map<String, Object> queryParams,
                                    Map<String, Object> pathParams, AuthType authType, ContentType contentType){
        RequestSpecification reqSpec=setUpRequest(baseUrl, authType, contentType);
        applyParams(reqSpec, queryParams, pathParams);
        Response response= reqSpec.body(body).put(endPoint).then().log().all()
                .spec(resSpec200or204).extract().response();
        response.prettyPrint();
        return response;
    }

    //CRUD-PATCH
    public <T> Response patch(String baseUrl, String endPoint, T body, Map<String, Object> queryParams,
                                   Map<String, Object> pathParams, AuthType authType, ContentType contentType){
        RequestSpecification reqSpec=setUpRequest(baseUrl, authType, contentType);
        applyParams(reqSpec, queryParams, pathParams);
        Response response= reqSpec.body(body).patch(endPoint).then().log().all()
                .spec(resSpec200or204).extract().response();
        response.prettyPrint();
        return response;
    }

    //CRUD-DELETE
    public Response delete(String baseUrl, String endPoint, Map<String, Object> queryParams,
                                         Map<String, Object> pathParams, AuthType authType, ContentType contentType){
        RequestSpecification reqSpec=setUpRequest(baseUrl, authType, contentType);
        applyParams(reqSpec, queryParams, pathParams);
        Response response= reqSpec.delete(endPoint).then().log().all()
                .spec(resSpec204).extract().response();
        response.prettyPrint();
        return response;
    }

}

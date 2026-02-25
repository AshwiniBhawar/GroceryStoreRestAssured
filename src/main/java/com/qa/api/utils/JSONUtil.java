package com.qa.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

public class JSONUtil {

    private static ObjectMapper mapper= new ObjectMapper();

    public static <T> T deserialize(Response response, Class<T> className){
        try {
            return mapper.readValue(response.getBody().asString(), className);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Deserialization is failed.."+className.getName());      //JSOnProcessingException, JSONMappingException
        }
    }
}

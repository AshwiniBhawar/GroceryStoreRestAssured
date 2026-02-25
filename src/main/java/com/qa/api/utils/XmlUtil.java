package com.qa.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.response.Response;

public class XmlUtil {

    private static XmlMapper mapper= new XmlMapper();

    public static <T> T deserialize(Response response, Class<T> className){
        try {
            return mapper.readValue(response.getBody().asString(), className);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Deserialization is failed.."+className.getName());      //JSOnProcessingException, JSONMappingException
        }
    }
}

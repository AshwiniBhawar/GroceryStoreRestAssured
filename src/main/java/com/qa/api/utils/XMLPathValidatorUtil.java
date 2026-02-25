package com.qa.api.utils;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import java.util.List;

public class XMLPathValidatorUtil {

    private static XmlPath getXmlPath(Response response) {
        return new XmlPath(response.getBody().asString());
    }

    public static <T> T read(Response response, String xmlPathExpression) {
        XmlPath xmlPath = getXmlPath(response);
        return xmlPath.get(xmlPathExpression);
    }

    public static <T> List<T> readList(Response response, String xmlPathExpression) {
        XmlPath xmlPath = getXmlPath(response);
        return xmlPath.getList(xmlPathExpression);
    }
}

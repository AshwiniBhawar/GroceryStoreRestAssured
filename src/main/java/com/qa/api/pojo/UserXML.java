package com.qa.api.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;

import java.util.List;


@Data
@JacksonXmlRootElement(localName="objects")
public class UserXML {

    @JacksonXmlProperty(isAttribute=true, localName="type")
    private String type;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName="object")
    private List<ObjectData> objects;

    @Data
    public static class ObjectData{

        @JacksonXmlProperty(localName = "id")
        private IdWrap id;

        @JacksonXmlProperty(localName = "name")
        private String name;

        @JacksonXmlProperty(localName = "email")
        private String email;

        @JacksonXmlProperty(localName = "gender")
        private String gender;

        @JacksonXmlProperty(localName = "status")
        private String status;

        @Data
        public static class IdWrap{

            @JacksonXmlText
            private int value;

            @JacksonXmlProperty(localName="type",isAttribute = true)
            private String type;

        }
    }
}

package com.qa.api.config;

import com.qa.api.exceptions.APIException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static Properties prop=new Properties();

    public static void initProp() {
        FileInputStream fip = null;
        String envName = System.getProperty("env", "qa");
        System.out.println("Env name is :"+envName);
        try {
                switch (envName.toLowerCase().trim()) {
                    case "qa":
                        fip = new FileInputStream("./src/test/resources/config/config_qa.properties");
                        break;
                    case "dev":
                        fip = new FileInputStream("./src/test/resources/config/config_dev.properties");
                        break;
                    case "uat":
                        fip = new FileInputStream("./src/test/resources/config/config_uat.properties");
                        break;
                    case "prod":
                        fip = new FileInputStream("./src/test/resources/config/config_prod.properties");
                        break;
                    default:
                        throw new APIException("========Invalid Environment=========");
                }
        } catch (FileNotFoundException e) {
            throw new APIException("File not found for " + envName + " env");
        }
        try {
            prop.load(fip);
        } catch (IOException e) {
            throw new APIException("Properties file is not successfully loaded");
        }
    }

    public static void setProp(String key, String value){
        prop.setProperty(key, value);
    }

    public static String getProp(String key){
        return prop.getProperty(key).trim();
    }

}

package com.vemser.correcao.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {
    public static Properties properties;

    public static void initializePropertyFile(){
        properties = new Properties();
        try {
            String configPath = "src/main/resources/configsetting.properties";
            InputStream instm = new FileInputStream(configPath);
            properties.load(instm);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

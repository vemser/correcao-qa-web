package com.vemser.correcao.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Manipulation {
    public Manipulation() {}

    public static Properties getProps() {
        Properties props = new Properties();
        try {
            FileInputStream file = new FileInputStream("src/main/resources/configSetting.properties");
            props.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public static void setProp(String key, String value) {
        Properties props = getProps();
        try {
            FileOutputStream file = new FileOutputStream("src/main/resources/configSetting.properties");
            props.setProperty(key, value);
            props.store(file, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

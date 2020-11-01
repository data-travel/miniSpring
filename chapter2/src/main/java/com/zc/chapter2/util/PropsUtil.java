package com.zc.chapter2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 〈〉
 *
 * @author zc
 * @create 2020/10/31
 * @since 1.0.0
 */
public class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String fileName) {
        Properties prop = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " is not found ");
            }
            prop = new Properties();
            prop.load(is);
        } catch (IOException e) {
            LOGGER.error("load properties file erroe", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream error", e);
                }
            }
            return prop;
        }


    }

    public static String getString(Properties prop, String key) {
        return getString(prop, key, "");
    }

    public static String getString(Properties prop, String key, String defaultValue) {
        String value = defaultValue;
        if (prop.containsKey(key)) {
            value = prop.getProperty(key);
        }
        return value;
    }


    public static int getInt(Properties prop, String key) {
        return getInt(prop, key, 0);
    }

    public static int getInt(Properties prop, String key, int defaultValue) {
        int value = defaultValue;
        if (prop.containsKey(key)) {
            value = Integer.parseInt( prop.getProperty(key));
        }
        return value;
    }

    public static boolean getBoolean(Properties prop, String key) {
        return getBoolean(prop, key, false);
    }

    public static boolean getBoolean(Properties prop, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (prop.containsKey(key)) {
            value = Boolean.parseBoolean(prop.getProperty(key));
        }
        return value;
    }
}

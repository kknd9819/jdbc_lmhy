package com.zz.util.shengyuan;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceUtils {
    private static String DEFAULT_PROPERTIES_FILENAME = "ApplicationResources";
    private static Map<String, PropertiesConfiguration> configMap = new HashMap();

    public ResourceUtils() {
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean(DEFAULT_PROPERTIES_FILENAME, key, defaultValue);
    }

    public static boolean getBoolean(String baseName, String key, boolean defaultValue) {
        Configuration config = getConfig(baseName);
        return config == null?defaultValue:config.getBoolean(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return getInt(DEFAULT_PROPERTIES_FILENAME, key, defaultValue);
    }

    public static int getInt(String baseName, String key, int defaultValue) {
        Configuration config = getConfig(baseName);
        return config == null?defaultValue:config.getInt(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        return getLong(DEFAULT_PROPERTIES_FILENAME, key, defaultValue);
    }

    public static long getLong(String baseName, String key, long defaultValue) {
        Configuration config = getConfig(baseName);
        return config == null?defaultValue:config.getLong(key, defaultValue);
    }

    public static BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        return getBigDecimal(DEFAULT_PROPERTIES_FILENAME, key, defaultValue);
    }

    public static BigDecimal getBigDecimal(String baseName, String key, BigDecimal defaultValue) {
        Configuration config = getConfig(baseName);
        return config == null?defaultValue:config.getBigDecimal(key, defaultValue);
    }

    public static String getString(String key) {
        return getString(DEFAULT_PROPERTIES_FILENAME, key);
    }

    public static String getString(String baseName, String key) {
        return getStringForDefault(baseName, key, key);
    }

    public static String getStringForDefault(String key, String defaultValue) {
        return getStringForDefault(DEFAULT_PROPERTIES_FILENAME, key, defaultValue);
    }

    public static String getStringForDefault(String baseName, String key, String defaultValue) {
        Configuration config = getConfig(baseName);
        return config == null?defaultValue:config.getString(key, defaultValue);
    }

    public String getString(String key, Object... args) {
        return getString(DEFAULT_PROPERTIES_FILENAME, key, args);
    }

    public static String getString(String baseName, String key, Object... args) {
        String text = getString(baseName, key);
        return text != null?MessageFormat.format(text, args):null;
    }

    public static List<String> getList(String key) {
        return getList(DEFAULT_PROPERTIES_FILENAME, key);
    }

    public static List<String> getList(String baseName, String key) {
        Configuration config = getConfig(baseName);
        return (List)(config == null?new ArrayList():config.getList(key));
    }

    public static String[] getStringArray(String key) {
        return getStringArray(DEFAULT_PROPERTIES_FILENAME, key);
    }

    public static String[] getStringArray(String baseName, String key) {
        Configuration config = getConfig(baseName);
        return config == null?new String[0]:config.getStringArray(key);
    }

    private static Configuration getConfig(String baseName) {
        try {
            PropertiesConfiguration config = (PropertiesConfiguration)configMap.get(baseName);
            if(config == null) {
                config = new PropertiesConfiguration(baseName + ".properties");
                FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
                config.setReloadingStrategy(strategy);
                configMap.put(baseName, config);
            }

            return config;
        } catch (ConfigurationException var3) {
            return null;
        }
    }
}
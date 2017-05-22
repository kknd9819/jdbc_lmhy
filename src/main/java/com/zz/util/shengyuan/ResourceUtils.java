package com.zz.util.shengyuan;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * <p>
 * 在本类中可以管理多个properties文件，负责从这些文件中取出名值对信息，并且可以自动检测文件是否已经修改过，如果修改过，则新的信息会在5秒钟内自动生效。
 * <p>
 * properties file语法说明如下, 它基本上遵循Properties.load的基本语法以及一些有用的扩展:
 * <ul>
 * <li>每个属性的基本格式为：<B>key <separator> value</B>. 可以使用的分隔符为：'=', ':' 和空格字符. 比如:
 *
 * <pre>
 * key1 = value1<br/>
 * key2 : value2<br/>
 * key3 value3<br/>
 * </pre>
 *
 * </li>
 * <li>key可以是任何字符，但是如果使用分隔符的话，必须通过"\"进行转义:
 *
 * <pre>
 * key\:foo = bar
 * </li>
 * </pre>
 * <li>value值可以跨行，但必须使用"\"作为换行符，换行符前的空格会保留，但是下一行的前置空格会被删除。</li>
 * <li>包含分隔符(delimiter)的value值会被解释为按token分隔的字符串列表。默认的分隔符(delimiter)是逗号','. 因此下面的property定义:
 *
 * <pre>
 * key = This property, has multiple, values<br/>
 * </pre>
 *
 * 将会生成三个值。你可以使用getStringArray或getList取得相应的值对象。</li>
 * <li>如果在value值中要使用逗号','必须进行转义.</li>
 * <li>如果一个key值出现多次的话，value值的处理与使用逗号进行分隔的效果相同</li>
 * <li>空行或以'#'、'!'开头的行在解释时不会被处理。</li>
 * <li>如果一个属性的名称是"include"，那么它的值代表全路径的另外一个配置文件，这些文件将会包含到父文件中，如果有相同key的话不会进行覆盖.</li>
 *
 * <p>
 * 下面是一个有效的带扩展的properties文件实例:
 *
 * <pre>
 * # lines starting with # are comments
 *
 * # This is the simplest property key = value
 *
 * # A long property may be separated on multiple lines
 * longvalue = aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa \
 *             aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
 *
 * # This is a property with many tokens
 * tokens_on_a_line = first token, second token
 *
 * # This sequence generates exactly the same result
 * tokens_on_multiple_lines = first token
 * tokens_on_multiple_lines = second token
 *
 * # commas may be escaped in tokens
 * commas.escaped = Hi\, what'up?
 *
 * # properties can reference other properties
 * base.prop = /base
 * first.prop = ${base.prop}/first
 * second.prop = ${first.prop}/second
 * </pre>
 *
 * @author shengyuan
 */
public class ResourceUtils {
    /** 默认的配置文件信息 */
    private static String DEFAULT_PROPERTIES_FILENAME = "ApplicationResources";
    /** 保存所有配置文件的信息 */
    private static Map<String, PropertiesConfiguration> configMap = new HashMap<String, PropertiesConfiguration>();

    /**
     * <p>
     * 返回默认属性文件中key对应的值
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean(DEFAULT_PROPERTIES_FILENAME, key, defaultValue);
    }

    /**
     * 返回 {res}.properties 中 key 对应的值
     *
     * @param baseName
     * @param key
     * @return
     */
    public static boolean getBoolean(String baseName, String key, boolean defaultValue) {
        Configuration config = getConfig(baseName);
        if (config == null) {
            // 没有找到相应的配置文件，则返回空字符串
            return defaultValue;
        }

        return config.getBoolean(key, defaultValue);
    }

    /**
     * <p>
     * 返回默认属性文件中key对应的值
     *
     * @param key
     * @return
     */
    public static int getInt(String key, int defaultValue) {
        return getInt(DEFAULT_PROPERTIES_FILENAME, key, defaultValue);
    }

    /**
     * 返回 {res}.properties 中 key 对应的值
     *
     * @param baseName
     * @param key
     * @return
     */
    public static int getInt(String baseName, String key, int defaultValue) {
        Configuration config = getConfig(baseName);
        if (config == null) {
            // 没有找到相应的配置文件，则返回空字符串
            return defaultValue;
        }

        return config.getInt(key, defaultValue);
    }

    /**
     * <p>
     * 返回默认属性文件中key对应的值
     *
     * @param key
     * @return
     */
    public static long getLong(String key, long defaultValue) {
        return getLong(DEFAULT_PROPERTIES_FILENAME, key, defaultValue);
    }

    /**
     * 返回 {res}.properties 中 key 对应的值
     *
     * @param baseName
     * @param key
     * @return
     */
    public static long getLong(String baseName, String key, long defaultValue) {
        Configuration config = getConfig(baseName);
        if (config == null) {
            // 没有找到相应的配置文件，则返回空字符串
            return defaultValue;
        }

        return config.getLong(key, defaultValue);
    }

    /**
     * <p>
     * 返回默认属性文件中key对应的值
     *
     * @param key
     * @return
     */
    public static BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        return getBigDecimal(DEFAULT_PROPERTIES_FILENAME, key, defaultValue);
    }

    /**
     * 返回 {res}.properties 中 key 对应的值
     *
     * @param baseName
     * @param key
     * @return
     */
    public static BigDecimal getBigDecimal(String baseName, String key, BigDecimal defaultValue) {
        Configuration config = getConfig(baseName);
        if (config == null) {
            // 没有找到相应的配置文件，则返回空字符串
            return defaultValue;
        }

        return config.getBigDecimal(key, defaultValue);
    }

    /**
     * <p>
     * 返回默认属性文件中key对应的值
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return getString(DEFAULT_PROPERTIES_FILENAME, key);
    }

    /**
     * 返回 {res}.properties 中 key 对应的值
     *
     * @param baseName
     * @param key
     * @return
     */
    public static String getString(String baseName, String key) {
        return getStringForDefault(baseName, key, key);
    }

    /**
     * <p>
     * 返回默认属性文件中key对应的值
     *
     * @param key
     * @return
     */
    public static String getStringForDefault(String key, String defaultValue) {
        return getStringForDefault(DEFAULT_PROPERTIES_FILENAME, key, defaultValue);
    }

    /**
     * 返回 {res}.properties 中 key 对应的值
     *
     * @param baseName
     * @param key
     * @return
     */
    public static String getStringForDefault(String baseName, String key, String defaultValue) {
        Configuration config = getConfig(baseName);
        if (config == null) {
            // 没有找到相应的配置文件，则返回默认值
            return defaultValue;
        }

        return config.getString(key, defaultValue);
    }

    /**
     * <p>
     * 返回默认属性文件中key对应的值，并对值进行参数格式化
     *
     * @param key
     * @param args
     * @return
     */
    public String getString(String key, Object... args) {
        return getString(DEFAULT_PROPERTIES_FILENAME, key, args);
    }

    /**
     * 返回 {res}.properties 中 key 对应的值，并对值进行参数格式化
     *
     * @param baseName
     * @param key
     * @param args
     * @return
     */
    public static String getString(String baseName, String key, Object... args) {
        String text = getString(baseName, key);
        return (text != null) ? MessageFormat.format(text, args) : null;
    }

    /**
     * <p>
     * 返回默认属性文件中key对应的值
     *
     * @param key
     * @return
     */
    public static List<String> getList(String key) {
        return getList(DEFAULT_PROPERTIES_FILENAME, key);
    }

    /**
     * 返回 {res}.properties 中 key 对应的值
     *
     * @param baseName
     * @param key
     * @return 如果没有找到相应的key返回空的数组
     */
    @SuppressWarnings("unchecked")
    public static List<String> getList(String baseName, String key) {
        Configuration config = getConfig(baseName);
        if (config == null) {
            // 没有找到相应的配置文件，则返回空字符串
            return new ArrayList<String>();
        }

        return (List<String>) config.getList(key);
    }

    /**
     * <p>
     * 返回默认属性文件中key对应的值
     *
     * @param key
     * @return 如果没有找到相应的key返回空的数组
     */
    public static String[] getStringArray(String key) {
        return getStringArray(DEFAULT_PROPERTIES_FILENAME, key);
    }

    /**
     * 返回 {res}.properties 中 key 对应的值
     *
     * @param baseName
     * @param key
     * @return 如果没有找到相应的key返回空的数组
     */
    public static String[] getStringArray(String baseName, String key) {
        Configuration config = getConfig(baseName);
        if (config == null) {
            // 没有找到相应的配置文件，则返回空字符串
            return new String[0];
        }

        return config.getStringArray(key);
    }

    /**
     * 返回 {res}.properties 中 key 对应的值
     *
     * @param locale
     * @param baseName
     * @param key
     * @return
     */
    private static Configuration getConfig(String baseName) {
        try {
            PropertiesConfiguration config = configMap.get(baseName);
            if (config == null) {
                config = new PropertiesConfiguration(baseName + ".properties");
                FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
                config.setReloadingStrategy(strategy);

                configMap.put(baseName, config);
            }

            return config;
        } catch (ConfigurationException e) {
            return null;
        }
    }
}
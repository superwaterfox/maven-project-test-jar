package waterfox.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
/**
 * 通过properties加载properties，然后遍历key返回指定的value字段值
 * @author ch
 *
 */
public class ConfigUtil {

	private static final Logger log = Logger.getLogger(ConfigUtil.class);
	private static final Map<String, String> CONFIG = new HashMap<String, String>();

	static {
		Properties p = new Properties();
		try {
			p.load(ConfigUtil.class.getClassLoader().getResourceAsStream(
					"config.properties"));

			for (Entry<Object, Object> tmp : p.entrySet()) {
				CONFIG.put((String) tmp.getKey(), (String) tmp.getValue());
			}
		} catch (IOException e) {
			log.error("加载配置文件:config.properties出错!", e);
		}
	}

	public static String getStringValue(String key, String defaultValue) {
		String value = CONFIG.get(key);
		return StringUtils.isEmpty(value) ? defaultValue : value;
	}

	public static String getStringValue(String key) {
		return getStringValue(key, null);
	}

	public static int getIntegerValue(String key) {
		String value = getStringValue(key);
		return StringUtils.isEmpty(value) ? 0 : NumberUtils.toInt(value);
	}

	public static double getDoubleValue(String key) {
		String value = getStringValue(key);
		return StringUtils.isEmpty(value) ? 0 : NumberUtils.toDouble(value);
	}
}

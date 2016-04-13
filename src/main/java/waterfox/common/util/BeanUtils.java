/**
 * bean操作公共类
 * @author Jerry
 * @version v1.0 BeanUtils.java 2013-10-10 下午05:49:25
 * 
 */
package waterfox.common.util;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * BeanUtils bean操作公共类
 * 
 * @author Jerry
 * @version v1.0 BeanUtils.java 2013-10-10 下午05:49:25
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	public static <T> T copyProperties(T dest, T src, String[] properties) {
		if (dest == null) {
			return src;
		}
		if (src == null) {
			return dest;
		}
		for (String property : properties) {
			try {
				Object value = PropertyUtils.getProperty(src, property);
				PropertyUtils.setProperty(dest, property, value);
			} catch (Exception e) {
				throw new RuntimeException("复制属性出错！dest="+dest+",src="+src+",properties="+properties, e);
			}
		}
		return dest;
	}
}

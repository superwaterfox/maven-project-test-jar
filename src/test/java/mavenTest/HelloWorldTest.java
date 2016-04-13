package mavenTest;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Note:
 * Maven的测试类。必须带Test咯
 * 目录为src/test/java下的新建类。
 * Maven会扫描该目录下@Test注解。通过junit.Assertd的方法可以返回判断结果
 * @Title HelloWorldTest
 * @Description 
 * @author ch
 * @Date 2016年2月23日 上午9:41:17
 */
public class HelloWorldTest {
	public void test1() {
		if (StringUtils.isNotEmpty("123")) {
			Assert.assertEquals("HelloMaven", "HelloMaven");
		}
	}
}

package waterfox.junit;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class MathJunit {
	/**
	 * 99算法 
	 */
	@Test
	public void test1() {
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(i + "*" + j + "=" + i * j + "  ");
			}
			System.out.println();
		}
	}

	/**
	 * 计算阶乘
	 *  n!=n*(n-1)*(n-2)*(n-3)...*2*1
	 */
	@Test
	public void test2() {

		int n = 0;
		long result = 1;
		if (n == 0) {
			System.out.println("0的阶乘是0");
		}
		while (n > 0) {
			result *= n;
			n--;
		}
		System.out.println(result);
	}

	/**
	 * 9的倍数
	 */
	@Test
	public void test3() {
		for (int i = 100; i > 0; i--) {
			if (i % 9 == 0) {
				System.out.println(i);
			}
		}
	}

	/**
	 * 求素数
	 */
	@Test
	public void test4() {
		int max = 100;
		int min = 1;
		while (max >= min) {
			boolean flag = true;
			if (max % min == 0) {// 素数
				flag = false;
			}

			if (flag) {
				max--;
				min = 1;
			}
			min++;
		}
	}

	@Test
	public void test5() {
		String detailUrl = "http://www.baidu.com/ha/hei/index.html";
		String[] split = detailUrl.split("/");
		ArrayList<String> arrayList = new ArrayList<String>();
		for (String string : split) {
			if (StringUtils.isNotEmpty(string)) {
				arrayList.add(string);
			}
		}
	}
}
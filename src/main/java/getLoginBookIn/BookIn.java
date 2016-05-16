
package getLoginBookIn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * 
 * httpurlconnetion的测试类。
 * 可以解决大部分的post请求和get请求，但是无法解决页面css和js的渲染问题
 * 
 */
public class BookIn {
	/**
	 * 
	 * miaomiaos 每天登陆获得流量
	 * 
	 */
	@Test
	public void test1() {
		try {
//			String url = "https://www.miaosguanwang.pw/my/api.php?cmd=gift500mb";
			String url = "https://www.miaosguanwang.pw/my/api.php?cmd=gift500mb";

			HashMap<Object, Object> requestProperty = getTextMap("C:\\Users\\X\\Desktop\\1.txt");
			UrlConnectionTest(url, "GET", requestProperty);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * _ _ *_ _ _ =_ _5 _   取值 不包含6,9。而且长度是固定的 
	 */
	public void test3() {
		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < 99; i++) {
			for (int x = 0; x < 999; x++) {
				if (!hashMap.containsKey(x)) {
					hashMap.put(i, x);
					if ((i + "").contains("6") || (i + "").contains("9") || (x + "").contains("6")
							|| (x + "").contains("9")) {
						continue;

					} else if ((i + "").length() == 2 && (x + "").length() == 3) {
						if ((x * i + "").contains("6") || (x * i + "").contains("9")) {
							continue;
						} else if (x * i % 100 / 10 == 5 && (x * i + "").length() == 4) {
							System.out.println(i + "*" + x + "=" + x * i);
						}

					}
				}

			}
		}

	}

	/**
	 * 测试返回结果
	 * @param url
	 * @param requestMethod   请求方法
	 * @param requestProperty	请求参数
	 * @throws MalformedURLException	
	 * @throws IOException
	 */
	private void UrlConnectionTest(String url, String requestMethod, HashMap<Object, Object> requestProperty)
			throws MalformedURLException, IOException {
		URL url1 = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();
		urlConnection.setRequestMethod(requestMethod);
		urlConnection.setConnectTimeout(2000);
		Iterator<Entry<Object, Object>> iterator = requestProperty.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Object, Object> next = iterator.next();
			urlConnection.setRequestProperty(next.getKey() + "", next.getValue() + "");
		}
		int responseCode = urlConnection.getResponseCode();
		System.out.println(url + ": result=" + responseCode);
		InputStream inputStream = urlConnection.getInputStream();
		String string = IOUtils.toString(inputStream, "UTF-8");
		System.out.println(string);
	}

	public HashMap<Object, Object> getTextMap(String fileName) {
		Scanner scanner = null;
		HashMap<Object, Object> hashMap = new HashMap<>();
		try {
			scanner = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			int lastIndexOf = 0;
			String key = "";
			String value = "";
			if (nextLine.indexOf(":") == 0) {
				lastIndexOf = nextLine.substring(1, nextLine.length()).indexOf(":");
				key = nextLine.substring(0, lastIndexOf + 1);
				value = nextLine.substring(lastIndexOf + 2, nextLine.length());
			} else {
				lastIndexOf = nextLine.indexOf(":");
				key = nextLine.substring(0, lastIndexOf);
				value = nextLine.substring(lastIndexOf + 1, nextLine.length());
			}
			System.out.println(key + "         " + value);
			hashMap.put(key, value);
		}
		return hashMap;
	}

}

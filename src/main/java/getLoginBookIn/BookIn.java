
package getLoginBookIn;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

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
			String url = "https://www.miaosguanwang.pw/api.php?cmd=gift500mb";
			HashMap<Object, Object> requestProperty = new HashMap<Object, Object>();
			requestProperty.put(":authority", "www.miaosguanwang.pw");
			requestProperty.put(":method", "GET");
			requestProperty.put(":path", "/panel.php");
			requestProperty.put(":scheme", "https");
			requestProperty.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			requestProperty.put("accept-encoding", "gzip, deflate, sdch");
			requestProperty.put("accept-language", "zh-CN,zh;q=0.8");
			requestProperty.put("cache-control", "max-age=0");
			requestProperty.put("cookie",
					"__cfduid=dcdd51d099589bb7ad96fd900ae27bd6c1453869652; u2=f859a6cfe4b1a682bd4ac9411686d08f; CNZZDATA1254027205=449094086-1453864736-%7C1456105261");
			requestProperty.put("upgrade-insecure-requests", "1");
			requestProperty.put("user-agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36");
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
		// InputStream inputStream = urlConnection.getInputStream();
		// String string = IOUtils.toString(inputStream);
		// System.out.println(string);
	}

}

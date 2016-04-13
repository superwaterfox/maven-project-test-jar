package waterfox.common.util.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * 将常见的HTTP请求的工具都一并收纳，根据优势劣势进行功能性选择
 * java.net.HttpURLConnection   轻量级
 * @Title HttpUtil
 * @Description 
 * @author ch
 * @Date 2016年2月29日 上午11:43:23
 */
public class URLConnectionUtil {

	/**
	 *  使用java.net.HttpURLConnection轻量级功能测试
	 * @param url
	 * @param requestMethod   请求方法
	 * @param requestProperty	请求参数
	 * @throws MalformedURLException	
	 * @throws IOException
	 */
	private boolean UrlConnectionTest(String url, String requestMethod, HashMap<Object, Object> requestProperty) {
		try {
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
			boolean x = responseCode == 200 ? true : false;
			return x;
		} catch (Exception e) {

		}
		return false;
		// InputStream inputStream = urlConnection.getInputStream();
		// String string = IOUtils.toString(inputStream);
		// System.out.println(string);
	}

	/**
	 * 使用java.net.HttpURLConnection轻量级 代理功能测试
	 * 功能：隐藏过程细节，只返回是否能正常访问的信息
	 * @param proxyIp
	 * @param proxyPort
	 * @return
	 */
	public boolean URLConnectionProxyTest(String url1, String proxyIp, String proxyPort) {
		URL url;
		// url1 = "http://www.whatismyip.com.tw/";
		try {
			url = new URL(url1);
			// 创建代理服务器
			InetSocketAddress addr = new InetSocketAddress(proxyIp, Integer.parseInt(proxyPort));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
			// 如果我们知道代理server的名字, 可以直接使用
			HttpURLConnection conn;

			conn = (HttpURLConnection) url.openConnection(proxy);
			conn.setConnectTimeout(1000 * 1);
			int responseCode = conn.getResponseCode();

			if (responseCode == 200) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

}

package getProxy;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class ProxyThread extends Thread {
	String proxyIp;
	int proxyPort;

	private static Object object;

	public ProxyThread() {
	}

	public ProxyThread(String proxyIp, int proxyPort) {
		this.proxyIp = proxyIp;
		this.proxyPort = proxyPort;
	}

	@Override
	public void run() {

		URL url;
		try {
			// http://www.whatismyip.com.tw/
			String url1 = "http://www.whatismyip.com.tw/";

			url = new URL(url1);
			// 创建代理服务器
			InetSocketAddress addr = new InetSocketAddress(proxyIp, proxyPort);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
			// 如果我们知道代理server的名字, 可以直接使用
			HttpURLConnection conn;

			conn = (HttpURLConnection) url.openConnection(proxy);
			conn.setConnectTimeout(1000 * 1);
			int responseCode = conn.getResponseCode();

			if (responseCode == 200) {
				Runtime rt = Runtime.getRuntime();
				Process process = null;
				process = rt
						.exec("D:/CH_Work_Station/Develop/workspace/webmagic-master/webmagic-selenium/resource/phantomjs-2.1.1-windows/bin/phantomjs.exe --proxy="
								+ proxyIp + ":" + proxyPort + ""
								+ " D:\\CH_Work_Station\\Develop\\workspace\\webmagic-master\\webmagic-selenium\\resource\\test2.js "
								+ url1.trim());
				// InputStream is = process.getInputStream();
				IOUtils.toString(process.getInputStream());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//
		// URL url;
		// try {
		// url = new URL("http://www.t66n.com/y/?id=waterfox1");
		// // 创建代理服务器
		// InetSocketAddress addr = new InetSocketAddress(proxyIp, proxyPort);
		// Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
		// // 如果我们知道代理server的名字, 可以直接使用
		// HttpURLConnection conn;
		//
		// conn = (HttpURLConnection) url.openConnection(proxy);
		// conn.setConnectTimeout(1000 * 1);
		// // conn.setRequestProperty("User-Agent",
		// // "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML,
		// // like Gecko) Chrome/47.0.2516.0 Safari/537.36");
		// int responseCode = conn.getResponseCode();
		// InputStream in = conn.getInputStream();
		// String s = IOUtils.toString(in);
		// System.out.println(s);
		// System.out.println(
		// Thread.currentThread().getName() + " " + proxyIp + ":" +
		// proxyPort + " res:" + responseCode);
		// } catch (Exception e) {
		// System.out.println(e);
		// System.out.println(Thread.currentThread().getName() + " " + proxyIp +
		// ":" + proxyPort + " res: failed");
		// }

	}

	public String getProxyIp() {
		return proxyIp;
	}

	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public static void main(String[] args) {
		new ProxyThread("202.195.192.197", 3128).run();
	}

}

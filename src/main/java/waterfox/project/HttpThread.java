package waterfox.project;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class HttpThread extends Thread {
	String proxyIp;
	int proxyPort;

	public HttpThread() {
		// TODO Auto-generated constructor stub
	}

	public HttpThread(String proxyIp, int proxyPort) {
		this.proxyIp = proxyIp;
		this.proxyPort = proxyPort;
	}

	@Override
	public void run() {
		try {
			URL url = new URL("http://www.t66n.com/index.asp?id=waterfox");
			// 创建代理服务器
			InetSocketAddress addr = new InetSocketAddress(proxyIp, proxyPort);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
			// 如果我们知道代理server的名字, 可以直接使用
			URLConnection conn;

			conn = url.openConnection(proxy);
			conn.setConnectTimeout(1000 * 2);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2516.0 Safari/537.36");
			InputStream in = conn.getInputStream();
			// String s = IOUtils.toString(in);
			// System.out.println(s);
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("失败:" + proxyIp);
		}
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

}

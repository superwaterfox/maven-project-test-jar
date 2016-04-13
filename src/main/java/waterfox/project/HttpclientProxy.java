package waterfox.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpclientProxy {
	public static void main(String args[]) throws Exception {
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		// 依次是目标请求地址，端口号,协议类型
		HttpHost target = new HttpHost("http://148.163.175.211/u/", 80, "http");
		// 依次是代理地址，代理端口号，协议类型
		HttpHost proxy = new HttpHost("1.193.163.32", 8000, "http");
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

		// 请求地址
		HttpPost httpPost = new HttpPost("http://148.163.175.211/u/");
		httpPost.setConfig(config);
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		// 参数名为pid，值是2
		formparams.add(new BasicNameValuePair("id", "waterfox"));

		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse response = closeableHttpClient.execute(target, httpPost);
			// getEntity()
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				System.out.println("response:" + EntityUtils.toString(httpEntity, "UTF-8"));
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1() throws ClientProtocolException, IOException, InterruptedException {

		ArrayList<String> list1 = new ArrayList<>();
		list1.add("58.20.251.44");
		list1.add("58.22.191.243");
		list1.add("58.57.84.206");
		list1.add("58.59.2.78");
		list1.add("58.59.68.91");
		list1.add("58.67.159.50");
		list1.add("58.83.174.114");
		list1.add("58.96.183.136");
		list1.add("58.96.187.208");
		list1.add("58.96.187.234");
		list1.add("58.133.61.3");
		list1.add("58.220.10.7");
		list1.add("58.243.0.162");
		list1.add("58.249.55.222");
		list1.add("58.251.47.101");
		list1.add("58.252.2.5");
		list1.add("58.252.7.125");
		list1.add("58.252.8.25");
		list1.add("58.253.238.242");
		list1.add("59.39.88.190");
		list1.add("59.42.251.214");
		list1.add("59.42.251.215");
		list1.add("59.42.251.216");
		list1.add("59.44.152.110");
		list1.add("59.48.106.154");
		list1.add("59.48.106.194");
		list1.add("59.49.248.216");
		list1.add("59.51.73.141");
		list1.add("59.58.162.141");
		list1.add("59.61.80.78");
		list1.add("59.108.61.132");
		list1.add("60.11.11.163");
		list1.add("60.15.25.190");
		list1.add("60.15.41.214");
		list1.add("60.15.55.228");
		list1.add("60.15.58.196");
		list1.add("60.15.58.197");
		list1.add("60.15.58.198");
		list1.add("60.15.58.199");
		list1.add("60.21.221.228");
		list1.add("60.29.248.142");
		list1.add("60.170.98.118");
		list1.add("60.191.130.36");
		list1.add("60.191.134.162");
		list1.add("60.191.146.245");
		list1.add("60.191.153.3");
		list1.add("60.191.153.12");
		list1.add("60.191.154.246");
		list1.add("60.191.156.134");
		list1.add("60.191.158.211");
		list1.add("60.191.160.20");
		list1.add("60.191.161.244");
		list1.add("60.191.163.235");
		list1.add("60.191.164.22");
		list1.add("60.191.164.59");
		list1.add("60.191.166.130");
		list1.add("60.191.167.93");
		list1.add("60.191.170.85");
		list1.add("60.191.174.13");
		list1.add("60.191.174.196");
		list1.add("60.191.178.43");
		list1.add("60.191.179.53");
		list1.add("60.191.180.38");
		list1.add("60.191.190.174");
		list1.add("60.194.100.51");
		list1.add("60.235.248.146");
		list1.add("60.250.81.97");
		list1.add("60.250.81.118");

		ArrayList<Integer> list2 = new ArrayList<>();
		list2.add(8080);
		list2.add(80);
		list2.add(8081);
		list2.add(9999);
		list2.add(9797);
		list2.add(80);
		list2.add(80);
		list2.add(8080);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(80);
		list2.add(9999);
		list2.add(9797);
		list2.add(8081);
		list2.add(8000);
		list2.add(8000);
		list2.add(8000);
		list2.add(80);
		list2.add(8080);
		list2.add(80);
		list2.add(80);
		list2.add(80);
		list2.add(9999);
		list2.add(9797);
		list2.add(9797);
		list2.add(3128);
		list2.add(3128);
		list2.add(888);
		list2.add(9999);
		list2.add(808);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(80);
		list2.add(8080);
		list2.add(3128);
		list2.add(3128);
		list2.add(9999);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(3128);
		list2.add(80);
		list2.add(9999);
		list2.add(80);
		list2.add(80);

		for (int i = 0; i < list2.size(); i++) {
			String proxyIp = list1.get(i);
			int proxyPort = list2.get(i);
			HttpThread httpThread = new HttpThread();
			httpThread.setProxyIp(proxyIp);
			httpThread.setProxyPort(proxyPort);
			Thread.sleep(1000);
			httpThread.start();
		}
	}
}

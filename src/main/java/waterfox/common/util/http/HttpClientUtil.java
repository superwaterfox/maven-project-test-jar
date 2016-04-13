package waterfox.common.util.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * 
 * @Title HttpClientUtil
 * @Description 
 * @author ch
 * @Date 2016年3月10日 下午3:50:20
 */
public class HttpClientUtil {

	public static HttpClientEntry request(String url, HttpClientEntry httpClientEntry) {
		/**请求配置封装**/
		HttpClient httpClient = baseConfig(httpClientEntry);

		/**确认请求类型，并确认请求内容的正确封装**/
		HttpMethodBase httpMethod = null;
		if ("GET".equals(httpClientEntry.getHttpMethod())) {
			httpMethod = new GetMethod(url);

		} else if ("POST".equals(httpClientEntry.getHttpMethod())) {
			httpMethod = new PostMethod(url);
		}
		sendContentConfig(httpClientEntry, httpMethod);

		/**请求头封装**/
		httpMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				httpClientEntry.getConfig_ReadCharset());
		if (httpClientEntry != null && httpClientEntry.getRequest_HeaderMap() != null
				&& httpClientEntry.getRequest_HeaderMap().size() > 0) {
			Map<String, String> requestHeaderMap = httpClientEntry.getRequest_HeaderMap();
			for (Entry<String, String> entry : requestHeaderMap.entrySet()) {
				httpMethod.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}

		/**响应返回封装**/
		try {
			httpClientEntry.setResponse_Status(httpClient.executeMethod(httpMethod));
			httpClientEntry.setResponse_Content(httpMethod.getResponseBodyAsString());

			// httpClientEntry.setResponse_Content(
			// IOUtils.toString(httpMethod.getResponseBodyAsStream(),
			// httpClientEntry.getConfig_ReadCharset()));

			return httpClientEntry;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpMethod.releaseConnection();
				SimpleHttpConnectionManager simpleHttpConnectionManager = (SimpleHttpConnectionManager) httpClient
						.getHttpConnectionManager();
				if (simpleHttpConnectionManager != null) {
					simpleHttpConnectionManager.shutdown();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**请求配置封装**/
	private static HttpClient baseConfig(HttpClientEntry httpClientEntry) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(httpClientEntry.getConfig_ConnectTimeOut());
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(httpClientEntry.getConfig_ReadTimeOut());
		return httpClient;
	}

	private static void sendContentConfig(HttpClientEntry httpClientEntry, HttpMethod httpMethod) {
		/**请求体**/
		if (httpClientEntry != null && httpClientEntry.getRequest_NameValuePairMap() != null
				&& !httpClientEntry.getRequest_NameValuePairMap().isEmpty()) {
			Map<String, String> requestNameValuePairMap = httpClientEntry.getRequest_NameValuePairMap();

			NameValuePair[] nameVP = new NameValuePair[requestNameValuePairMap.size()];
			int ii = 0;
			for (Entry<String, String> entry : requestNameValuePairMap.entrySet()) {
				nameVP[ii] = new NameValuePair(entry.getKey(), entry.getValue());
				ii++;
			}

			if (httpMethod instanceof PostMethod) {
				((PostMethod) httpMethod).setRequestBody(nameVP);
			} else {
				httpMethod.setQueryString(nameVP);
			}
		}
	}

	@Test
	public void test1() {
		HttpClientEntry httpClientEntry = new HttpClientEntry().setRequest_HeaderMap_Base();
		Map<String, String> request_NameValuePairMap = new HashMap<String, String>();
		request_NameValuePairMap.put("md5_zifu", "1234");
		httpClientEntry.setRequest_NameValuePairMap(request_NameValuePairMap);
		HttpClientEntry request = request(
				"http://md5jiami.51240.com/web_system/51240_com_www/system/file/md5jiami/data/?ajaxtimestamp=1457684338564",
				httpClientEntry);
		System.out.println(request.getResponse_Content());
	}

	@Test
	public void test2() {
		HttpClientEntry httpClientEntry = new HttpClientEntry().setRequest_HeaderMap_Base();
		Map<String, String> request_NameValuePairMap = new HashMap<String, String>();
		request_NameValuePairMap.put("md5_zifu", "1234");
		httpClientEntry.setRequest_NameValuePairMap(request_NameValuePairMap);
		HttpClientEntry request = request(
				"http://localhost/mortgage/order/foreclosure/v/add",
				httpClientEntry);
		System.out.println(request.getResponse_Content());
		
	}
}

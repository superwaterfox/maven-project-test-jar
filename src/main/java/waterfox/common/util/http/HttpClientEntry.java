package waterfox.common.util.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送请求的实体类
 * @Title HttpClientEntry
 * @Description 
 * @author ch
 * @Date 2016年3月10日 下午2:36:49
 */
public class HttpClientEntry {
	// ----------------请求交互内容start---------------------
	public Map<String, String> request_HeaderMap = new HashMap<String, String>(request_HeaderMap_Base);// 请求头
	public static Map<String, String> request_HeaderMap_Base;// 提供基本的参数

	public Map<String, String> request_NameValuePairMap;// GET和POST的请求内容

	public int response_Status;// 响应状态
	public static Map<String, String> response_HeaderMap;// 响应头
	public String response_Content;// 响应体

	private String httpMethod = "GET";

	// ----------------请求基本配置start---------------------
	static {
		request_HeaderMap_Base = new HashMap<String, String>();
		request_HeaderMap_Base.put("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		// request_HeaderMap_Base.put("Accept-Encoding", "gzip, deflate,
		// sdch");//这句话说明文本需要经过压缩后返回的，那么java会将数据进行压缩得到，那么内容就不是我们想要的了
		// Authorization HTTP授权的授权证书 Authorization: Basic
		// QWxhZGRpbjpvcGVuIHNlc2FtZQ==
		request_HeaderMap_Base.put("Accept-Language", "zh-CN,zh;q=0.8");
		request_HeaderMap_Base.put("Cache-Control", "max-age=0");
		request_HeaderMap_Base.put("Connection", "keep-alive");
		request_HeaderMap_Base.put("Cookie", "");
		request_HeaderMap_Base.put("Host", "");
		request_HeaderMap_Base.put("Referer", "");
		request_HeaderMap_Base.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36");
	}

	private int config_ConnectTimeOut = 10000;// 连接超时
	private int config_ReadTimeOut = 10000;// 页面加载超时
	private String config_ReadCharset = "UTF-8";// 读取流

	public Map<String, String> getRequest_HeaderMap() {
		return request_HeaderMap = new HashMap<String, String>(request_HeaderMap_Base);
	}

	public HttpClientEntry setRequest_HeaderMap_Base() {
		this.request_HeaderMap = request_HeaderMap_Base;
		return this;
	}

	public HttpClientEntry setRequest_HeaderMap(Map<String, String> request_HeaderMap) {
		this.request_HeaderMap = request_HeaderMap;
		return this;
	}

	public Map<String, String> getRequest_NameValuePairMap() {
		return request_NameValuePairMap;
	}

	public void setRequest_NameValuePairMap(Map<String, String> request_NameValuePairMap) {
		this.request_NameValuePairMap = request_NameValuePairMap;
	}

	public int getResponse_Status() {
		return response_Status;
	}

	public void setResponse_Status(int response_Status) {
		this.response_Status = response_Status;
	}

	public Map<String, String> getResponse_HeaderMap() {
		return response_HeaderMap;
	}

	public HttpClientEntry setResponse_HeaderMap(Map<String, String> response_HeaderMap) {
		HttpClientEntry.response_HeaderMap = response_HeaderMap;
		return this;
	}

	public String getResponse_Content() {
		return response_Content;
	}

	public HttpClientEntry setResponse_Content(String response_Content) {
		this.response_Content = response_Content;
		return this;
	}

	public int getConfig_ConnectTimeOut() {
		return config_ConnectTimeOut;
	}

	public HttpClientEntry setConfig_ConnectTimeOut(int config_ConnectTimeOut) {
		this.config_ConnectTimeOut = config_ConnectTimeOut;
		return this;
	}

	public int getConfig_ReadTimeOut() {
		return config_ReadTimeOut;
	}

	public HttpClientEntry setConfig_ReadTimeOut(int config_ReadTimeOut) {
		this.config_ReadTimeOut = config_ReadTimeOut;
		return this;
	}

	public String getConfig_ReadCharset() {
		return config_ReadCharset;
	}

	public HttpClientEntry setConfig_ReadCharset(String config_ReadCharset) {
		this.config_ReadCharset = config_ReadCharset;
		return this;
	}

	public HttpClientEntry setHttpMethod_POST() {
		this.httpMethod = "POST";
		return this;
	}

	public HttpClientEntry setHttpMethod_GET() {
		this.httpMethod = "GET";
		return this;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

}

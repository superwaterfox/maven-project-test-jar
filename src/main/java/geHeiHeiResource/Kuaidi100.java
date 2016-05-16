package geHeiHeiResource;

import java.util.HashMap;
import java.util.Map;

import common.util.http.HttpClientEntry;
import common.util.http.HttpClientUtil;

public class Kuaidi100 {
	public static void main(String[] args) {
		// 根据快递单号 获取可能性的快递
		// http://www.kuaidi100.com/autonumber/autoComNum?text=377575213096
		String text = "377575213096";

		HttpClientEntry httpClientEntry = new HttpClientEntry();
		Map<String, Object> request_NameValue = new HashMap<String, Object>();
		request_NameValue.put("text", text);
		httpClientEntry.setRequest_NameValuePairMap(request_NameValue);

		HttpClientEntry response = HttpClientUtil.request("http://www.kuaidi100.com/autonumber/autoComNum",
				httpClientEntry);
		System.out.println(response.getResponse_Content());

		// 根据快递单号和快递公司进行查询
		// http://www.kuaidi100.com/query?type=zhongtong&postid=377575213096&id=1&valicode=&temp=0.4304088514763862
		HttpClientEntry httpClientEntry1 = new HttpClientEntry();
		Map<String, Object> request_NameValue1 = new HashMap<String, Object>();
		request_NameValue1.put("postid", text);
		request_NameValue1.put("type", "zhongtong");
		request_NameValue1.put("temp", "0.5738821462728083");
		httpClientEntry1.setRequest_NameValuePairMap(request_NameValue1);

		HttpClientEntry response1 = HttpClientUtil.request("http://www.kuaidi100.com/query?id=1&valicode=",
				httpClientEntry1);
		System.out.println(response1.response_Content);

	}

}

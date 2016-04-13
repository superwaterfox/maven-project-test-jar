package getProxy;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class ProxyTestProcessor implements PageProcessor {
	private List<String[]> httpProxyList = new ArrayList<>();

	// 部分一：配置网站的相关配置，包括头部信息，抓取间隔，重试次数等
	private Site site = Site.me().setRetryTimes(3).setSleepTime(0).enableHttpProxyPool()
			.setHttpProxyPool(httpProxyList);

	@Override
	public void process(Page page) {
		String string = page.getHtml().toString();
		System.out.println(string);
	}

	@Override
	public Site getSite() {
		return this.site;
	}

	public List<String[]> getHttpProxyList() {
		return httpProxyList;
	}

	public void setHttpProxyList(List<String[]> httpProxyList) {
		this.httpProxyList = httpProxyList;
	}

	public ProxyTestProcessor() {

	}

	public ProxyTestProcessor(List<String[]> httpProxyList) {
		super();
		this.httpProxyList = httpProxyList;
	}

	public static void main(String[] args) {
		Spider.create(new ProxyTestProcessor()).addUrl("http://www.whatismyip.com.tw/").thread(1).run();
	}
}

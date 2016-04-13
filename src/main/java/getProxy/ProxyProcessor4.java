package getProxy;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * http://www.proxy360.cn
 * @Title ProxyProcessor3
 * @Description 
 * @author ch
 * @Date 2016年2月4日 下午4:01:26
 */
public class ProxyProcessor4 implements PageProcessor {
	// 部分一：配置网站的相关配置，包括头部信息，抓取间隔，重试次数等
	private Site site = Site.me().setRetryTimes(3).setTimeOut(2000).setSleepTime(1000).addHeader("User-Agent",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2516.0 Safari/537.36");

	@Override
	public void process(Page page) {
		String url = page.getUrl().toString();
		// 分类模块`
		List<String> all3 = page.getHtml().xpath("//*[@id='ctl00_ContentPlaceHolder1_divRegion']/div/a/@href").all();
		for (String string : all3) {
			page.addTargetRequest(string);
		}

		// 抓取本页
		List<String> all = page.getHtml()
				.xpath("//*[@id='ctl00_ContentPlaceHolder1_upProjectList']/div/div[1]/span[@class='tbBottomLine'][1]/allText()")
				.all();
		List<String> all2 = page.getHtml()
				.xpath("//*[@id='ctl00_ContentPlaceHolder1_upProjectList']/div/div[1]/span[@class='tbBottomLine'][2]/allText()")
				.all();

		List<String[]> httpProxyList = new ArrayList<>();
		for (int i = 0; i < all.size(); i++) {
			String proxyIp = all.get(i);
			String proxyPort = all2.get(i);
			String[] e = { proxyIp, proxyPort };
			httpProxyList.add(e);
			// new ProxyThread(proxyIp.trim(),
			// Integer.parseInt(proxyPort)).run();
		}
		if (httpProxyList.size()>0) {
			ProxyTestProcessor proxyTestProcessor = new ProxyTestProcessor();
			proxyTestProcessor.setHttpProxyList(httpProxyList);
			proxyTestProcessor.main(null);
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		// 国内高匿
		Spider.create(new ProxyProcessor4()).addUrl("http://www.kuaidaili.com/free/inha/").// 入口处
				thread(5).run();// 入口处

	}

}

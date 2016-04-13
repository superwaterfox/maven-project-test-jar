package getProxy;

import java.io.File;

import org.apache.http.HttpHost;
import org.junit.Test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 代理加载页面<br>
 * 使用Selenium做页面动态渲染。<br>
 */
public class ProxyAttrackProcessor implements PageProcessor {

	private Site site = Site.me().setDomain("t66n.com").setSleepTime(1)
			.setHttpProxy(new HttpHost("59.62.35.126", 9000));

	// private Site site =
	// Site.me().setDomain("t66n.com").setSleepTime(0).setHttpProxyPool(
	// Lists.newArrayList(new String[] { "59.62.35.126", "9000" }, new String[]
	// { "59.62.35.126", "9000" }));

	@Override
	public void process(Page page) {
		String url = page.getUrl().toString();
		// //*[@id="header"]/table/tbody/tr/td[1]/font/b
		// page.addTargetRequests(page.getHtml().links().regex("http://huaban\\\\.com/.*").all());
		// if (page.getUrl().toString().contains("pins")) {
		// page.putField("img",
		// page.getHtml().xpath("//div[@id='pin_img']/a/img/@src").toString());
		// } else {
		// page.getResultItems().setSkip(true);
		// }
	}

	public static void main(String[] args) {

		// Spider.create(new ProxyAttrackProcessor()).thread(5)
		// .setDownloader(new SeleniumDownloader(
		// "D:\\CH_Work_Station\\Develop\\workspace\\webmagic\\webmagic-selenium\\src\\main\\resources\\chromedriver.exe"))
		// .addUrl("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=ip&oq=setHttpProxyPool&rsv_pq=e6d2ad5100048e4c&rsv_t=9b8d%2BZKkl6LZXSRVK6AEro70b5MABGD0KT3UTIq4M50eGrSBabgwLLAjuJU&rsv_enter=1&rsv_sug3=2&rsv_sug1=2&rsv_sug2=0&rsv_sug7=100&inputT=436&rsv_sug4=437")
		// .runAsync();
		// http://www.t66n.com/y/?id=waterfox1

	}

	@Override
	public Site getSite() {
		return site;
	}

	@Test
	public void test1() {
		File file = new File("/data/lastUse.proxy");
		String[] list = file.list();
		boolean exists = file.exists();
		System.out.println(exists);
	}
}

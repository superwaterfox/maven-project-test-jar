package getProxy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * http://www.xicidaili.com  西刺 
 * @Title ProxyProcessor
 * @Description 
 * @author ch
 * @Date 2016年2月4日 下午4:01:26
 */
public class ProxyProcessor2 implements PageProcessor {
	// 部分一：配置网站的相关配置，包括头部信息，抓取间隔，重试次数等
	private Site site = Site.me().setRetryTimes(3).setTimeOut(2000).setSleepTime(1000).addHeader("User-Agent",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2516.0 Safari/537.36");

	@Override
	public void process(Page page) {
		String url = page.getUrl().toString();
		// 分页
		List<String> all4 = page.getHtml().xpath("//* [@id='body']/div[2]/a/@href").all();
		for (String string : all4) {// 分页
			Pattern compile = Pattern.compile("(\\d+)");
			Matcher matcher = compile.matcher(string);
			if (matcher.find()) {
				String pageNum = matcher.group(1);
				int pageNum1 = Integer.parseInt(pageNum);
				if (pageNum1 <= 5) {// 只需要前5页,后边的的时间太久啦
					page.addTargetRequest(string);
				}
			}
		}

		// 抓取本页
		List<String> all = page.getHtml().xpath("//*[@id='ip_list']/tbody/tr[@class='odd']/td[3]/allText()").all();
		List<String> all2 = page.getHtml().xpath("//*[@id='ip_list']/tbody/tr[@class='odd']/td[4]/allText()").all();
		List<String> all3 = page.getHtml().xpath("//*[@id='ip_list']/tbody/tr[@class='odd']/td[10]/allText()").all();

		List<String[]> httpProxyList = new ArrayList<>();
		for (int i = 0; i < all.size(); i++) {
			String proxyIp = all.get(i);
			String proxyPort = all2.get(i);
			String date = all3.get(i);
			SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
			try {
				Date date2 = format.parse(date);
				Date date3 = new Date();
				int x = date3.getDate() - date2.getDate();
				if (x <= 5) {// 5天前的资源才要
					String[] e = { proxyIp.trim(), proxyPort };
					httpProxyList.add(e);
					// new ProxyThread(proxyIp.trim(),
					// Integer.parseInt(proxyPort)).run();
				} else {
					continue;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		new ProxyTestProcessor(httpProxyList).main(null);

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		// 国内高匿
		Spider.create(new ProxyProcessor2()).addUrl("http://www.xicidaili.com/nn").// 入口处
				thread(5).run();// 入口处
		// 国内普通代理
		Spider.create(new ProxyProcessor2()).addUrl("http://www.xicidaili.com/nt/").// 入口处
				thread(5).run();// 入口处
		// // 国外搞匿代理
		// Spider.create(new
		// ProxyProcessor2()).addUrl("http://www.xicidaili.com/wn/").// 入口处
		// thread(5).run();// 入口处
		// // 国外普通代理
		// Spider.create(new
		// ProxyProcessor2()).addUrl("http://www.xicidaili.com/wt/").// 入口处
		// thread(5).run();// 入口处
	}

	@Test
	public void test1() throws ParseException {
		Pattern compile = Pattern.compile("(\\d+)");
		Matcher matcher = compile.matcher("/nn/4");
		if (matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}
}

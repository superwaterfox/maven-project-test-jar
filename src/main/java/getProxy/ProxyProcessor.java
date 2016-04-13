package getProxy;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * www.haodailiip.com   
 * @Title ProxyProcessor
 * @Description 
 * @author ch
 * @Date 2016年2月4日 下午4:01:26
 */
public class ProxyProcessor implements PageProcessor {

	// 部分一：配置网站的相关配置，包括头部信息，抓取间隔，重试次数等
	private Site site = Site.me().setRetryTimes(3).setTimeOut(3000).setSleepTime(1000);

	@Override
	public void process(Page page) {
		String url = page.getUrl().toString();

		// 获取二级板块目录
		List<String> all4 = page.getHtml().xpath("/html/body/center/table[1]/tbody/tr[1]/td/a/@href").all();// 分页
		for (String string : all4) {
			page.addTargetRequest(string);
		}

		// 获取分页
		List<String> all3 = page.getHtml().xpath("/html/body/center/table[2]/tbody/tr/td[1]/p/a/@href").all();// 分页
		for (String string : all3) {
			page.addTargetRequest(string);
		}

		// 抓取本页
		List<String> all = page.getHtml()
				.xpath("/html/body/center/table[2]/tbody/tr/td[1]/table/tbody/tr/td[1]/allText()")
				.regex("((\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+))").all();
		List<String> all2 = page.getHtml()
				.xpath("/html/body/center/table[2]/tbody/tr/td[1]/table/tbody/tr/td[2]/allText()").regex("[\\d]+")
				.all();
		// 是否可匿名
		// List<String> all5 = page.getHtml()
		// .xpath("/html/body/center/table[2]/tbody/tr/td[1]/table/tbody/tr[@style='font-size:
		// 16px;line-height: 25px;']/td[5]/allText()")
		// .all();
		List<String> al0 = page.getHtml()
				.xpath("/html/body/center/table[2]/tbody/tr/td[1]/table/tbody/tr/td[7]/allText()").regex("[\\d]+ms")
				.all();

		// 速度过滤

		for (int i = 0; i < al0.size(); i++) {
			int speend = Integer.parseInt(al0.get(i).replace("ms", ""));
			if (speend >= 500) {
				continue;
			}
			String proxyIp = all.get(i);
			String proxyPort = all2.get(i);
			new ProxyThread(proxyIp.trim(), Integer.parseInt(proxyPort)).run();
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	/**
	 * 去除特殊字段
	 * 去除多余空格
	 * @return
	 */
	public String filterUseful(String str) {
		str = str.replaceAll(" +", "").replaceAll(" ", "");
		String string = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Matcher matcher = Pattern.compile(string).matcher(str);
		String trim = matcher.replaceAll("").trim().replaceAll(" +", "").replaceAll(" ", "");// 去除多余空格tab
		return trim;

	}

	public static void main(String[] args) {
		// 这个网站比较变态，一个网站的两个页面不能同时打开，也就是说页面不能同时下载。

		// 国内代理
		Spider.create(new ProxyProcessor()).addUrl("http://www.haodailiip.com/guonei").// 入口处
				thread(1).run();// 入口处
		Spider.create(new ProxyProcessor()).addUrl("http://www.haodailiip.com/guonei").// 入口处
				thread(1).run();// 入口处
		// 国外代理
		Spider.create(new ProxyProcessor()).addUrl("http://www.haodailiip.com/guoji").// 入口处
				thread(1).run();// 入口处
		Spider.create(new ProxyProcessor()).addUrl("http://www.haodailiip.com/guoji").// 入口处
				thread(1).run();// 入口处
	}
}

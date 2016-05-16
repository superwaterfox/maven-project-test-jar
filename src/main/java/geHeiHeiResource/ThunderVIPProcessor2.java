package geHeiHeiResource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * http://www.xunleihuiyuan.net/
 * @Title ThunderVIPProcessor2
 * @Description 
 * @author ch
 * @Date 2016年2月25日 上午10:11:14
 */
public class ThunderVIPProcessor2 implements PageProcessor {
	// 部分一：配置网站的相关配置，包括头部信息，抓取间隔，重试次数等
	private Site site = Site.me().setRetryTimes(3).setSleepTime(0);

	@Override
	public void process(Page page) {// 实现的主要抽取逻辑
		List<String> all = page.getHtml().xpath("//*[@id='divMain']/div/h2/a/@href")
				.regex("http://www.xunleihuiyuan.net/vip/.*").all();
		List<String> times = page.getHtml().xpath("//*[@id='divMain']/div/h3/span[3]/allText()").replace("日期: ", "")
				.all();
		for (int i = 0; i < all.size(); i++) {
			String date = times.get(i);
			SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
			Date date2;
			try {
				date2 = format.parse(date);
				Date date3 = new Date();
				int x = date3.getDate() - date2.getDate();
				if (x <= 0) {// 就要今天
					page.addTargetRequest(all.get(i));// 加入详情页
				} else {
					continue;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (page.getUrl().toString().contains("vip")) {
			/**
			 * 进入详情页
			 */
			// *[@id="divMain"]/div[1]/div[4]/div[7]
			////*[@id="divMain"]/div[1]/div[4]
			List<String> all2 = page.getHtml().xpath("//*[@id='divMain']/div[1]/div[4]//div//text()").all();
			for (String string2 : all2) {
				System.out.println(string2.replace("密码", " 密码 ").replace("账号", "账号 "));
			}
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
		String string = "[`~!@#$%^&*()+=|{}';',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Matcher matcher = Pattern.compile(string).matcher(str);
		String trim = matcher.replaceAll("").replaceAll(" ", "").replaceAll(" ", "");// 去除多余空格tab
		return trim;

	}

	public static void main(String[] args) {
		Spider.create(new ThunderVIPProcessor2()).addUrl("http://www.xunleihuiyuan.net/").// 入口处
				thread(1).run();// 入口处

	}

}

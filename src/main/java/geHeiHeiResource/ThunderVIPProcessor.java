package geHeiHeiResource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * http://521xunlei.com/portal.php
 * @Title ThunderVIPProcessor
 * @Description 
 * @author ch
 * @Date 2016年2月25日 上午10:11:14
 */
public class ThunderVIPProcessor implements PageProcessor {
	// 部分一：配置网站的相关配置，包括头部信息，抓取间隔，重试次数等
	private Site site = Site.me().setRetryTimes(3).setSleepTime(0);

	@Override
	public void process(Page page) {// 实现的主要抽取逻辑
		
		List<String> all = page.getHtml()
				.xpath("//div[@id='portal_block_62_content']//div[@class='module cl xl xl1']//ul//li//a")
				.regex("thread-[\\d]{4}-[\\d]-[\\d].html").all();
		List<String> times = page.getHtml()
				.xpath("//div[@id='portal_block_62_content']//div[@class='module cl xl xl1']//ul//li//em/allText()")
				.all();
		for (int i = 0; i < all.size(); i++) {
			String date = times.get(i);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
		//*[@id="postmessage_179722"]/font[17]/font
		List<String> all2 = page.getHtml().xpath("//*[@id='postmessage_179722']/font[@size='5']/font/allText()").all();
		for (String string2 : all2)
		{
			string2 = filterUseful(string2);
			if (StringUtils.isNotEmpty(string2)) {
				System.out.println(string2.replace("账号", "账号 ").replace("密码", "密码 "));
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
		Spider.create(new ThunderVIPProcessor()).addUrl("http://521xunlei.com/portal.php").// 入口处
				thread(1).run();// 入口处
		
		//521xunlei.com
	}

	@Test
	public void test1() {
		List<int[]> asList = Arrays.asList(new int[] { 1, 2 });
		ArrayList<int[]> arrayList = new ArrayList<>(asList);
		arrayList.remove(0);
	}
}

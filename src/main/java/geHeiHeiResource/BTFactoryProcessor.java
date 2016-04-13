package geHeiHeiResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class BTFactoryProcessor implements PageProcessor {
	// 部分一：配置网站的相关配置，包括头部信息，抓取间隔，重试次数等
	private Site site = Site.me().setRetryTimes(3).setTimeOut(1000).setSleepTime(1)
			.addHeader("Referer", "hk.1024cc.info").setDomain("1024cc.info");

	private static String parentFilePath = "D:\\other\\image\\bt";// 下载的父路径
	private static Map<String, String> map = new HashMap<String, String>();

	@Override
	public void process(Page page) {
		String url = page.getUrl().toString();
		if (url.contains("thread")) {// 获取详细页列表
			List<String> urls = page.getHtml().xpath("//h3//a/@href").regex("htm_data.*.html").all();
			List<String> times = page.getHtml().xpath("//*[@id='ajaxtable']/tbody[2]/tr/td[5]/a/allText()").all();
			for (int i = 0; i < urls.size(); i++) {
				String date = times.get(i);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					Date date2 = format.parse(date);
					Date date3 = new Date();
					int x = date3.getDate() - date2.getDate();
					if (x <= 7) {// 7天前的资源才要
						page.addTargetRequest(urls.get(i));// 加入详情页
					} else {
						continue;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}

			// for (String string : urls) {
			// page.addTargetRequest(string);// 加入详情页
			// }
		} else if (url.contains("htm_data")) {// 进入详情页
			// ----------------标题start---------------------
			String title = page.getHtml().xpath("//h1[@id='subject_tpc']//allText()").get();
			// ----------------标题end---------------------
			// ----------------图片start-------------------

			List<String> bigpics = page.getHtml().xpath("//div[@class='tpc_content' and	 @id='read_tpc']//a/@href")
					.regex("http://.*.imageab.com/.*").all();
			for (String string : bigpics) {
				page.addTargetRequest(string);// 大图链接
				map.put(string, title);
			}

			List<String> smallIMG = page.getHtml().xpath("//div[@class='tpc_content' and @id='read_tpc']//img/@src")
					.regex(".*.jpg").all();
			for (String imageUrl : smallIMG) {
				if (imageUrl.length() > 35) {
					downImage(url, imageUrl, title);
				}
			}
			// ----------------图片end---------------------

			// ----------------下载链接start-------------------
			String downloadlinks = page.getHtml().xpath("//div[@class='tpc_content' and @id='read_tpc']//a/@href")
					.regex("http://.*.newstorrentsspace.info/freeone/file.php/.*").get();
			try {
				downText(url, downloadlinks, title);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// ----------------下载链接end---------------------

		} else if (url.contains("imageab")) {
			String bigimageUrl = page.getHtml().xpath("//div[@id='imageContainer']//table//tr//td//a/@href")
					.regex("http://.*.imageab.com/.*.jpg").get();//
			String bigimageUrl1 = page.getHtml().xpath("//div[@id='imageContainer']//table//tbody//tr//td//img@src")
					.regex("http://.*.imageab.com/.*.jpg").get();
			// 大图地址
			String title = map.get(url);
			if (StringUtils.isNotEmpty(bigimageUrl)) {
				downImage(url, bigimageUrl, title);
			} else {
				downImage(url, bigimageUrl1, title);
			}

		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	/**
	 * 保存文本
	 * @param line		解析文本
	 * @param pattern	匹配正则
	 * @param fileDir	下载目录
	 * @throws IOException
	 */
	public void downText(String referer, String line, String fileDir) throws Exception {
		if (StringUtils.isNotEmpty(fileDir) && StringUtils.isNotEmpty(line)) {
			fileDir = filterUseful(fileDir);// 去除空格,去除tab
			File file = new File(parentFilePath + File.separator + fileDir + File.separator + "Note.txt");

			if (!new File(parentFilePath + File.separator + fileDir).exists())// 创建目录
				new File(parentFilePath + File.separator + fileDir).mkdirs();
			if (!file.exists()) {// 新建文本
				file.createNewFile();
			}
			FileWriter fileWriter = null;
			fileWriter = new FileWriter(file);
			if (null != referer) {
				fileWriter.write("referer : " + referer + "\n");
			}
			fileWriter.write(line + "\n");
			fileWriter.close();
		}

	}

	/**
	 * 下载图片。下载成功返回true
	 * @param referer  打开的图片地址。破解防盗链！，如果为空，就不进行Referer设置
	 * @param imageUrl 下载路径
	 * @param fileDir 保存路径
	 * @throws Exception
	 * 
	 */
	public boolean downImage(String referer, String imageUrl, String fileDir) {
		if (StringUtils.isEmpty(imageUrl) || StringUtils.isEmpty(fileDir)) {
			return false;
		}
		fileDir = filterUseful(fileDir);// 去除空格去除tab
		String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);// 获取文件名
		String dir = parentFilePath + File.separator + fileDir;

		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		String result = dir + File.separator + imageName;// 目录+文件
		imageUrl = imageUrl.replaceAll(" +", "%20");// 解决名字空格问题
		OutputStream out = null;
		InputStream is = null;
		try {
			File imageFile = new File(result);
			if (!imageFile.exists()) {
				URL url = new URL(imageUrl);
				URLConnection conn = url.openConnection();
				conn.setRequestProperty("Referer", referer);
				conn.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
				conn.setConnectTimeout(2000);
				is = conn.getInputStream();
				out = new FileOutputStream(imageFile);
				byte[] buf = new byte[1024];
				int len = -1;
				while ((len = is.read(buf)) > -1) {
					out.write(buf, 0, len);
				}
				System.out.println(Thread.currentThread() + "下载图片位置 ：" + result);
				return true;
			} else {
				System.out.println(Thread.currentThread() + "已经存在 ： " + result);
			}
		} catch (Exception e) {
			System.err.println("下载文件" + imageUrl + "失败");
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
				if (null != is) {
					is.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return false;
	}

	public static void main(String[] args) throws InterruptedException {
		// http://hk.1024cc.info/pw/thread.php?fid=5&page=2 分页
		// Spider.create(new
		// BTFactoryProcessor()).addUrl("http://hk.1024cc.info/pw/thread.php?fid=5").//
		// 入口处
		// thread(2).run();// 入口处
		for (int i = 1; i < 8; i++) {

			// 国内
			Spider.create(new BTFactoryProcessor()).addUrl("http://1024.hegongchang.kim/pw/thread.php?fid=3" + i)
					.thread(2).run();// 入口处
			// 国内骑
			// Spider.create(new
			// BTFactoryProcessor()).addUrl("http://hk.1024cc.info/pw/thread.php?fid=22&page="
			// + i)
			// .thread(3).run();// 入口处
			// // 欧
			// Spider.create(new
			// BTFactoryProcessor()).addUrl("http://hk.1024cc.info/pw/thread.php?fid=7&page="
			// + i).// 入口处
			// thread(3).run();// 入口处
		}
		new BTFactoryProcessor().removeNoJPG();
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

	@Test
	public void removeNoJPG() {
		File file = new File(parentFilePath);
		File[] listFiles = file.listFiles();
		out1: for (File file2 : listFiles) {
			if (file2.isDirectory()) {
				File[] listFiles2 = file2.listFiles();
				boolean flag = true;
				for (File file3 : listFiles2) {
					if (file3.getName().endsWith(".jpg")) {
						flag = false;
					}
				}
				if (flag) {
					// 删除空文件
					file2.delete();
					System.out.println("删除无图" + file2);
					continue out1;
				} else {
					// System.out.println("有图" + file2);
				}
			}
		}
	}
}

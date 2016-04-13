package geHeiHeiResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爱弹幕
 * http://www.idanmu.com
 * @Title BTFactoryProcessor2
 * @Description 
 * @author ch
 * @Date 2016年2月16日 上午11:16:57
 */
public class IdanmuProcessor implements PageProcessor {
	// 部分一：配置网站的相关配置，包括头部信息，抓取间隔，重试次数等
	private Site site = Site.me().setRetryTimes(3).setTimeOut(1500).setSleepTime(10).addHeader("Host", "www.idanmu.com")
			.addHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36");

	private static String parentFilePath = "D:\\other\\image\\bt";// 下载的父路径
	private static Map<String, String> map = new HashMap<String, String>();

	@Override
	public void process(Page page) {
		String string3 = page.getUrl().toString();
		// 列表页
		List<String> all = page.getHtml().xpath("//*[@id='main']/div[1]/article/div/a[1]/@href").all();
		for (String string : all) {
			page.addTargetRequest(string);

			String[] split = string.split("/");
			String id = split[split.length - 1];
			String string4 = page.getHtml().toString();
			System.out.println(string4);
			// 详情页
			String xpath = "//*[@id='post-" + id + "']/div/div[3]/a[1]/@href";
			List<String> all2 = page.getHtml().xpath(xpath).all();
			for (String string2 : all2) {
				System.out.println();
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

	public static void main(String[] args) {
		// http://hk.1024cc.info/pw/thread.php?fid=5&page=2 分页
		// Spider.create(new
		// BTFactoryProcessor()).addUrl("http://hk.1024cc.info/pw/thread.php?fid=5").//
		// 入口处
		// thread(2).run();// 入口处
		Spider.create(new IdanmuProcessor()).addUrl("http://www.idanmu.com/category/v09/v10/").// 入口处
				thread(1).run();// 入口处
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
}

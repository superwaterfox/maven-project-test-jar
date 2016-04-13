package waterfox.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

public class FileList {
	private final static String IMGREGEX = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
	private String dir_name = null;
	Vector<String> ver = null;

	public FileList() {
	}

	public FileList(String dir_name) throws IOException {
		this.dir_name = dir_name; // 文件夹地址
		ver = new Vector<String>(); // 用做堆栈
	}

	public void getList() throws Exception {
		ver.add(dir_name);
		while (ver.size() > 0) {
			File[] files = new File(ver.get(0).toString()).listFiles(); // 获取该文件夹下所有的文件(夹)名
			ver.remove(0);

			int len = files.length;
			for (int i = 0; i < len; i++) {
				String tmp = files[i].getAbsolutePath();
				if (files[i].isDirectory()) {// 如果是目录，则加入队列。以便进行后续处理
					ver.add(tmp);
				} else if (tmp.matches(IMGREGEX) && !tmp.contains("_copy") && !tmp.contains(".thumb")
						&& !tmp.contains("_50_50") && !tmp.contains("_140") && !tmp.contains("_280")
						&& !tmp.contains("_zip")) {
					// ImageZipUtil.zipImageFile(new File(tmp),new
					// File(getNewName(tmp,"_140")),140,0,0.5f);
					// ImageZipUtil.zipImageFile(new File(tmp),new
					// File(getNewName(tmp,"_280")),280,0,0.5f);
					// ImageZipUtil.zipImageFile(new File(tmp),new
					// File(getNewName(tmp,"_zip")),0.5f);
					imageZip(tmp);
				}
			}
		}
		// out.close();
	}

	public void imageZip(String tmp) {
		if (!new File(getNewName(tmp, "_140")).exists()) {
			try {
				ImageIOUtil.scale(tmp, getNewName(tmp, "_140"), 140, 140);
				if (!new File(getNewName(tmp, "_140")).exists()) {
					copyImage(tmp, "_140");
					System.out.println("复制图片" + tmp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!new File(getNewName(tmp, "_280")).exists()) {
			try {
				ImageIOUtil.scale(tmp, getNewName(tmp, "_280"), 280, 280);
				if (!new File(getNewName(tmp, "_280")).exists()) {
					copyImage(tmp, "_280");
					System.out.println("复制图片" + tmp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!new File(getNewName(tmp, "_zip")).exists()) {
			try {
				ImageIOUtil.imageZip(tmp, getNewName(tmp, "_zip"));
				if (!new File(getNewName(tmp, "_zip")).exists()) {
					copyImage(tmp, "_zip");
					System.out.println("复制图片" + tmp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(tmp); // 如果是文件，则直接输出文件名到指定的文件。
	}

	public String getNewName(String src, String name) {
		if (src != null && src.length() > 0) {
			int len = src.lastIndexOf(".");
			return src.substring(0, len) + name + src.substring(len);
		}
		return null;
	}

	public void copyImage(String src, String name) {
		try {
			// 要拷贝的图片
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(src)));
			// 目标的地址
			int len = src.lastIndexOf(".");
			File file = new File(src.substring(0, len) + name + src.substring(len));
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdir();
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			try {
				int val = -1;
				while ((val = bis.read()) != -1) {
					bos.write(val);
				}
				bos.flush();
				bos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			FileList file = new FileList("F:\\logs");
			file.getList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
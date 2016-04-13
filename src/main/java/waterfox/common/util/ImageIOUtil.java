package waterfox.common.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;

public class ImageIOUtil {
	/**
	 * 图片伸缩，不破坏图片
	 * 
	 * @param srcFile 原图片路径
	 * @param dstFile 目标图片路径
	 * @param dstWidth 目标宽度
	 * @param dstHeight 目标高度
	 * @date 2013-11-1
	 */
	public static void scale(String srcFile, String dstFile, int dstWidth, int dstHeight) {

	  try {
	    ImageInputStream iis = ImageIO.createImageInputStream(new File(srcFile));

	    Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);

	    if (iterator.hasNext()) {
		    ImageReader reader = (ImageReader) iterator.next();
	
		    reader.setInput(iis, true);
	
		    BufferedImage source = null;
			try {
				source = reader.read(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		    if(source!=null&&source.getType()>0){
		    	int width = source.getWidth();
			    if(dstWidth<=0||width<dstWidth){
			    	dstWidth =width;
			    }
			    int height = source.getHeight();
			    if(dstHeight<=0||height<dstHeight){
			    	dstHeight =height;
			    }
			    BufferedImage tag = new BufferedImage(dstWidth, dstHeight, source.getType());
			    tag.getGraphics().drawImage(source, 0, 0, dstWidth, dstHeight, null);
			    File file = new File(dstFile);
			    ImageIO.write(tag, reader.getFormatName(), file);
		    }
	    }
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	}
	/**
	 * 图片剪裁，基于起始坐标(x,y)和范围[widthRange,heightRange]
	 * 
	 * @param srcFile 原图片路径
	 * @param dstFile 目标图片路径
	 * @param x 起始X
	 * @param y 起始Y
	 * @param widthRange 范围width
	 * @param heightRange 范围height
	 * @date 2013-11-1
	 */
	public static void cut(String srcFile, String dstFile, int x, int y, int widthRange,
	  int heightRange) {
	  try {

	    ImageInputStream iis = ImageIO.createImageInputStream(new File(srcFile));

	    Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);
	    if (iterator.hasNext()) {
		    ImageReader reader = (ImageReader) iterator.next();
	
		    reader.setInput(iis, true);
		    ImageReadParam param = reader.getDefaultReadParam();
		    Rectangle rectangle = new Rectangle(x, y, widthRange, heightRange);
		    param.setSourceRegion(rectangle);
		    BufferedImage bi = reader.read(0, param);
		    File file = new File(dstFile);
		    ImageIO.write(bi, reader.getFormatName(), file);
	    }
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
		  String srcFile = "F:\\222.jpg";
		  String dstFile = "F:\\333.jpg";
		  int width = 281;
		  int height = 200;
		  //cut(srcFile, dstFile, 10, 10, width, height);
		  scale(srcFile, dstFile, width, height);
		  //imageZip(srcFile,dstFile);
	}
	public static void imageZip(String srcFile,String dstFile){
		try {
			BufferedImage bi = ImageIO.read(new FileImageInputStream(new File(srcFile)));
			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
			if (iter.hasNext()&&bi!=null) {
				ImageWriter writer = iter.next();
				ImageWriteParam param = writer.getDefaultWriteParam();
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(0.5f);
				FileImageOutputStream out = new FileImageOutputStream(new File(dstFile));
				writer.setOutput(out);
				writer.write(null, new IIOImage(bi, null, null), param);
				out.close();
				writer.dispose();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package waterfox.project;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.mail.handlers.multipart_mixed;

import common.util.MathUtil;

/**
 * 通过区域选择，返回不同的数值。例如区间价格
 * @Title SelectArea
 * @Description 
 * @author ch
 * @Date 2016年5月5日 上午10:34:37
 */
public class SelectArea {

	public static final ArrayList<Double> areaList = new ArrayList<Double>();// 区间
	public static final Map<Double, Object> priceList = new HashMap<Double, Object>();// 金额

	public static double positiveInfinite = 0.0;// 负无穷
	public static double minusInfinity = 0.0;// 正无穷

	public static double x2 = 100.0;
	public static double x3 = 1000.0;
	static double x4 = 2000.0;
	static double x5 = +3000.0;

	static {
		areaList.add(x2);
		areaList.add(x3);
		areaList.add(x4);
		areaList.add(x5);

		priceList.put(x2, 200);
		priceList.put(x3, 500);
		priceList.put(x4, 1000);
		priceList.put(x5, 1500);
	}

	public BigDecimal areaSum = new BigDecimal(0.0);// 赎楼总额
	public BigDecimal priceSum = new BigDecimal(0.0);// 返利总额
	public double thisPrice = 0.0;
	public double nextPrice = 0.0;

	public SelectArea run(ArrayList<Double> arrayList) {

		for (Double double1 : arrayList) {
			areaSum = areaSum.add(new BigDecimal(double1.toString()));
		}

		Collections.sort(areaList);
		// 获得注入元素的集合
		ArrayList<Double> mutiList = new ArrayList<Double>(areaList);
		mutiList.add(areaSum.doubleValue());
		Collections.sort(mutiList);

		int indexOf = mutiList.indexOf(areaSum.doubleValue());
		int lastIndexOf = mutiList.lastIndexOf(areaSum.doubleValue());

		// 索引的位置---------------------------------------------------
		// 第一个,就取负无穷
		if (indexOf == 0) {
			thisPrice = positiveInfinite;
			nextPrice = areaList.get(0);
			priceSum = new BigDecimal(positiveInfinite);
		} else if (indexOf == mutiList.size()) {
			// 最后一个,那么取正无穷
			thisPrice = minusInfinity;
			nextPrice = minusInfinity;
			for (int i = indexOf; i >= 0; i--) {
				String fl = priceList.get(areaList.get(i)).toString();
				priceSum = priceSum.add(new BigDecimal(fl));
			}
			priceSum = new BigDecimal(MathUtil.add(minusInfinity, positiveInfinite));
		} else {
			// 重叠，要取后一个，而不是前一个
			if (indexOf != lastIndexOf) {
				for (int i = lastIndexOf; i >= 0; i--) {
					String fl = priceList.get(areaList.get(i)).toString();
					priceSum = priceSum.add(new BigDecimal(fl));
				}
				thisPrice = new BigDecimal(priceList.get(areaList.get(lastIndexOf)) + "").doubleValue();
				nextPrice = new BigDecimal(priceList.get(areaList.get(lastIndexOf+1)) + "").doubleValue();
			} else {
				for (int i = indexOf; i >= 0; i--) {
					String fl = priceList.get(areaList.get(i)).toString();
					priceSum = priceSum.add(new BigDecimal(fl));
				}
				thisPrice = new BigDecimal(priceList.get(areaList.get(indexOf)) + "").doubleValue();
				nextPrice = new BigDecimal(priceList.get(areaList.get(indexOf + 1)) + "").doubleValue();
			}

		}

		return this;
	}

	public static void main(String[] args) {

		ArrayList<Double> arrayList = new ArrayList<Double>();// 交易单
		arrayList.add(1000.0);
		// arrayList.add(80.0);
		// arrayList.add(80.0);
		// arrayList.add(180.0);
		// arrayList.add(1800.0);
		// arrayList.add(2800.0);
		// arrayList.add(3800.0);

		SelectArea fanLi = new SelectArea().run(arrayList);
		System.out.println("获得价格总额" + fanLi.priceSum);
		System.out.println("区域总量" + fanLi.areaSum);
		System.out.println();
		System.out.println("当前获得价格" + fanLi.thisPrice);
		System.out.println("下一获得价格" + fanLi.nextPrice);
	}
}

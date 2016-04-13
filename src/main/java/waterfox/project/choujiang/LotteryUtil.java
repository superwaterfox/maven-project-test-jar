package waterfox.project.choujiang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 不同概率抽奖工具包
 *
 * @author Shunli
 */
public class LotteryUtil {

	/**
	 * 获得百分比的区间。然后随机获得百分比，放入这个区间中，获得区间范围
	 * 特殊情况：
	 * 0.1    0.2   0.3   0   0.7
	 * 0-0.1  0.1-0.1+0.2   0.3-0.3+0.3    0.6-0.6+0    0.6-0.6+0.7
	 * 那么区间的大小就是   综合的概率值了，而且按照大小排序的话，  0概率的位置也会被左右两边的取代，插入的话也会选中左右两边而不是0的位置了
	 * @param orignalRates
	 * @return
	 */
	public static List<Double> getCompositeRates(List<Double> orignalRates) {
		if (orignalRates == null || orignalRates.isEmpty()) {
			throw new RuntimeException("没参数我怎么搞");
		}

		// 计算总概率，就可以比较每个子概率占总概率的百分比
		double sumRate = 0d;
		for (double rate : orignalRates) {
			sumRate += rate;
		}

		// 计算每个物品在总概率的基础下的概率情况
		int size = orignalRates.size();
		List<Double> sortOrignalRates = new ArrayList<Double>(size);
		Double tempSumRate = 0d;
		for (double rate : orignalRates) {
			tempSumRate += rate;// 叠加的概率区间
			sortOrignalRates.add(tempSumRate / sumRate);// 添加概率区间
		}
		return sortOrignalRates;
	}

	/**
	
	 * 根据上边的区间。利用随机数，挤进去这个区间范围
	 * @param sortOrignalRates
	 * @return
	 */
	public static int lottery(List<Double> sortOrignalRates) {
		List<Double> dest = new ArrayList<>(sortOrignalRates);// 复制数组
		// 根据区块值来获取抽取到的物品索引
		double nextDouble = Math.random();// 这里的随机数的获得是用java的虚拟机环境生成的，那么 可以使用
											// 苹果股票的开盘价+某地区的天气气温 分别的md5进行生成咯(天时地利任何才能获取)
		dest.add(nextDouble);
		Collections.sort(dest);// 添加一个随机数，并进行排序。那么获取该随机数的下标，就是获得原来下标的那个物品
		return dest.indexOf(nextDouble);
	}

}
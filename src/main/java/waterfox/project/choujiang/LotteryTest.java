package waterfox.project.choujiang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

/**
 *不同概率的抽奖原理
	 *1计算子概率和总概率的百分比(因为子概率的和不一定是1),那么就可以算出综合概率了
	 *2根据综合概率   ，分块区间      0-1进行分块
	 *3获取随机数，放入上边的区间中，获取下标。因为是严格按照物品的下标进行操作的。那么区间的下标就是物品的下标

因为是根据综合概率进行获取的，那么这里的概率可以理解为权重
负数和0的概率是支持的，而且不会影响其他的数据--都定义为不会被选中
 *
 */
public class LotteryTest {

	public static void main(String[] args) {

		List<Gift> gifts = new ArrayList<Gift>();
		// 序号==物品Id==物品名称==概率
		gifts.add(new Gift(1, "P1", "物品1", 0.2d));
		gifts.add(new Gift(2, "P2", "物品2", 0.2d));
		gifts.add(new Gift(3, "P3", "物品3", 0.4d));
		gifts.add(new Gift(4, "P4", "物品4", 0.3d));
		gifts.add(new Gift(5, "P5", "物品5", -0.3d));
		gifts.add(new Gift(6, "P6", "物品6", 0d));
		gifts.add(new Gift(7, "P7", "物品7", 0.008d));

		/**
		 * 将礼物的概率进行统一，如果是负数，那么归为0。因为要保证绝对的下标，这样获取才不会出错
		 */
		List<Double> orignalRates = new ArrayList<Double>(gifts.size());
		for (Gift gift : gifts) {
			double probability = gift.getProbability();
			if (probability < 0) {
				probability = 0;
			}
			orignalRates.add(probability); // 根据下标索引定位物品的下标，那么概率0也是作为下标补充放进去了
		}

		List<Double> sortRates = LotteryUtil.getCompositeRates(orignalRates);// 获得综合概率
		Map<Integer, Integer> count = new HashMap<Integer, Integer>();

		double num = 1000000;// 获得100万次，查看效果
		for (int i = 0; i < num; i++) {
			int orignalIndex = LotteryUtil.lottery(sortRates);// 获得随机数在有序概率集合中的位置
			Integer value = count.get(orignalIndex);
			count.put(orignalIndex, value == null ? 1 : value + 1);
		}

		for (Entry<Integer, Integer> entry : count.entrySet()) {
			System.out.println(
					gifts.get(entry.getKey()) + ",      获得数=" + entry.getValue() + ", 综合概率=" + entry.getValue() / num);
		}
	}

}

/**
 * 随机数操作公共类
 * @author Jerry
 * @version v1.0 RandomUtil.java 2013-9-25 下午06:15:36
 * 
 */
package waterfox.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RandomUtil 随机数操作公共类
 * @author Jerry
 * @version v1.0 RandomUtil.java 2013-9-25 下午06:15:36
 */
public class RandomUtil {
	public static void main(String[] args) {
		int[] random = getRandom(10,1);
	}
	
	private static AtomicInteger initCount;
	private static AtomicInteger initCount2;
	
	static {
		initCount = new AtomicInteger(6628);
		initCount2 = new AtomicInteger(7689);
	}
	
	public static void addCount() {
		int temp = new Random().nextInt(3);
		initCount.set(initCount.addAndGet(temp));
	}
	
	public static void addCount2() {
		int temp = new Random().nextInt(3);
		initCount2.set(initCount2.addAndGet(temp));
	}

	public static int getCount() {
		addCount();
		return initCount.get();
	}
	public static int getCount2() {
		addCount2();
		return initCount2.get();
	}
	
	/**
	 * ams20以内整形随机数
	 * @author Jerry
	 * @version v1.0 2013-9-25 下午06:18:43 
	 * @return 20以内整形随机数
	 */
	public static int getRandomIn20(){
		return new Random().nextInt(20);
	}
	
	/**
	 * 获取随机数
	 * @param count 随机数的范围 最大值
	 * @param index 随机数个数
	 * @return
	 */
	public static int[] getRandom(int count,int index){
		Map<Integer,Integer> map = new HashMap<Integer, Integer>();
		int[] arr = new int[index];
		if(count<=index){
			for(int i=0;i<index;i++){
				arr[i] = i;
			}
		}else{
			index--;
			for(int i=count-1;i>=0;i--){
				double n = (Math.random()*count);
				int num= (int)n;
				if(map.containsKey(num)){
					i++;
				}else{
					map.put(num, num);
					arr[index] = num;
					index--;
				}
				if(index < 0){
					break;
				}
			}
		}
		return arr;
	}
}

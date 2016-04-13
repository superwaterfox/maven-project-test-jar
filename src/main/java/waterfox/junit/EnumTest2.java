package waterfox.junit;

public class EnumTest2 {

	/**
	 * 枚举的注意事项：枚举只能放到最后，因为是遍历到最后一个参数进行封装到数组中的
	 * @param str
	 * @param ars   原理:new一个指定类型的数组，然后封装进数组中，结果还是要遍历获得
	 */
	public static String test1(String str, int... ars) {
		for (int i : ars) {
			System.out.println(i);
		}
		return str;
	}

	public static void main(String[] args) {
		test1("1", 2, 3, 4);
		double str = 1 == 2 ? 1 : 1.0;//三元预算必须有返回值，而且返回值类型根据true和false的返回值类型的最高类型
		int i = 1 == 2 ? 1 : 2;
		
	}
}

package waterfox.junit;

/**
 * 枚举和switch的使用方式
 * @Title Test1
 * @Description 
 * @author ch
 * @Date 2015年12月27日 下午8:48:17
 */
public class EnumTest1 {

	public enum Product {
		// 通过构造函数构造对象
		car1, shoes1, // 使用空构造函数的对象，没有实际参数可以拿出来使用
		car("jack", "这个车是jack的"), shoes("may", "这双鞋是may的") {
			@Override
			public String getTitle() {
				return "可以自定义对象里边的方法";
			}
		};// 自定义构造函数，可以根据该对象取出参数
			// ----------------构造函数start---------------------
		private Product() {
		}

		Product(String name, String title) {
			this.name = name;
			this.title = title;
		}

		// ----------------构造函数end---------------------
		private String name;
		private String title;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

	}

	public static void main(String[] args) {
		// ----------------获得枚举的方式有两种start---------------------
		Product product = Product.car;
		// Product valueOf = product.valueOf("car");//获得该枚举对象
		// ----------------获得枚举的方式有两种end---------------------

		switch (product) {
		case car:
			String name = product.name();// 获得该枚举对象的名称
			System.out.println("枚举在整个枚举类中的序号:" + product.ordinal() + "枚举对象名称:" + name + "自定义参数1:" + product.name
					+ "自定义参数2:" + product.title);
			break;
		case shoes1:
			break;
		default:
			break;
		}
	}
}

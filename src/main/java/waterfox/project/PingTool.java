package waterfox.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingTool {

	public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
		BufferedReader in = null;
		Runtime r = Runtime.getRuntime(); // 将要执行的ping命令,此命令是windows格式的命令
		String pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w " + timeOut;
		try { // 执行命令并获取输出
			System.out.println(pingCommand);
			Process p = r.exec(pingCommand);
			if (p == null) {
				return false;
			}
			in = new BufferedReader(new InputStreamReader(p.getInputStream())); // 逐行检查输出,计算类似出现=23ms
																				// TTL=62字样的次数
			int connectedCount = 0;
			String line = null;
			while ((line = in.readLine()) != null) {
				connectedCount += getCheckResult(line);
			} // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
			return connectedCount == pingTimes;
		} catch (Exception ex) {
			ex.printStackTrace(); // 出现异常则返回假
			return false;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
	private static int getCheckResult(String line) { // System.out.println("控制台输出的结果为:"+line);
		Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		// String ipAddress = "127.0.0.1";
		// System.out.println(ping(ipAddress, 5, 1500));
//		Socket socket = new Socket("127.0.0.1", 80);
//		InetSocketAddress socketAddress = new InetSocketAddress("192.168.254.222", 80);
//		socket.connect(socketAddress);
	}

	// public static void main(String[] args) {
	// String startIp = "192.168.254.1";
	// String endIp = "192.168.254.254";
	// String port = "22";
	// ArrayList<String> list = new ArrayList<String>();
	//
	// String[] split = startIp.split("\\.");
	// String ta1 = split[0];
	// String ta2 = split[1];
	// String ta3 = split[2];
	// String ta4 = split[3];
	//
	// String[] split1 = endIp.split("\\.");
	// String ta11 = split1[0];
	// String ta21 = split1[1];
	// String ta31 = split1[2];
	// String ta41 = split1[3];
	//
	// int ip1 = Integer.parseInt(ta1.trim());// ＩＰ第一个字段
	//
	// int ip11 = Integer.parseInt(ta11.trim());
	//
	// int ip2 = Integer.parseInt(ta2.trim());// ＩＰ第2个字段
	//
	// int ip21 = Integer.parseInt(ta21.trim());
	//
	// int ip3 = Integer.parseInt(ta3.trim());// ＩＰ第3个字段
	//
	// int ip31 = Integer.parseInt(ta31.trim());
	//
	// int ip4 = Integer.parseInt(ta4.trim());// ＩＰ第4个字段
	//
	// int ip41 = Integer.parseInt(ta41.trim());
	//
	// final int port1 = Integer.parseInt(port.trim());
	// // 四循环
	// for (int i = ip1; i <= ip11; i++) {
	// for (int j = ip2; j <= ip21; j++) {
	// for (int k = ip3; k <= ip31; k++) {
	// for (int m = ip4; m <= ip41; m++) {
	// final String ip = String.valueOf(i) + "." + String.valueOf(j) + "." +
	// String.valueOf(k) + "."
	// + String.valueOf(m);
	// Socket s = null;
	// try {
	// s = new Socket(ip, port1);
	// InetSocketAddress socketAddress = new InetSocketAddress("", 80);
	// s.connect(socketAddress, 500);
	// list.add(ip);
	// } catch (Exception e) {
	// // e.printStackTrace();
	// } finally {
	// try {
	// if (s != null)
	// s.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }
	// // 如果连接成功，将ＩＰ添加到结果集合中
	//
	// }
	// }
	// }
	//
	// }
	// for (String string : split1) {
	// System.out.println(string);
	// }
	// }
}
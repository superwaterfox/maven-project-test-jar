package waterfox.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 线程工具类
 * @author limh
 *
 */
public class ThreadUtil {
	/**
	 * 根据线程名称得到正在运行的线程
	 * @param name
	 * @return
	 */
	public static Thread getThreadByName(String name){
		if(StringUtils.isEmpty(name)){
			return null;
		}
		ThreadGroup group = Thread.currentThread().getThreadGroup();  
		ThreadGroup topGroup = group;  
		// 遍历线程组树，获取根线程组  
		while (group != null) {  
		    topGroup = group;  
		    group = group.getParent();  
		}  
		// 激活的线程数加倍  
		int estimatedSize = topGroup.activeCount() * 2;  
		Thread[] slackList = new Thread[estimatedSize];  
		// 获取根线程组的所有线程  
		int actualSize = topGroup.enumerate(slackList);  
		// copy into a list that is the exact size  
		Thread[] list = new Thread[actualSize];  
		System.arraycopy(slackList, 0, list, 0, actualSize);  
		System.out.println("Thread list size == " + list.length);  
		for (Thread thread : list) {
			if(thread.getName().equals(name)){
				return thread;
			}
		}  
		return null;
	}
	
	/**
	 * 根据线程名称结束线程
	 * @param name
	 */
	@SuppressWarnings("deprecation")
	public static void stopThreadByName(String name){
		if(StringUtils.isEmpty(name)){
			return;
		}
		Thread thread = getThreadByName(name);
		if(thread!=null){
			thread.stop();
		}
	}
}

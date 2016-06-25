package cn.tuxt.log4j;

import org.apache.log4j.Logger;

public class Demo1 {
	private static Logger logger = Logger.getLogger(Demo1.class);
	public static void test(){
		logger.debug("this is a debug message.");
		logger.error("this is a error message.", new NullPointerException("引用了一个空对象"));
	}
	public static void main(String[] args) {
		test();
	}

}

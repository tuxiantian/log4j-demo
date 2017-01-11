package cn.tuxt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class LogUtilTest {

	@Test
	public void test1() throws FileNotFoundException, IOException {
		Map params=new HashMap<String, Object>();
		params.put("name", "tom");
		params.put("age", 12);
		params.put("pic", IOUtils.toString(new FileInputStream(new File("E:/test/wzh/signPic_encrypt.jpg"))));
		LogUtil.getInstance(LogUtilTest.class).error("test LogUtil", params);
		//test LogUtil>>>{"name":"tom","pic":"pic_replaced","age":12}
	}
}

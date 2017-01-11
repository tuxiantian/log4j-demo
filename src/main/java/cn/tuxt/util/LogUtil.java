package cn.tuxt.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class LogUtil {
	private static Properties prop;
	private static Log4JLogger log = (Log4JLogger) LogFactory
			.getLog(LogUtil.class);
	private static final String FQCN = LogUtil.class.getName();

	private Logger logger = null;

	private LogUtil(Class<?> clazz) {
		this.logger = Logger.getLogger(clazz);
	}

	public static LogUtil getInstance(Class<?> clazz) {
		return new LogUtil(clazz);
	}

	private static String filterPicLog(String key, Map<String, Object> inputMap) {
		String isoutput = LogUtil.getString("log4j.filterOutputPic");
		// 文件流字符串长度
		String piclength = LogUtil.getString("log4j.filterPicLength");
		if ("true".equalsIgnoreCase(isoutput)) {
			if (piclength != null && piclength.trim().length() > 0) {
				long length = Long.valueOf(piclength.trim());
				filterPicMap(inputMap, length);
			}
		}
		return key + ">>>" + JSON.toJSONString(inputMap);
	}

	private static String filterPicLog(String key, JSONObject json) {
		return filterPicLog(key, parseJSON2Map(json));
	}

	private static String getString(String key) {
		InputStream is=null;
		try {
			if (prop == null) {
				prop=new Properties(); 
				String path = LogUtil.class.getClassLoader().getResource("log4j.properties").getPath();  
				is = new FileInputStream(path);  
				prop.load(is);  
			}
			return prop.getProperty(key);
		} catch (Exception e) {
			log.error(String.format("get prop key=%s,error", key), e);
			return null;
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					log.info("", e);
				}
			}
		}
	}

	public void trace(Object message) {
		this.logger.log(FQCN, Priority.DEBUG, message, null);
	}

	public void trace(Object message, Throwable t) {
		this.logger.log(FQCN, Priority.DEBUG, message, t);
	}

	public void debug(Object message) {
		this.logger.log(FQCN, Priority.DEBUG, message, null);
	}

	public void debug(Object message, Throwable t) {
		this.logger.log(FQCN, Priority.DEBUG, message, t);
	}

	public void info(Object message) {
		this.logger.log(FQCN, Priority.INFO, message, null);
	}

	public void info(Object message, Throwable t) {
		this.logger.log(FQCN, Priority.INFO, message, t);
	}

	public void warn(Object message) {
		this.logger.log(FQCN, Priority.WARN, message, null);
	}

	public void warn(Object message, Throwable t) {
		this.logger.log(FQCN, Priority.WARN, message, t);
	}

	public void error(Object message) {
		this.logger.log(FQCN, Priority.ERROR, message, null);
	}

	public void error(Object message, Throwable t) {
		this.logger.log(FQCN, Priority.ERROR, message, t);
	}

	public void fatal(Object message) {
		this.logger.log(FQCN, Priority.FATAL, message, null);
	}

	public void fatal(Object message, Throwable t) {
		this.logger.log(FQCN, Priority.FATAL, message, t);
	}

	public Logger getLogger() {
		return this.logger;
	}

	public boolean isDebugEnabled() {
		return this.logger.isDebugEnabled();
	}

	public boolean isErrorEnabled() {
		return this.logger.isEnabledFor(Priority.ERROR);
	}

	public boolean isFatalEnabled() {
		return this.logger.isEnabledFor(Priority.FATAL);
	}

	public boolean isInfoEnabled() {
		return this.logger.isInfoEnabled();
	}

	public boolean isTraceEnabled() {
		return this.logger.isDebugEnabled();
	}

	public boolean isWarnEnabled() {
		return this.logger.isEnabledFor(Priority.WARN);
	}

	public void error(String key, Map<String, Object> mapMessage) {
		if(mapMessage!=null){
			String json=JSON.toJSONString(mapMessage);
			error(filterPicLog(key, JSON.parseObject(json)));
		}else{
			error(key);
		}
		
	}

	public void error(String key, JSONObject jsonMessage) {
		error(filterPicLog(key, jsonMessage));
	}

	public void error(String key, Map<String, Object> mapMessage, Throwable t) {
		if(mapMessage!=null){
			String json=JSON.toJSONString(mapMessage);
			error(filterPicLog(key, JSON.parseObject(json)),t);
		}else{
			error(key,t);
		}
	}

	public void error(String key, JSONObject jsonMessage, Throwable t) {
		error(filterPicLog(key, jsonMessage), t);
	}
	//info
	public void info(String key, Map<String, Object> mapMessage) {
		if(mapMessage!=null){
			String json=JSON.toJSONString(mapMessage);
			info(filterPicLog(key, JSON.parseObject(json)));
		}else{
			info(key);
		}
		
	}

	public void info(String key, JSONObject jsonMessage) {
		info(filterPicLog(key, jsonMessage));
	}

	public void info(String key, Map<String, Object> mapMessage, Throwable t) {
		if(mapMessage!=null){
			String json=JSON.toJSONString(mapMessage);
			info(filterPicLog(key, JSON.parseObject(json)),t);
		}else{
			info(key,t);
		}
	}

	public void info(String key, JSONObject jsonMessage, Throwable t) {
		info(filterPicLog(key, jsonMessage), t);
	}
	
	private static Map<String, Object> parseJSON2Map(JSONObject json) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<Object> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = (JSONObject) it.next();
					list.add(parseJSON2Map(json2));
				}
				map.put(k.toString(), list);
			} else if (v instanceof JSONObject) {
				Map<String, Object> map2 = parseJSON2Map((JSONObject) v);
				map.put(k.toString(), map2);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String, Object> filterPicMap(Map<String, Object> inputMap,long length){
		if(inputMap!=null){
			for(Map.Entry<String, Object> entry:inputMap.entrySet()){
				Object v=entry.getValue();
				if(v instanceof ArrayList){
					for (Map<String, Object> map:(ArrayList<Map<String, Object>>)v) {
						filterPicMap(map,length);
					}
				}else if(v instanceof HashMap){
					filterPicMap((Map<String, Object>)v,length);
				}else{
					if(v.toString().length()>=length){
						entry.setValue("pic_replaced");
					}
				}
			}
		}
		
		return inputMap;
	}
	/**
	 * 过滤map中的图片参数，过滤后会影响map中的值
	 * @param inputMap
	 * @return
	 */
	private static String handPicLog(Map<String, Object> inputMap) {
		String isoutput = LogUtil.getString("log4j.filterOutputPic");
		// 文件流字符串长度
		String piclength = LogUtil.getString("log4j.filterPicLength");
		if ("true".equalsIgnoreCase(isoutput)) {
			if (piclength != null && piclength.trim().length() > 0) {
				long length = Long.valueOf(piclength.trim());
				filterPicMap(inputMap, length);
			}
		}
		return JSON.toJSONString(inputMap);
	}

	public static String handPicLog(JSONObject json) {
		return handPicLog(parseJSON2Map(json));
	}
}

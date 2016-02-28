package com.jayu.note.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class AttributeUtil {
	static final String SP = ";";
	static final String SSP = ":";
	
	static final String R_SP = "#3A";
	static final String R_SSP = "#3B";
	
	/**
	 * 通过Map转换成String
	 * @param attrs
	 * @return
	 */
	public static final String toString(Map<String, String> attrs) {
		StringBuilder sb = new StringBuilder();
		if (null != attrs && !attrs.isEmpty()) {
			sb.append(SP);
			for (String key : attrs.keySet()) {
				String val = attrs.get(key);
				if (StringUtils.isNotEmpty(val)) {
					sb.append(encode(key)).append(SSP).append(encode(val)).append(SP);
				}
			}
		}
		return sb.toString();
	}
	
	public static final String toString(String key, String val) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(val)) {
			sb.append(SP);
			sb.append(encode(key)).append(SSP).append(encode(val));
			sb.append(SP);
		}
		return sb.toString();
	}
	
	/**
	 * 通过字符串解析成attributes
	 * @param str
	 * @return
	 */
	public static final Map<String, String> fromString(String str) {
		Map<String, String> attrs = new HashMap<String, String>();
		if (StringUtils.isNotBlank(str)) {
			String[] arr = str.split(SP);
			if (null != arr) {
				for (String kv : arr) {
					if (StringUtils.isNotBlank(kv)) {
						String[] ar = kv.split(SSP);
						if (null != ar && ar.length == 2) {
							String key = decode(ar[0]);
							String val = decode(ar[1]);
							if (StringUtils.isNotEmpty(val)) {
								attrs.put(key, val);
							}
						}
					}
				}
			}
		}
		return attrs;
	}
	
	/**
	 * 分阶段订单的退款，拼接服务子订单信息列表的字符串
	 * by yichen.gfh 2012.09.20
	 * @param serviceMap
	 * @return
	 */
	public static String getServiceDetailString(Map<Long, Long> serviceMap) {
		if(serviceMap == null) {
    		return null;
    	}
    	
    	StringBuffer attrValue = new StringBuffer();
    	Iterator<Entry<Long, Long>> it = serviceMap.entrySet().iterator();
    	while(it.hasNext()) {
    		Entry<Long, Long> entry = it.next();
    		Long key = entry.getKey();
    		Long value = entry.getValue();
    		if(key != null && value != null) {
    			if(attrValue.length() > 0) {
    				attrValue.append('|');
    			}
    			attrValue.append(key);
    			attrValue.append(',');
    			attrValue.append(value);
    		}
    	}
    	
    	return attrValue.toString();
	}
	
	private static String encode(String val) {
		return StringUtils.replace(StringUtils.replace(val, SP, R_SP), SSP, R_SSP);
	}
	
	private static String decode(String val) {
		return StringUtils.replace(StringUtils.replace(val, R_SP, SP), R_SSP, SSP);
	}
}

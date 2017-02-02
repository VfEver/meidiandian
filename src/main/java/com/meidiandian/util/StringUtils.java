package com.meidiandian.util;
/**
 * String util class
 * @author zys
 *
 */
public class StringUtils {
	
	public static boolean isEmpty (String string) {
		if (string != null && string.length()>0 && !"".equals(string)) {
			return true;
		}
		return false;
	}
}

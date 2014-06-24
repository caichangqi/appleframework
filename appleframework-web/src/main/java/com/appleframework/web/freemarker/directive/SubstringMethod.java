package com.appleframework.web.freemarker.directive;

import java.util.List;

import org.springframework.stereotype.Component;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

@SuppressWarnings("deprecation")
@Component("substringMethod")
public class SubstringMethod implements TemplateMethodModel {
	
	public static final String TAG_NAME = "substring";

	@SuppressWarnings({ "rawtypes" })
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments.size() == 2) {
			String str = arguments.get(0).toString();
			Integer length = Integer.valueOf(arguments.get(1).toString());
			return new SimpleScalar(substring(str, length, null));
		} else if (arguments.size() == 3) {
			String str = arguments.get(0).toString();
			Integer length = Integer.valueOf(arguments.get(1).toString());
			String moreSuffix = arguments.get(2).toString();
			return new SimpleScalar(substring(str, length, moreSuffix));
		} else {
			throw new TemplateModelException("Wrong arguments");
		}
	}
	
	/**
     * 根据字节长度截取字符串前部分
     * 
     * @param str
     *        原字符
     * 
     * @param length
     *        截取字节长度
     * 
     * @param moreSuffix
     *        后缀
     * 
     * @return String
	*/
	@SuppressWarnings("static-access")
	public static String substring(String str, int length, String moreSuffix) {
		if (str == null) {
			return "";
		}
		int reInt = 0;
		String reStr = "";
		char[] tempChar = str.toCharArray();
		for (int i = 0; (i < tempChar.length && length > reInt); i ++) {
			byte[] bytes = str.valueOf(tempChar[i]).getBytes();
			reInt += bytes.length;
			reStr += tempChar[i];
		}
		if (moreSuffix != null && (length == reInt || (length == reInt - 1))) {
			reStr += moreSuffix;
		}
		return reStr;
	}

}
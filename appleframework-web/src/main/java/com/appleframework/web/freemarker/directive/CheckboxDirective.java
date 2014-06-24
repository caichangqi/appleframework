package com.appleframework.web.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.appleframework.web.freemarker.util.DirectiveUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("checkboxDirective")
public class CheckboxDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "checkbox";
	private static final String NAME_PARAMETER_NAME = "name";
	private static final String VALUE_PARAMETER_NAME = "value";
	private static final String FIELD_VALUE_PARAMETER_NAME = "fieldValue";
	private static final String CSS_CLASS_PARAMETER_NAME = "cssClass";
	private static final String CSS_STYLE_PARAMETER_NAME = "cssStyle";
	private static final String DISABLED_PARAMETER_NAME = "disabled";
	private static final String READONLY_PARAMETER_NAME = "readonly";
	private static final String TABINDEX_PARAMETER_NAME = "tabindex";
	private static final String ID_PARAMETER_NAME = "id";
	private static final String TITLE_PARAMETER_NAME = "title";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String id = DirectiveUtil.getStringParameter(ID_PARAMETER_NAME, params);
		String name = DirectiveUtil.getStringParameter(NAME_PARAMETER_NAME, params);
		String fieldValue = DirectiveUtil.getStringParameter(FIELD_VALUE_PARAMETER_NAME, params);
		String cssClass = DirectiveUtil.getStringParameter(CSS_CLASS_PARAMETER_NAME, params);
		String cssStyle = DirectiveUtil.getStringParameter(CSS_STYLE_PARAMETER_NAME, params);
		String title = DirectiveUtil.getStringParameter(TITLE_PARAMETER_NAME, params);
		String tabindex = DirectiveUtil.getStringParameter(TABINDEX_PARAMETER_NAME, params);
		Boolean value = DirectiveUtil.getBooleanParameter(VALUE_PARAMETER_NAME, params);
		Boolean disabled = DirectiveUtil.getBooleanParameter(DISABLED_PARAMETER_NAME, params);
		Boolean readonly = DirectiveUtil.getBooleanParameter(READONLY_PARAMETER_NAME, params);
		
		if (id == null) {
			id = name.replace(".", "_");
		}
		if (name == null) {
			name = ""; 
		}
		if (fieldValue == null) {
			fieldValue = "true";
		}
		if (value == null) {
			value = false;
		}
		if (disabled == null) {
			disabled = false;
		}
		if (readonly == null) {
			readonly = false;
		}
		
		Writer out = env.getOut();
		StringBuffer stringBuffer = new StringBuffer("<input type=\"checkbox\" name=\"" + name + "\" value=\"" + fieldValue + "\"");
		if (StringUtils.isNotEmpty(id)) {
			stringBuffer.append(" id=\"" + id + "\"");
		}
		if (StringUtils.isNotEmpty(cssClass)) {
			stringBuffer.append(" class=\"" + cssClass + "\"");
		}
		if (StringUtils.isNotEmpty(cssStyle)) {
			stringBuffer.append(" style=\"" + cssStyle + "\"");
		}
		if (StringUtils.isNotEmpty(title)) {
			stringBuffer.append(" title=\"" + title + "\"");
		}
		if (StringUtils.isNotEmpty(tabindex)) {
			stringBuffer.append(" tabindex=\"" + title + "\"");
		}
		if (value) {
			stringBuffer.append(" checked=\"checked\"");
		}
		if (disabled) {
			stringBuffer.append(" disabled=\"disabled\"");
		}
		if (readonly) {
			stringBuffer.append(" readonly=\"readonly\"");
		}
		stringBuffer.append(" /><input type=\"hidden\" id=\"__checkbox_" + id + "\" name=\"__checkbox_" + name + "\" value=\"" + fieldValue + "\"");
		if (disabled) {
			stringBuffer.append(" disabled=\"disabled\"");
		}
		stringBuffer.append(" />");
		out.write(stringBuffer.toString());
	}

}
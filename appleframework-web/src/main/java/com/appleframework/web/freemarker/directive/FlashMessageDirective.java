package com.appleframework.web.freemarker.directive;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.appleframework.web.bean.Message;
import com.appleframework.web.freemarker.directive.BaseDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("flashMessageDirective")
public class FlashMessageDirective extends BaseDirective {

	public static final String FLASH_MESSAGE_ATTRIBUTE_NAME = "FLASH_MESSAGE";

	private static final String VARIABLE_NAME = "flashMessage";

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) 
			throws TemplateException, IOException {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			Message message = (Message) requestAttributes.getAttribute(FLASH_MESSAGE_ATTRIBUTE_NAME, RequestAttributes.SCOPE_REQUEST);
			setLocalVariable(VARIABLE_NAME, message, env, body);
		}
	}

}
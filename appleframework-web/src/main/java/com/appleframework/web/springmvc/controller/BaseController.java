package com.appleframework.web.springmvc.controller;

import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appleframework.web.bean.Message;
import com.appleframework.web.bean.Message.Type;
import com.appleframework.web.freemarker.directive.FlashMessageDirective;

public class BaseController {
	
	protected static final String ERROR_VIEW = "/commons/error";
	
	protected static final String SUCCESS_VIEW = "/commons/success";
	
	protected static final Message ERROR_MESSAGE = Message.error("操作错误");

	protected static final Message SUCCESS_MESSAGE = Message.success("操作成功");

	protected static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";

	@Resource(name = "validator")
	protected Validator validator;

	protected boolean isValid(Object target, Class<?>... groups) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
			return false;
		}
	}

	protected boolean isValid(Class<?> type, String property, Object value, Class<?>... groups) {
		Set<?> constraintViolations = validator.validateValue(type, property, value, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
			return false;
		}
	}

	protected void addFlashMessage(RedirectAttributes redirectAttributes, Message message) {
		if (redirectAttributes != null && message != null) {
			redirectAttributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}
	
	protected void addSuccessMessage(Model model, String content, String url) {
		if ( model != null) {
			Message message = new Message(Type.success, content, url);
			model.addAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}
	
	protected void addSuccessMessage(Model model, String url) {
		if ( model != null) {
			Message message = new Message(Type.success, url);
			model.addAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}
	
	protected void addErrorMessage(Model model, String content) {
		if ( model != null) {
			Message message = new Message(Type.error, content, "");
			model.addAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}
	
	protected String ajax(String content) {
		return content.trim();
	}

}

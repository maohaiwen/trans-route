package com.trans.route.util.system.validate;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.trans.route.util.common.ExceptionConstants;


public class ValidateInteceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod methodHandler=(HandlerMethod) handler;
		Validate validate = methodHandler.getMethodAnnotation(Validate.class);
		if(validate == null) {
			return true;
		}
		
		return doValidate(request, response, validate);
		
	}

	private boolean doValidate(HttpServletRequest request,HttpServletResponse response, Validate validate) {
		
		doValidateByColumns(validate.columns(), request);
		doValidateByClazz(validate.validatorClass(), request, response);
		
		return true;
	}

	private void doValidateByColumns(String[] columns, HttpServletRequest request) {
		
		if(columns == null || columns.length == 0) {
			return;
		}
		
		for(String column : columns) {
			if(StringUtils.isEmpty(column)) {
				continue;
			}
			String[] columnArr = column.split(",");
			if(columnArr == null || columnArr.length < 2) {
				continue;
			}
			String columnName = columnArr[0];
			String validateType = columnArr[1];
			String validateMessage = columnArr[2];
			
			String requestParam = request.getParameter(columnName);
			if(validateType.trim().equals("notnull")) {
				if(StringUtils.isEmpty(requestParam)) {
					throw new ValidateException(ExceptionConstants.Validate.NOT_NULL, validateMessage);
				}
			}else {
				// TODO 
			}
		}
	}

	private void doValidateByClazz(String clazzPath, HttpServletRequest request, HttpServletResponse response) {
		
		if(StringUtils.isEmpty(clazzPath)) {
			return;
		}
		
		try {
			Class<?> clazz = Class.forName(clazzPath);
			Object validator = clazz.newInstance();
			Method method = clazz.getMethod("doValidate", HttpServletRequest.class);
			method.invoke(validator, request, response);
		}catch (ValidateException e) {
			throw e;
		} catch (Exception e) {
			throw new ValidateException(ExceptionConstants.Validate.VALIDATOR_INIT, "can not execute doValidate in class [" + clazzPath + "]");
		} 
		
	}

	
}

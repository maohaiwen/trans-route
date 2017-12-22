package com.trans.route.util.system.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.trans.route.util.common.CommonResp;
import com.trans.route.util.common.DispatcherConstants;
import com.trans.route.util.system.validate.ValidateException;

public class CustomExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ex.printStackTrace();
		String requestedWith = request.getHeader("x-requested-with");
		if(requestedWith == null) {
			try {
				boolean instanceOfValidate = false;
				boolean instanceOfBusiness = false;
				if((instanceOfValidate = (ex instanceof ValidateException))
						|| (instanceOfBusiness = (ex instanceof BusinessException))) {

					String message = "";
					if(instanceOfValidate) {
						message = ((ValidateException)ex).getErrorMsg();
					}
					if(instanceOfBusiness) {
						message = ((BusinessException)ex).getErrorMsg();
					}
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write("<script>alert(\"" + message + "\")</script>");
				}else {
					response.sendRedirect(DispatcherConstants.HOME_PAGE);
				}
			} catch (IOException e) {

			}
		}else {
			int errorCode = 500;
			String errorMessage = "服务器发生未知错误！";

			if(ex instanceof ValidateException) {
				errorCode = ((ValidateException) ex).getErrorCode();
				errorMessage = ((ValidateException) ex).getErrorMsg();
			}
			if(ex instanceof BusinessException) {
				errorCode = ((BusinessException) ex).getErrorCode();
				errorMessage = ((ValidateException) ex).getErrorMsg();
			}
			return getAjaxReturn(response, errorCode, errorMessage);
		}

		return new ModelAndView();
	}

	private ModelAndView getAjaxReturn(HttpServletResponse response, int errorCode, String errormSG) {
		ModelAndView mv = new ModelAndView();
		/*	使用response返回	*/
		response.setStatus(HttpStatus.OK.value()); //设置状态码
		response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
		response.setCharacterEncoding("UTF-8"); //避免乱码
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		try {
			CommonResp resp = new CommonResp(errorCode, errormSG);
			response.getWriter().write(JSONObject.toJSONString(resp));
		} catch (IOException e) {
			//log.error("与客户端通讯异常:"+ e.getMessage(), e);
		}

		return mv;
	}
}

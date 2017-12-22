package com.trans.route.util.system.validate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IValidator {

	public void doValidate(HttpServletRequest request, HttpServletResponse response) throws ValidateException;
}

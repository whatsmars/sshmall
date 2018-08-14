package com.$2012.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.$2012.entity.user.User;
import com.$2012.utils.*;


/*
 * 用户登录过滤器-进入结算中心前必须已登录
 */
public class UserLoginFilter2 implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * 如在session中未找到用户，自动跳转到登录页面
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		User user = WebUtils.getUser(request);
		if (user == null) {
			HttpServletResponse response = (HttpServletResponse) res;
			response.sendRedirect("/page/user/front/login.jsp?toUI=myCenterUI");
		} else {
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}

package com.meidiandian.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.meidiandian.util.StringUtils;

public class LoginFilter implements Filter{

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		HttpSession session = request.getSession(true);
		
		String id = String.valueOf(session.getAttribute("id"));
		String url = request.getRequestURI();
		//忽略登录注册退出
		if (url.contains("login") || 
				!url.contains(".do") || 
				url.contains("register") || 
				url.contains("logout")) {
			chain.doFilter(request, response);
			return ;
		}
		if (StringUtils.isEmpty(id)) {
			response.sendRedirect(request.getContextPath()+"/#login");
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}

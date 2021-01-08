package com.fengjian.crowd.mvc.interceptor;

import com.fengjian.crowd.constant.Constant;
import com.fengjian.crowd.entity.Admin;
import com.fengjian.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Admin admin = (Admin)session.getAttribute(Constant.ATTR_NAME_LOGIN_ADMIN);
        if (admin == null) {
            throw new AccessForbiddenException(Constant.MESSAGE_ACCESS_FORBIDDEN);
        }
        return true;
    }

}

package com.viv.inteceptor;

import com.viv.Config;
import com.viv.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by viv on 16-4-22.
 */
public class SudoInteceptor implements HandlerInterceptor {
    public void afterCompletion(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void postHandle(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public boolean preHandle(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o) throws Exception {
        if(httpServletRequest.getSession().getAttribute(Config.login_user) == null){
           httpServletResponse.sendRedirect("/visitor/login");
            return false;
        }
        User user = (User) httpServletRequest.getSession().getAttribute(Config.login_user);
        if (user.getSudo() != 2) {
            httpServletResponse.sendRedirect("/");
            return false;
        }
        return true;
    }
}

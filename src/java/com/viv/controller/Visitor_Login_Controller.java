package com.viv.controller;

import com.viv.Config;
import com.viv.entity.User;
import com.viv.exception.ControllerException;
import com.viv.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
@Controller
@RequestMapping(value = "/visitor")
public class Visitor_Login_Controller {
    UserService userService = new UserService();
    /*---------------------------------page-start-----------------------------------*/
    @RequestMapping(value = "/login")
    public String login(){
        return "login.jsp";
    }
    /*---------------------------------page-end-----------------------------------*/
    @RequestMapping(value = "/login",params = "json")
    public @ResponseBody Map<String,Object> login_json(HttpSession session, User user){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {

            /*数据检验*/
            if (user.getName() == null || user.getPassword() == null || user.getName().trim().equals("") || user.getPassword().trim().equals("")) {
                result = Config.ERROR;
                message = "存在不能为空的输入";
                throw new ControllerException(message);
            }
            /*数据过滤*/
            String name = user.getName();
            String password = user.getPassword();
            user = new User();
            user.setName(name);
            user.setPassword(password);

            /*权限检验*/
            map.put(Config.user, user);
            List<User> users = userService.select(map);
            map.clear();
            if (users.size() <= 0) {
                result = Config.ERROR;
                message = "用户名或密码有错";
                throw new ControllerException(message);
            }
            /*数据处理*/
            user = users.get(0);
            user.setPassword("");
            /*业务操作*/
            if (user.getSudo() == 0) {
                /*学生登录*/
                session.setAttribute(Config.login_user,user);
                message = "student";
                map.put(Config.MESSAGE, message);
            } else if (user.getSudo() == 1 || user.getSudo() == 2) {
                /*管理员登录*/
                session.setAttribute(Config.login_user, user);
                message = "admin";
                map.put(Config.MESSAGE, message);
            }
            result = Config.SUCCESS;
            map.put(Config.RESULT, result);

        } catch (ControllerException m) {
            map.clear();
            map.put(Config.RESULT, result);
            map.put(Config.MESSAGE, m.getMessage());
        }finally {
            return map;
        }
    }

}

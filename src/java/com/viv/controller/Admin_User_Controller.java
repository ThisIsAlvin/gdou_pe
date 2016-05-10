package com.viv.controller;

import com.viv.Config;
import com.viv.entity.Page;
import com.viv.entity.SortDirectionEnum;
import com.viv.entity.User;
import com.viv.exception.ControllerException;
import com.viv.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
@Controller
@RequestMapping(value = "/admin")
public class Admin_User_Controller {
    UserService userService = new UserService();

    /*------------------超管对管理员的授权操作---------------------------*/
    @RequestMapping(value = "/sudo/add",params = "json")
    public @ResponseBody Map<String,Object> s_add_json(HttpSession session,User user){
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
            if (user.getU_power() != null && (user.getU_power() < 0 || user.getU_power() > 7)) {
                result = Config.ERROR;
                message = "所设置管理员的学生管理权限不在范围内";
                throw new ControllerException(message);
            }
            if (user.getPower() != null && (user.getPower() < 0 || user.getPower() > 7)) {
                result = Config.ERROR;
                message = "所设置管理员的事务管理权限不在范围内";
                throw new ControllerException(message);
            }
            /*数据过滤*/
                /*一个系统只能有一个超管*/
            user.setSudo(1);
            /*权限检验*/
            User admin = (User) session.getAttribute(Config.login_user);
            if (admin.getSudo() != 2) {
                result = Config.ERROR;
                message = "非法的越权操作";
                throw new ControllerException(message);
            }
            /*数据处理*/
            user.setStatus(null);
            /*业务操作*/
            userService.insert(user);
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

    @RequestMapping(value = "/sudo/del",params = "json")
    public @ResponseBody Map<String,Object> s_del_json(HttpSession session,Integer id){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {

            /*数据检验*/
            if (id == null) {
                result = Config.ERROR;
                message = "存在不能为空的输入";
                throw new ControllerException(message);
            }
            /*数据过滤*/
            /*权限检验*/
            User admin = (User) session.getAttribute(Config.login_user);
            if (admin.getSudo() != 2) {
                result = Config.ERROR;
                message = "非法的越权操作";
                throw new ControllerException(message);
            }
            /*数据处理*/
            /*业务操作*/
            userService.delete(id);
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

    @RequestMapping(value = "/sudo/show",params = "json")
    public @ResponseBody Map<String,Object> s_show_json( Integer pageIndex, User user, Timestamp startTime,Timestamp endTiem){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {
            /*数据检验*/

            /*数据过滤*/
                /*user包含查询条件*/
            if (user != null) {
                map.put(Config.user, user);
            }
            if (startTime != null) {
                map.put(Config.like, "1");
                map.put(Config.startTime, startTime);
            }
            if (endTiem != null) {
                map.put(Config.like, "1");
                map.put(Config.endTime, endTiem);
            }
            if (pageIndex != null) {
                /*pageIndex值是否大于零并小于等于最大页数*/
                map.put(Config.count, "1");
                List<User> counts = userService.select(map);
                if (counts.size() > 0) {
                    int count = counts.get(0).getId();
                    int maxIndex = count / Config.PAGE_SIZE;
                    if (pageIndex < 0 || pageIndex > maxIndex) {
                        result = Config.ERROR;
                        message = "请求页面超出范围 ";
                        throw new ControllerException(message);
                    }
                } else {
                    pageIndex = 0;
                }
            } else {
                    pageIndex = 0;
            }
            /*权限检验*/

            /*数据处理*/
            map.remove(Config.count);
            Page page = new Page(SortDirectionEnum.DESC.toString(), "id", pageIndex, Config.PAGE_SIZE);
            map.put(Config.page, page);
            /*业务操作*/
            List<User> users = userService.select(map);
            map.clear();
            result = Config.SUCCESS;
            map.put(Config.RESULT, result);
            map.put(Config.users, users);

        } catch (ControllerException m) {
            map.clear();
            map.put(Config.RESULT, result);
            map.put(Config.MESSAGE, m.getMessage());
        }finally {
            return map;
        }
    }

    @RequestMapping(value = "/sudo/update",params = "json")
    public @ResponseBody Map<String,Object> s_update_json(HttpSession session, User user){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {

            /*数据检验*/
            if (user.getSudo() != null && (user.getSudo() < 0 || user.getSudo() > 1)) {
                result = Config.ERROR;
                message = "所设置身份不在范围内";
                throw new ControllerException(message);
            }
            if (user.getU_power() != null && (user.getU_power() < 0 || user.getU_power() > 7)) {
                result = Config.ERROR;
                message = "所设置管理员的学生管理权限不在范围内";
                throw new ControllerException(message);
            }
            if (user.getPower() != null && (user.getPower() < 0 || user.getPower() > 7)) {
                result = Config.ERROR;
                message = "所设置管理员的事务管理权限不在范围内";
                throw new ControllerException(message);
            }
            if (user.getStatus() != null && (user.getStatus() < 0 || user.getStatus() > 2)) {
                result = Config.ERROR;
                message = "所设置学生状态不在范围内";
                throw new ControllerException(message);
            }
            /*数据过滤*/
            /*权限检验*/
            User admin = (User) session.getAttribute(Config.login_user);
                /*超管不能更改自己的权限！*/
            if (user.getId() == admin.getId()) {
                result = Config.ERROR;
                message = "不能修改自己的权限";
                throw new ControllerException(message);
            }
            /*数据处理*/
            /*业务操作*/
            userService.update(user);
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

    /*------------------管理员对学生用户的操作---------------------------*/

}

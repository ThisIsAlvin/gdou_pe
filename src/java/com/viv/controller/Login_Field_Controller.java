package com.viv.controller;

import com.viv.Config;
import com.viv.entity.Field;
import com.viv.entity.Field_book;
import com.viv.entity.Match;
import com.viv.entity.User;
import com.viv.exception.ControllerException;
import com.viv.service.FieldBookService;
import com.viv.service.FieldService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by viv on 16-5-9.
 */
@Controller
@RequestMapping(value = "/field")
public class Login_Field_Controller {
    FieldService fieldService = new FieldService();
    FieldBookService fieldBookService = new FieldBookService();

//    @RequestMapping(value = "/test/search")
//    public @ResponseBody
//    Map<String ,Object> test(HttpSession session){
//        User user = new User();
//        user.setId(1);
//        session.setAttribute(Config.login_user,user);
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.add(Calendar.DATE, +10);
//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.add(Calendar.DATE,+12);
//        Field_book field_book = new Field_book();
//        field_book.setField_id(4);
//        field_book.setBook_message("12345");
//        field_book.setStart_time(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar1.getTime())));
//        field_book.setEnd_time(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar2.getTime())));
//        return request_json(session, field_book);
//
//    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class,new CustomDateEditor(dateFormat,true));
    }

    @RequestMapping(value = "/request",params = "json")
    public Map<String,Object> request_json(HttpSession session, Field_book field_book){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {

            /*数据检验*/
            if (field_book == null) {
                result = Config.ERROR;
                message = "存在不能为空的输入";
                throw new ControllerException(message);
            }
            if (field_book.getField_id() == null
                    || field_book.getBook_message() == null || field_book.getBook_message().trim().equals("")
                    || field_book.getStart_time() == null || field_book.getEnd_time() == null) {
                result = Config.ERROR;
                message = "存在不能为空的输入";
                throw new ControllerException(message);
            }
            if (field_book.getStart_time().after(field_book.getEnd_time()) || field_book.getStart_time().before(new Date())) {
                result = Config.ERROR;
                message = "时间非法";
                throw new ControllerException(message);
            }
            Field_book fb_check_time = new Field_book();
            fb_check_time.setField_id(field_book.getField_id());
            fb_check_time.setStart_time(field_book.getStart_time());
            fb_check_time.setEnd_time(field_book.getEnd_time());
            map.put(Config.field_book, fb_check_time);
            map.put(Config.count, "1");
            map.put(Config.like, "1");
            map.put(Config.in_time, "1");
            List<Field_book> counts = fieldBookService.select(map);
            map.clear();
            if (counts.get(0).getId() != 0) {
                result = Config.ERROR;
                message = "该时段已存在预约";
                throw new ControllerException(message);
            }
            /*数据过滤*/
            field_book.setId(null);
            field_book.setField(null);
            field_book.setReply(null);
            field_book.setStatus(null);
            /*权限检验*/
            User user = (User) session.getAttribute(Config.login_user);
            /*数据处理*/
            field_book.setBook_by(user.getId());
            /*业务操作*/
            Integer newId =  fieldBookService.insert(field_book);
            result = Config.SUCCESS;
            message = newId.toString();
            map.put(Config.RESULT, result);
            map.put(Config.MESSAGE, message);

        } catch (ControllerException m) {
            map.clear();
            map.put(Config.RESULT, result);
            map.put(Config.MESSAGE, m.getMessage());
        }finally {
            return map;
        }
    }
}

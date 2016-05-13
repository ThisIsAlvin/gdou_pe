package com.viv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by viv on 16-5-9.
 */
@Controller
public class Page_Controller {

//    /*404页*/
//    @RequestMapping(value = "/")
//    public String noFount(){
//        return "404.jsp";
//    }

    /*首页*/
    @RequestMapping(value = "/")
    public String index() {
        return "index.html";
    }

    /*获取公告也页*/
    @RequestMapping(value = "/callBoard")
    public String callBoard(){
        return "callBoard.html";
    }

    /*获取登录页面*/
    @RequestMapping(value = "/visitor/login")
    public String login(){
        return "login.html";
    }

}

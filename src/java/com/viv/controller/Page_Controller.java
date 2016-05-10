package com.viv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by viv on 16-5-9.
 */
@Controller
public class Page_Controller {

    /*404页*/
    @RequestMapping(value = "/**")
    public String noFount(){
        return "404.jsp";
    }

    /*首页*/
    @RequestMapping(value = "/")
    public String index() {
        return "index.jsp";
    }
}

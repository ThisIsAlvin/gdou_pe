package com.viv.controller;

import com.viv.Config;
import com.viv.entity.Page;
import com.viv.entity.SortDirectionEnum;
import com.viv.entity.Tool;
import com.viv.entity.User;
import com.viv.exception.ControllerException;
import com.viv.service.ToolBookService;
import com.viv.service.ToolService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by viv on 16-5-9.
 */
@Controller
@RequestMapping(value = "/tool")
public class Visitor_Tool_Controller {
    ToolService toolService = new ToolService();
    ToolBookService toolBookService = new ToolBookService();

    @RequestMapping(value = "/test/search")
    public @ResponseBody Map<String ,Object> test(){
        return show_json(null,1,0,null);

    }

    /*callType == 0 显示所有  callType == 1 条件检索*/
    @RequestMapping(value = "show",params = "json")
    public @ResponseBody Map<String,Object> show_json(HttpSession session, Integer pageIndex, Integer callType,Tool tool){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {

            /*数据检验*/
            if (pageIndex == null || pageIndex < 0) {
                pageIndex = 0;
            }

            /*数据过滤*/
            if (callType == 1 || tool != null) {
                User admin = (User) session.getAttribute(Config.login_user);
                if (admin == null || admin.getSudo() != 1 || admin.getSudo() != 2) {
                    tool.setIs_bad(0);
                }
            }
            if (tool != null) {
                tool.setImgs(null);
            } else {
                tool = new Tool();
                tool.setIs_bad(0);
            }
            map.put(Config.tool, tool);
            map.put(Config.count, "1");
            List<Tool> counts = toolService.select(map);
            map.remove(Config.count);
            if (counts.size() > 0) {
                int count = counts.get(0).getId();
                int maxIndex = count / Config.PAGE_SIZE;
                if (pageIndex > maxIndex) {
                    pageIndex = maxIndex;
                }
            } else {
                pageIndex = 0;
            }


            /*权限检验*/
            /*数据处理*/
            Page page = new Page(SortDirectionEnum.DESC.toString(), "id", pageIndex, Config.PAGE_SIZE);
            map.put(Config.page, page);
            /*业务操作*/
            List<Tool> tools = toolService.select(map);
            map.clear();
            result = Config.SUCCESS;
            map.put(Config.RESULT, result);
            map.put(Config.tools, tools);

        } catch (Exception m) {
            map.clear();
            map.put(Config.RESULT, result);
            map.put(Config.MESSAGE, m.getMessage());
        }finally {
            return map;
        }
    }

    @RequestMapping(value = "/maxPage",params = "json")
    public @ResponseBody Map<String,Object> maxPage(HttpSession session,Integer callType,Tool tool){
                Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {

            /*数据检验*/
            /*数据过滤*/
            if (callType == 1 || tool != null) {
                User admin = (User) session.getAttribute(Config.login_user);
                if (admin == null || admin.getSudo() != 1 || admin.getSudo() != 2) {
                    tool.setIs_bad(0);
                }
            }
            if (tool != null) {
                tool.setImgs(null);
            } else {
                tool = new Tool();
                tool.setIs_bad(0);
            }
            /*权限检验*/
            /*数据处理*/
            map.put(Config.count, "1");
            /*业务操作*/
            List<Tool> counts = toolService.select(map);
            map.clear();
            int count = counts.get(0).getId();
            int maxPage = count / Config.PAGE_SIZE;
            result = Config.SUCCESS;
            map.put(Config.RESULT, result);
            map.put(Config.maxPage, maxPage);

        } catch (Exception m) {
            map.clear();
            map.put(Config.RESULT, result);
            map.put(Config.MESSAGE, m.getMessage());
        }finally {
            return map;
        }
    }
}

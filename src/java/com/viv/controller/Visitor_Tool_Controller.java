package com.viv.controller;

import com.viv.Config;
import com.viv.entity.*;
import com.viv.exception.ControllerException;
import com.viv.service.ToolBookService;
import com.viv.service.ToolService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
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
@RequestMapping(value = "/tool")
public class Visitor_Tool_Controller {
    ToolService toolService = new ToolService();
    ToolBookService toolBookService = new ToolBookService();

//    @RequestMapping(value = "/test/search")
//    public @ResponseBody Map<String ,Object> test(){
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, +1);
//        return search_json(0,new Date(),calendar.getTime(),1);
//
//    }
    /*查询某时段可租借的器材 callType == 0 返回总页数  callType == 1 返回结果*/
    @RequestMapping(value = "/search",params = "json")
    public @ResponseBody Map<String,Object> search_json(Integer pageIndex,Date startTime,Date endTime,Integer callType){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {

            /*数据检验*/
            if (pageIndex == null || pageIndex < 0) {
                pageIndex = 0;
            }
            if (callType == null || (callType != 0 && callType != 1)) {
                callType = 0;
            }
            if (startTime == null || endTime == null) {
                result = Config.ERROR;
                message = "存在不能为空的输入";
                throw new ControllerException(message);
            }
            if (startTime.before(new Date())) {
                startTime = new Date();
                if (endTime.before(startTime)) {
                    result = Config.ERROR;
                    message = "时间非法";
                    throw new ControllerException(message);
                }
            }
            /*数据过滤*/
            /*权限检验*/
            /*数据处理*/
            Timestamp startStamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime));
            Timestamp endStamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime));
            map.put(Config.like, "1");
            map.put(Config.tool, "1");
            Tool_book tool_book = new Tool_book();
            tool_book.setStart_time(startStamp);
            tool_book.setEnd_time(endStamp);
            map.put(Config.tool_book, tool_book);
            map.put(Config.count, "1");
            List<Tool_book> counts = toolBookService.select(map);
            if (callType == 0) {
                map.clear();
                int count = counts.get(0).getId();
                int maxPage = count / Config.PAGE_SIZE;
                result = Config.SUCCESS;
                map.put(Config.RESULT, result);
                map.put(Config.maxPage, maxPage);
                return map;
            }
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
            Page page = new Page(SortDirectionEnum.DESC.toString(), "id", pageIndex, Config.PAGE_SIZE);
            map.put(Config.page, page);
            /*业务操作*/
            List<Tool_book> tool_books = toolBookService.select(map);
            map.clear();

            /*数据处理*/
            Iterator<Tool_book> tool_bookIterator = tool_books.iterator();
            List<Tool> tools = new ArrayList<>();
            while (tool_bookIterator.hasNext()) {
                tools.add(tool_bookIterator.next().getTool());
            }
            result = Config.SUCCESS;
            map.put(Config.RESULT, result);
            map.put(Config.fields, tools);

        } catch (Exception m) {
            map.clear();
            map.put(Config.RESULT, result);
            map.put(Config.MESSAGE, m.getMessage());
        }finally {
            return map;
        }
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

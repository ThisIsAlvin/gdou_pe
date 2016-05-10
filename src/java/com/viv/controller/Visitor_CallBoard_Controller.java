package com.viv.controller;

import com.viv.Config;
import com.viv.entity.CallBoard;
import com.viv.entity.Page;
import com.viv.entity.SortDirectionEnum;
import com.viv.exception.ControllerException;
import com.viv.service.CallBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by viv on 16-5-9.
 */
@Controller
@RequestMapping(value = "/callBoard")
public class Visitor_CallBoard_Controller {
    private CallBoardService callBoardService = new CallBoardService();

    @RequestMapping(value = "/show",params = "json")
    public @ResponseBody Map<String,Object> show_json(Integer pageIndex,Integer type){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {
            CallBoard callBoard;
            /*数据检验*/
                /*非空检测*/
            if (pageIndex == null || type == null) {
                result = Config.ERROR;
                message = "存在不能为空的输入";
                throw new ControllerException(message);
            }
                /*type处理*/
            if (type < 0 || type > 3) {
                type = 0;
            }
                /*pageIndex值是否大于零并小于等于最大页数*/
            callBoard = new CallBoard();
            callBoard.setType(type);
            map.put(Config.callBoard, callBoard);
            map.put(Config.count, "1");
            List<CallBoard> counts = callBoardService.select(map);
            map.clear();
            int count = counts.get(0).getId();
            int maxIndex = count / Config.PAGE_SIZE;
            if (pageIndex < 0 || pageIndex > maxIndex) {
                result = Config.ERROR;
                message = "请求页面超出范围";
                throw new ControllerException(message);
            }
            /*数据过滤*/
            /*权限检验*/
            /*数据处理*/
            Page page = new Page(SortDirectionEnum.DESC.toString(), "id", pageIndex, Config.PAGE_SIZE);
            map.put(Config.callBoard, callBoard);
            map.put(Config.page, page);
            /*业务操作*/
            List<CallBoard> callBoards = callBoardService.select(map);
            map.clear();
            result = Config.SUCCESS;
            map.put(Config.RESULT, result);
            map.put(Config.callBoards, callBoards);

        } catch (ControllerException m) {
            map.clear();
            map.put(Config.RESULT, result);
            map.put(Config.MESSAGE, m.getMessage());
        }finally {
            return map;
        }

    }

    @RequestMapping(value = "/maxPage",params = "json")
    public @ResponseBody Map<String,Object> maxPage(Integer type){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {
            CallBoard callBoard;
            /*数据检验*/
            if (type == null) {
                result = Config.ERROR;
                message = "存在不能为空的输入";
                throw new ControllerException(message);
            }
            /*数据过滤*/
            if (type < 0 || type > 3) {
                type = 0;
            }
            /*权限检验*/
            /*数据处理*/
            callBoard = new CallBoard();
            callBoard.setType(type);
            map.put(Config.callBoard, callBoard);
            map.put(Config.count, "1");
            /*业务操作*/
            List<CallBoard> counts = callBoardService.select(map);
            map.clear();
            int count = counts.get(0).getId();
            int maxPage = count / Config.PAGE_SIZE;
            result = Config.SUCCESS;
            map.put(Config.RESULT, result);
            map.put(Config.maxPage, maxPage);

        } catch (ControllerException m) {
            map.clear();
            map.put(Config.RESULT, result);
            map.put(Config.MESSAGE, m.getMessage());
        }finally {
            return map;
        }
    }

}

package com.viv.controller;

import com.viv.Config;
import com.viv.entity.CallBoard;
import com.viv.service.CallBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-3-28.
 * 该类在新建工程的时候用于测试工程是否建立成功
 * http://localhost:8080/1.do
 */
@Controller
public class HelloController {
    Date date = new Date();
    String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    Timestamp ts_date = Timestamp.valueOf(nowTime);
    CallBoardService callBoardService = new CallBoardService();

    @RequestMapping(value = "/test/callBoard/insert")
    public @ResponseBody String index1() {
        CallBoard callBoard = new CallBoard();
        callBoard.setMessage("test");
        callBoard.setType(0);
        callBoard.setRecent_time(ts_date);
        Integer id = callBoardService.insert(callBoard);
        return id.toString();
        }

    @RequestMapping(value = "/test/callBoard/delete")
    public @ResponseBody String index2() {
        callBoardService.delete(new Integer(2));
        return "delete";
    }

    @RequestMapping(value = "/test/callBoard/update")
    public @ResponseBody String index3(){
        CallBoard callBoard = new CallBoard();
        callBoard.setId(1);
        callBoard.setMessage("updateTest");
        callBoardService.update(callBoard);
        return "update";
    }

    @RequestMapping(value = "/test/callBoard/select")
    public @ResponseBody List<CallBoard> index4(){
        Map map = new HashMap();
        CallBoard callBoard = new CallBoard();
        callBoard.setMessage("t");
        map.put(Config.callBoard, callBoard);
        map.put(Config.like, "1");
        List<CallBoard> callBoards = callBoardService.select(map);
        return callBoards;
    }

}

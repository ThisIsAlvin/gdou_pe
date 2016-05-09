package com.viv.controller;

import com.viv.entity.CallBoard;
import com.viv.service.CallBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by viv on 16-3-28.
 * 该类在新建工程的时候用于测试工程是否建立成功
 * http://localhost:8080/1.do
 */
@Controller
public class HelloController {
    private Date date = new Date();
    private String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    private Timestamp ts_date = Timestamp.valueOf(nowTime);
//    private CallBoardService callBoardService = new CallBoardService();


/*    @RequestMapping(value = "/test/callBoard/insert")
    public String index1() {
        CallBoard callBoard = new CallBoard();
        callBoard.setMessage("test");
        callBoard.setRecent_time(ts_date);
        Integer id = callBoardService.insert(callBoard);
        return id.toString();
}*/
   @RequestMapping(value = "/test/callBoard/insert")
    public @ResponseBody String index1() {

    return "ok";
}
}

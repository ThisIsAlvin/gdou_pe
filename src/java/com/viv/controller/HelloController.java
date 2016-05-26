package com.viv.controller;

import com.viv.Config;
import com.viv.entity.*;
import com.viv.exception.ControllerException;
import com.viv.service.CallBoardService;
import com.viv.service.FieldBookService;
import com.viv.service.FieldService;
import com.viv.service.UserService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

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
    UserService userService = new UserService();
    FieldService fieldService = new FieldService();
    FieldBookService fieldBookService = new FieldBookService();

    @RequestMapping(value = "/test/callBoard/insert")
    public @ResponseBody String index1() {
        CallBoard callBoard = new CallBoard();
        callBoard.setMessage("testtest");
        Integer id = callBoardService.insert(callBoard);
        return id.toString();
        }

    @RequestMapping(value = "/test/callBoard/delete")
    public @ResponseBody String index2() {

        /*规范模板*/
//        Map<String, Object> map = new HashMap<>();
//        String result = Config.ERROR;
//        String message = Config.ERROR;
//        try {
//
//            /*数据检验*/
//            /*数据过滤*/
//            /*权限检验*/
//            /*数据处理*/
//            /*业务操作*/
//
//        } catch (ControllerException m) {
//            map.clear();
//            map.put(Config.RESULT, result);
//            map.put(Config.MESSAGE, m.getMessage());
//        }finally {
//            return map;
//        }


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
        List<CallBoard> callBoards = callBoardService.select(map);
        return callBoards;
    }

    @RequestMapping(value = "/test/user/insert")
    public @ResponseBody String index5(){
        User user = new User();
        user.setName("student1");
        user.setPassword("test");
        user.setSudo(2);
        return (userService.insert(user)).toString();
    }

    @RequestMapping(value = "/test/user/update")
    public @ResponseBody String index6(){
        User user = new User();
        user.setId(5);
        user.setPassword("new_password");
        return (userService.update(user)).toString();
    }

    @RequestMapping(value = "/test/user/select")
    public @ResponseBody List<User> index7(){
        Map map = new HashMap();
        User user = new User();
        return userService.select(map);
    }

    @RequestMapping(value = "/test/user/delete")
    public @ResponseBody String index8(){
        userService.delete(5);
        return "delete";
    }

    @RequestMapping(value = "/test/upload")
    public String index9(){
        return "uploadTest.html";
    }

    @RequestMapping(value = "/test/upload", params = "callBoard")
    public @ResponseBody String index10(HttpServletRequest request, HttpServletResponse response,String title,String message){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        List<String> fileNames = new ArrayList<>();
        int i = 0;
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    String myFileName = file.getOriginalFilename();
                    if (myFileName.trim() != "") {
                        System.out.println(myFileName);
                        String fileName  = "callBoard-"+i+"-"+ ts_date + ".jpg";
                        String path = request.getSession().getServletContext().getRealPath("source/upload/img");
                        String fileUrl = path+"/"+fileName;
                        File localFile = new File(fileUrl);
                        try {
                            file.transferTo(localFile);
                            fileNames.add(fileName);
                            i++;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        CallBoard callBoard = new CallBoard();
        callBoard.setTitle(title);
        callBoard.setMessage(message);
        callBoard.setImgs(fileNames);

        Integer id = callBoardService.insert(callBoard);
        return "success";
    }

    @RequestMapping(value = "/test/upload", params = "field")
    public @ResponseBody String index11(HttpServletRequest request, HttpServletResponse response,String title,String message){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        List<String> fileNames = new ArrayList<>();
        int i = 0;
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    String myFileName = file.getOriginalFilename();
                    if (myFileName.trim() != "") {
                        System.out.println(myFileName);
                        String fileName  = "field-"+i+"-"+ ts_date + ".jpg";
                        String path = request.getSession().getServletContext().getRealPath("source/upload/img");
                        String fileUrl = path+"/"+fileName;
                        File localFile = new File(fileUrl);
                        try {
                            file.transferTo(localFile);
                            fileNames.add(fileName);
                            i++;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        Field field = new Field();
        field.setTitle(title);
        field.setMessage(message);
        field.setImgs(fileNames);

        Integer id = fieldService.insert(field);
        return "success";
    }

    @RequestMapping(value = "/test/fieldBook/show")
    public @ResponseBody List<Field_book> index12(){
        Map map = new HashMap();
        Field_book field_book = new Field_book();
        map.put(Config.field, "1");
        map.put(Config.field_book, field_book);
        map.put(Config.like, "1");
        Page page = new Page(SortDirectionEnum.DESC.toString(), "id", 0, Config.PAGE_SIZE);
        map.put(Config.page, page);
        List<Field_book> field_books = fieldBookService.select(map);
        return field_books;
    }

}

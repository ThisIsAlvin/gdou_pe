package com.viv.controller;

import com.sun.org.apache.xml.internal.security.Init;
import com.viv.Config;
import com.viv.entity.Field;
import com.viv.entity.Field_book;
import com.viv.entity.Page;
import com.viv.entity.SortDirectionEnum;
import com.viv.exception.ControllerException;
import com.viv.service.FieldBookService;
import com.viv.service.FieldService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by viv on 16-5-9.
 */

@Controller
@RequestMapping(value = "/field")
public class Visitor_Field_Controller {
    private FieldService fieldService = new FieldService();
    private FieldBookService fieldBookService = new FieldBookService();

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class,new CustomDateEditor(dateFormat,true));
    }

    @RequestMapping(value = "/test/search")
    public @ResponseBody Map<String ,Object> test(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);
        return searchMaxPage_json(0, new Date(), calendar.getTime());

    }

    @RequestMapping(value = "/search",params = "json")
    public @ResponseBody Map<String ,Object> search_json(Integer pageIndex,Integer callType,Date startTime,Date endTime,Integer fieldId){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {

            /*数据检验*/
            if (pageIndex == null || pageIndex < 0) {
                pageIndex = 0;
            }
            if (callType == null) {
                result = Config.ERROR;
                message = "存在不能为空的输入";
                throw new ControllerException(message);
            }
            if (callType == 0 && startTime != null && endTime != null) {
                if (startTime.before(new Date())) {
                    startTime = new Date();
                    if (endTime.before(startTime)) {
                        result = Config.ERROR;
                        message = "时间非法";
                        throw new ControllerException(message);
                    }
                }
                Timestamp startStamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime));
                Timestamp endStamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime));
                map.put(Config.like, "1");
                map.put(Config.field, "1");
                Field_book field_book = new Field_book();
                field_book.setStart_time(startStamp);
                field_book.setEnd_time(endStamp);
                map.put(Config.field_book, field_book);
                map.put(Config.count, "1");
                List<Field_book> counts = fieldBookService.select(map);
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

            } /*else if (callType == 1 && fieldId != null) {


            }*/ else {
                result = Config.ERROR;
                message = "存在不能为空的输入";
                throw new ControllerException(message);
            }
            /*数据过滤*/
            /*权限检验*/
            /*数据处理*/
            Page page = new Page(SortDirectionEnum.DESC.toString(), "id", pageIndex, Config.PAGE_SIZE);
            map.put(Config.page, page);
            /*业务操作*/
            List<Field_book> field_books = fieldBookService.select(map);
            map.clear();

            /*数据处理*/
            Iterator<Field_book> field_bookIterator = field_books.iterator();
            List<Field> fields = new ArrayList<>();
            while (field_bookIterator.hasNext()) {
                fields.add(field_bookIterator.next().getField());
            }
            result = Config.SUCCESS;
            map.put(Config.RESULT, result);
            map.put(Config.fields, fields);

        } catch (ControllerException m) {
            map.clear();
            map.put(Config.RESULT, result);
            map.put(Config.MESSAGE, m.getMessage());
        }finally {
            return map;
        }
    }

    @RequestMapping(value = "/searchMaxPage",params = "json")
    public @ResponseBody Map<String,Object> searchMaxPage_json(Integer callType,Date startTime,Date endTime){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {

            /*数据检验*/
            if (callType == null) {
                result = Config.ERROR;
                message = "存在不能为空的输入";
                throw new ControllerException(message);
            }
            if (callType == 0 && startTime != null && endTime != null) {
                if (startTime.before(new Date())) {
                    startTime = new Date();
                    if (endTime.before(startTime)) {
                        result = Config.ERROR;
                        message = "时间非法";
                        throw new ControllerException(message);
                    }
                }
                Timestamp startStamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime));
                Timestamp endStamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime));
                map.put(Config.like, "1");
                map.put(Config.field, "1");
                Field_book field_book = new Field_book();
                field_book.setStart_time(startStamp);
                field_book.setEnd_time(endStamp);
                map.put(Config.field_book, field_book);
                map.put(Config.count, "1");
                List<Field_book> counts = fieldBookService.select(map);
                map.clear();
                int count = counts.get(0).getId();
                int maxPage = count / Config.PAGE_SIZE;
                result = Config.SUCCESS;
                map.put(Config.RESULT, result);
                map.put(Config.maxPage, maxPage);


            } /*else if (callType == 1 && fieldId != null) {


            }*/ else {
                result = Config.ERROR;
                message = "存在不能为空的输入";
                throw new ControllerException(message);
            }
            /*数据过滤*/
            /*权限检验*/
            /*数据处理*/
            /*业务操作*/

        } catch (ControllerException m) {
            map.clear();
            map.put(Config.RESULT, result);
            map.put(Config.MESSAGE, m.getMessage());
        }finally {
            return map;
        }
    }

    @RequestMapping(value = "/show",params = "json")
    public @ResponseBody Map<String,Object> show_json(Integer pageIndex){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {

            /*数据检验*/
                /*非空检测*/
            if (pageIndex == null || pageIndex < 0) {
                pageIndex = 0;
            }
                /*pageIndex是否小于等于最大页数*/
            map.put(Config.count, "1");
            List<Field> counts = fieldService.select(map);
            map.clear();
            if (counts.size() > 0) {
                int count = counts.get(0).getId();
                int maxIndex = count / Config.PAGE_SIZE;
                if (pageIndex > maxIndex) {
                    pageIndex = maxIndex;
                }
            } else {
                pageIndex = 0;
            }
            /*数据过滤*/
            /*权限检验*/
            /*数据处理*/
            Page page = new Page(SortDirectionEnum.DESC.toString(), "id", pageIndex, Config.PAGE_SIZE);
            map.put(Config.page, page);
            /*业务操作*/
            List<Field> fields = fieldService.select(map);
            map.clear();
            result = Config.SUCCESS;
            map.put(Config.RESULT, result);
            map.put(Config.fields, fields);

        } catch (Exception m) {
            map.clear();
            map.put(Config.RESULT, result);
            map.put(Config.MESSAGE, m.getMessage());
        }finally {
            return map;
        }
    }

    @RequestMapping(value = "/maxPage",params = "json")
    public @ResponseBody Map<String,Object> maxPage(){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {

            /*数据检验*/
            /*数据过滤*/
            /*权限检验*/
            /*数据处理*/
            map.put(Config.count, "1");
            /*业务操作*/
            List<Field> counts = fieldService.select(map);
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

package com.viv.controller;

import com.viv.Config;
import com.viv.entity.Field;
import com.viv.entity.Page;
import com.viv.entity.SortDirectionEnum;
import com.viv.exception.ControllerException;
import com.viv.service.FieldService;
import org.codehaus.jackson.map.ObjectMapper;
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
@RequestMapping(value = "/field")
public class Visitor_Field_Controller {
    private FieldService fieldService = new FieldService();

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

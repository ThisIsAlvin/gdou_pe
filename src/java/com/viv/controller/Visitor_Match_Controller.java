package com.viv.controller;

import com.viv.Config;
import com.viv.entity.*;
import com.viv.exception.ControllerException;
import com.viv.service.MatchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by viv on 16-5-9.
 */
@Controller
@RequestMapping(value = "/match")
public class Visitor_Match_Controller {
    MatchService matchService = new MatchService();

    @RequestMapping(value = "/test/search")
    public @ResponseBody Map<String ,Object> test(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);
        Match match = new Match();
        match.setStart_time(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
        match.setEnd_time(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime())));
        return search_json(0,1,match);

    }

    /*callType == 0 返回页数 callType == 1 返回结果*/
    @RequestMapping(value = "/search",params = "json")
    public @ResponseBody Map<String,Object> search_json(Integer pageIndex, Integer callType, Match match){
        Map<String, Object> map = new HashMap<>();
        String result = Config.ERROR;
        String message = Config.ERROR;
        try {

            /*数据检验*/
            if (pageIndex == null || pageIndex < 0) {
                pageIndex = 0;
            }
            /*数据过滤*/
            if (callType == null || (callType != 0 && callType != 1)) {
                callType = 0;
            }
            if (match.getEnd_time() == null && match.getStart_time() != null) {
                match.setStart_time(null);
            }
            if (match.getStart_time() == null && match.getEnd_time() != null) {
                match.setStart_time(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
            }
            if (match.getStart_time() != null && match.getEnd_time() != null) {
                if (match.getStart_time().before(new Date())) {
                    match.setStart_time(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
                    if (match.getEnd_time().before(match.getStart_time())) {
                        result = Config.ERROR;
                        message = "时间非法";
                        throw new ControllerException(message);
                    }
                }
            }
            match.setBook_by(null);
            match.setBook_message(null);
            match.setReply(null);
            match.setStatus(2);
            /*权限检验*/
            /*数据处理*/
            map.put(Config.like, "1");
            map.put(Config.count, "1");
            map.put(Config.match, match);
            map.put(Config.in_time, "1");
            List<Match> counts = matchService.select(map);
            if (callType == 0) {
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
            List<Match> matches = matchService.select(map);
            map.clear();
            /*数据处理*/
            Iterator<Match> matchIterator = matches.iterator();
            Match m = null;
            while (matchIterator.hasNext()) {
                m = matchIterator.next();
                m.setBook_message(null);
                m.setBook_by(null);
                m.setReply(null);
            }
            result = Config.SUCCESS;
            map.put(Config.RESULT, result);
            map.put(Config.matchs, matches);

        } catch (ControllerException m) {
            map.clear();
            map.put(Config.RESULT, result);
            map.put(Config.MESSAGE, m.getMessage());
        }finally {
            return map;
        }
    }
}

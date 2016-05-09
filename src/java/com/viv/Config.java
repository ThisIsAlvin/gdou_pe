package com.viv;

/**
 * Created by viv on 16-4-29.
 */
public class Config {
    /*分页时，每一页的记录条数*/
    public static final int PAGE_SIZE = 2;
    /*返回result*/
    public static final String RESULT = "result";
    /*返回message*/
    public static final String MESSAGE = "message";

    /*错误*/
    public static final String ERROR = "error";
    /*成功*/
    public static final String SUCCESS = "success";


    /*-------------------动态sql的Map所使用的常量--------------------*/
    /*分页查询标志*/
    public static final String page = "page";
    /*模糊查询开启标志*/
    public static final String like = "like";
    /*时间范围查询标志*/
    public static final String startTime = "startTime";
    public static final String endTime = "endTime";
    /*各各实体的常量值*/
    public static final String callBoard = "callBoard";

    /*--------------------读取配置文件---------------------------*/
    public static final String resource = "conf.xml";

}

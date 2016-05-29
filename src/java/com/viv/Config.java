package com.viv;

/**
 * Created by viv on 16-4-29.
 */
public class Config {
    /*分页时，每一页的记录条数*/
    public static final int PAGE_SIZE = 2;

    /*-------------------动态sql的Map所使用的常量--------------------*/
    /*查询的时候如果只是需要统计数据量，传入参数count，返回的统计数量存在对象的id中*/
    public static final String count = "count";

    /*分页查询标志*/
    public static final String page = "page";
    /*模糊查询开启标志*/
    public static final String like = "like";
    /*时间范围查询标志*/
    public static final String startTime = "startTime";
    public static final String endTime = "endTime";
    /*标记查询时间范围内还是外 不为空标示范围内*/
    public static final String in_time = "in_time";
    /*各各实体的常量值*/
    public static final String callBoard = "callBoard";
    public static final String user = "user";
    public static final String field = "field";
    public static final String field_book = "field_book";
    public static final String tool = "tool";
    public static final String tool_book = "tool_book";
    public static final String match = "match";

    /*--------------------页面返回常用标示---------------------------*/
    /*返回result*/
    public static final String RESULT = "result";

    /*返回message*/
    public static final String MESSAGE = "message";
    /*错误*/
    public static final String ERROR = "error";

    /*成功*/
    public static final String SUCCESS = "success";

    /*最大页数*/
    public static final String maxPage = "maxPage";
    /*各种对象实体集合*/
    public static final String callBoards = "callBoards";
    public static final String users = "users";
    public static final String fields = "fields";
    public static final String tools = "tools";
    public static final String matchs = "matchs";


    /*--------------------session保存的标示---------------------------*/
    public static final String login_user = "login_user";

    /*--------------------读取配置文件---------------------------*/
    public static final String resource = "conf.xml";



}

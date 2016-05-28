package com.viv.entity;

import java.sql.Timestamp;

/**
 * Created by viv on 16-5-23.
 */
public class Tool_book {
    private Integer id;
    private Integer tool_id;
    private Tool tool;
    private String book_message;
    private Integer book_by;
    private Timestamp start_time;
    private Timestamp end_time;
    private String reply;
    private Integer status;
    private Integer reback;
    private Timestamp recent_time;

    public Integer getBook_by() {
        return book_by;
    }

    public void setBook_by(Integer book_by) {
        this.book_by = book_by;
    }

    public String getBook_message() {
        return book_message;
    }

    public void setBook_message(String book_message) {
        this.book_message = book_message;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReback() {
        return reback;
    }

    public void setReback(Integer reback) {
        this.reback = reback;
    }

    public Timestamp getRecent_time() {
        return recent_time;
    }

    public void setRecent_time(Timestamp recent_time) {
        this.recent_time = recent_time;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTool_id() {
        return tool_id;
    }

    public void setTool_id(Integer tool_id) {
        this.tool_id = tool_id;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }
}

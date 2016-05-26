package com.viv.entity;

import java.sql.Timestamp;

/**
 * Created by viv on 16-5-23.
 */
public class Field_book {
    private Integer id;
    private Integer field_id;
    private Field field;
    private String book_message;
    private Integer book_by;
    private Timestamp start_time;
    private Timestamp end_time;
    private String reply;
    private Integer status;
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

    public Integer getField_id() {
        return field_id;
    }

    public void setField_id(Integer field_id) {
        this.field_id = field_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}

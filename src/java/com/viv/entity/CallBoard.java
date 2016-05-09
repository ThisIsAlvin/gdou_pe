package com.viv.entity;

import java.sql.Timestamp;

/**
 * Created by viv on 16-5-9.
 */
public class CallBoard {
    private Integer id;
    private String message;
    private Integer type;
    private Timestamp recent_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getRecent_time() {
        return recent_time;
    }

    public void setRecent_time(Timestamp recent_time) {
        this.recent_time = recent_time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

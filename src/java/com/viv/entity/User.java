package com.viv.entity;

import java.sql.Timestamp;

/**
 * Created by viv on 16-5-10.
 */
public class User {
    private Integer id;
    private String name;
    private String password;
    private Integer sudo;
    private Integer u_power;
    private Integer power;
    private Integer status;
    private Timestamp recent_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Timestamp getRecent_time() {
        return recent_time;
    }

    public void setRecent_time(Timestamp recent_time) {
        this.recent_time = recent_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSudo() {
        return sudo;
    }

    public void setSudo(Integer sudo) {
        this.sudo = sudo;
    }

    public Integer getU_power() {
        return u_power;
    }

    public void setU_power(Integer u_power) {
        this.u_power = u_power;
    }
}

package com.viv.entity;

import java.util.List;

/**
 * Created by viv on 16-5-23.
 */
public class Tool {
    private Integer id;
    private String title;
    private String message;
    private String img;
    private List<String> imgs;
    private Integer is_bad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getIs_bad() {
        return is_bad;
    }

    public void setIs_bad(Integer is_bad) {
        this.is_bad = is_bad;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}

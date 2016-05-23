package com.viv.dao;

import com.viv.entity.Tool;

import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public interface ToolOperation {
    public Integer insert(Tool tool);

    public void delete(Integer id);

    public int update(Tool tool);

    public List<Tool> select(Map map);
}

package com.viv.dao;

import com.viv.entity.CallBoard;
import com.viv.entity.Tool_book;

import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public interface ToolBookOperation {
    public Integer insert(Tool_book tool_book);

    public void delete(Integer id);

    public int update(Tool_book tool_book);

    public List<Tool_book> select(Map map);
}

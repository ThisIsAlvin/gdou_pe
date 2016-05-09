package com.viv.dao;

import com.viv.entity.CallBoard;

import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public interface CallBoardOperation {
    public Integer insert(CallBoard callBoard);

    public void delete(Integer id);

    public int update(CallBoard callBoard);

    public List<CallBoard> select(Map map);
}

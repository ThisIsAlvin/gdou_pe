package com.viv.dao;

import com.viv.entity.CallBoard;
import com.viv.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public interface UserOperation {
    public Integer insert(User user);

    public void delete(Integer id);

    public int update(User user);

    public List<User> select(Map map);
}

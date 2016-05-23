package com.viv.dao;

import com.viv.entity.CallBoard;
import com.viv.entity.Field;

import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public interface FieldOperation {
    public Integer insert(Field field);

    public void delete(Integer id);

    public int update(Field field);

    public List<Field> select(Map map);
}

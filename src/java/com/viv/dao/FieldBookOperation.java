package com.viv.dao;

import com.viv.entity.CallBoard;
import com.viv.entity.Field_book;

import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public interface FieldBookOperation {
    public Integer insert(Field_book field_book);

    public void delete(Integer id);

    public int update(Field_book field_book);

    public List<Field_book> select(Map map);
}

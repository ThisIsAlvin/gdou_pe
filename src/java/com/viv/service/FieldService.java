package com.viv.service;

import com.viv.Config;
import com.viv.dao.CallBoardOperation;
import com.viv.dao.FieldOperation;
import com.viv.entity.CallBoard;
import com.viv.entity.Field;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public class FieldService {

    public Integer insert(Field field) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            FieldOperation fieldOperation = session.getMapper(FieldOperation.class);
            fieldOperation.insert(field);
            session.commit();
            return field.getId();
        }finally {
            session.close();
        }
    }

    public void delete(Integer id) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            FieldOperation fieldOperation = session.getMapper(FieldOperation.class);
            fieldOperation.delete(id);
            session.commit();
        }finally {
            session.close();
        }
    }

    public List<Field> select(Map map) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        if (map.containsKey(Config.like) && map.containsKey(Config.field)) {
            Field field = (Field) map.get(Config.field);
            if (field.getTitle() != null) {
                field.setTitle("%" + field.getTitle() + "%");
            }
            if (field.getMessage() != null) {
                field.setMessage("%" + field.getMessage() + "%");
            }
        }

        try {
            FieldOperation fieldOperation = session.getMapper(FieldOperation.class);
            List<Field> fields = fieldOperation.select(map);
            session.commit();
            return fields;
        }finally {
            session.close();
        }
    }

    public int update(Field field) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            FieldOperation fieldOperation = session.getMapper(FieldOperation.class);
            int count = fieldOperation.update(field);
            session.commit();
            return count;
        }finally {
            session.close();
        }
    }



}

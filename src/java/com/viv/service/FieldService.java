package com.viv.service;

import com.viv.Config;
import com.viv.dao.CallBoardOperation;
import com.viv.dao.FieldOperation;
import com.viv.entity.CallBoard;
import com.viv.entity.Field;
import org.apache.ibatis.session.SqlSession;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public class FieldService {

    public Integer insert(Field field) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            if (field.getImgs() != null) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    field.setImg(mapper.writeValueAsString(field.getImgs()));
                    field.setImgs(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

            if (!map.containsKey(Config.count)) {
                Iterator<Field> fieldIterator = fields.iterator();
                ObjectMapper mapper = new ObjectMapper();
                while (fieldIterator.hasNext()) {
                    Field f = fieldIterator.next();
                    List imgs = null;
                    try {
                        imgs = mapper.readValue(f.getImg(), List.class);
                        f.setImgs(imgs);
                        f.setImg(null);
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

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

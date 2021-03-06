package com.viv.service;

import com.viv.Config;
import com.viv.dao.CallBoardOperation;
import com.viv.dao.FieldBookOperation;
import com.viv.entity.CallBoard;
import com.viv.entity.Field_book;
import org.apache.ibatis.session.SqlSession;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public class FieldBookService {

    public Integer insert(Field_book field_book) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            FieldBookOperation fieldBookOperation = session.getMapper(FieldBookOperation.class);
            fieldBookOperation.insert(field_book);
            session.commit();
            return field_book.getId();
        }finally {
            session.close();
        }
    }

    public void delete(Integer id) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            FieldBookOperation fieldBookOperation = session.getMapper(FieldBookOperation.class);
            fieldBookOperation.delete(id);
            session.commit();
        }finally {
            session.close();
        }
    }

    public List<Field_book> select(Map map) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        if (map.containsKey(Config.like) && map.containsKey(Config.field_book)) {
            Field_book field_book = (Field_book) map.get(Config.field_book);
            if (field_book.getBook_message() != null) {
                field_book.setBook_message("%" + field_book.getBook_message() + "%");
            }
            if (field_book.getReply() != null) {
                field_book.setReply("%" + field_book.getReply() + "%");
            }
        }

        try {
            FieldBookOperation fieldBookOperation = session.getMapper(FieldBookOperation.class);
            List<Field_book> field_books = fieldBookOperation.select(map);
            session.commit();

            if (!map.containsKey(Config.count) && map.containsKey(Config.field)) {
                Iterator<Field_book> field_bookIterator = field_books.iterator();
                ObjectMapper mapper = new ObjectMapper();
                while (field_bookIterator.hasNext()) {
                    Field_book field_book =  field_bookIterator.next();
                    try {
                        List imgs = mapper.readValue(field_book.getField().getImg(), List.class);
                        field_book.getField().setImgs(imgs);
                        field_book.getField().setImg(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return field_books;
        }finally {
            session.close();
        }
    }

    public int update(Field_book field_book) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            FieldBookOperation fieldBookOperation = session.getMapper(FieldBookOperation.class);
            int count = fieldBookOperation.update(field_book);
            session.commit();
            return count;
        }finally {
            session.close();
        }
    }



}

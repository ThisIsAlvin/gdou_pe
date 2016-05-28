package com.viv.service;

import com.viv.Config;
import com.viv.dao.ToolBookOperation;
import com.viv.entity.Field_book;
import com.viv.entity.Tool_book;
import org.apache.ibatis.session.SqlSession;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public class ToolBookService {

    public Integer insert(Tool_book tool_book) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            ToolBookOperation toolBookOperation = session.getMapper(ToolBookOperation.class);
            toolBookOperation.insert(tool_book);
            session.commit();
            return tool_book.getId();
        }finally {
            session.close();
        }
    }

    public void delete(Integer id) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            ToolBookOperation toolBookOperation = session.getMapper(ToolBookOperation.class);
            toolBookOperation.delete(id);
            session.commit();
        }finally {
            session.close();
        }
    }

    public List<Tool_book> select(Map map) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        if (map.containsKey(Config.like) && map.containsKey(Config.tool_book)) {
            Tool_book tool_book = (Tool_book) map.get(Config.tool_book);
            if (tool_book.getBook_message() != null) {
                tool_book.setBook_message("%" + tool_book.getBook_message() + "%");
            }
            if (tool_book.getReply() != null) {
                tool_book.setReply("%" + tool_book.getReply() + "%");
            }
        }

        try {
            ToolBookOperation toolBookOperation = session.getMapper(ToolBookOperation.class);
            List<Tool_book> tool_books = toolBookOperation.select(map);
            session.commit();

            if (!map.containsKey(Config.count) && map.containsKey(Config.tool)) {
                Iterator<Tool_book> tool_bookIterator = tool_books.iterator();
                ObjectMapper mapper = new ObjectMapper();
                while (tool_bookIterator.hasNext()) {
                    Tool_book tool_book =  tool_bookIterator.next();
                    try {
                        List imgs = mapper.readValue(tool_book.getTool().getImg(), List.class);
                        tool_book.getTool().setImgs(imgs);
                        tool_book.getTool().setImg(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return tool_books;
        }finally {
            session.close();
        }
    }

    public int update(Tool_book tool_book) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            ToolBookOperation toolBookOperation = session.getMapper(ToolBookOperation.class);
            int count = toolBookOperation.update(tool_book);
            session.commit();
            return count;
        }finally {
            session.close();
        }
    }



}

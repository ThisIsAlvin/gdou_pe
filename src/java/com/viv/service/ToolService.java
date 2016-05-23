package com.viv.service;

import com.viv.Config;
import com.viv.dao.ToolOperation;
import com.viv.entity.Tool;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public class ToolService {

    public Integer insert(Tool tool) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            ToolOperation toolOperation = session.getMapper(ToolOperation.class);
            toolOperation.insert(tool);
            session.commit();
            return tool.getId();
        }finally {
            session.close();
        }
    }

    public void delete(Integer id) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            ToolOperation toolOperation = session.getMapper(ToolOperation.class);
            toolOperation.delete(id);
            session.commit();
        }finally {
            session.close();
        }
    }

    public List<Tool> select(Map map) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        if (map.containsKey(Config.like) && map.containsKey(Config.tool)) {
            Tool tool = (Tool) map.get(Config.tool);
            if (tool.getTitle() != null) {
                tool.setTitle("%" + tool.getTitle() + "%");
            }
            if (tool.getMessage() != null) {
                tool.setMessage("%" + tool.getMessage() + "%");
            }
        }

        try {
            ToolOperation toolOperation = session.getMapper(ToolOperation.class);
            List<Tool> tools = toolOperation.select(map);
            session.commit();
            return tools;
        }finally {
            session.close();
        }
    }

    public int update(Tool tool) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            ToolOperation toolOperation = session.getMapper(ToolOperation.class);
            int count = toolOperation.update(tool);
            session.commit();
            return count;
        }finally {
            session.close();
        }
    }



}

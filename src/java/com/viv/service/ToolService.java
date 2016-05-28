package com.viv.service;

import com.viv.Config;
import com.viv.dao.ToolOperation;
import com.viv.entity.Field;
import com.viv.entity.Tool;
import org.apache.ibatis.session.SqlSession;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public class ToolService {

    public Integer insert(Tool tool) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            if (tool.getImgs() != null) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    tool.setImg(mapper.writeValueAsString(tool.getImgs()));
                    tool.setImgs(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

            if (!map.containsKey(Config.count)) {
                Iterator<Tool> toolIterator = tools.iterator();
                ObjectMapper mapper = new ObjectMapper();
                while (toolIterator.hasNext()) {
                    Tool t = toolIterator.next();
                    List imgs = null;
                    try {
                        imgs = mapper.readValue(t.getImg(), List.class);
                        t.setImgs(imgs);
                        t.setImg(null);
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }


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

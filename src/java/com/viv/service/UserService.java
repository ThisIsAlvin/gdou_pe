package com.viv.service;

import com.viv.Config;
import com.viv.dao.CallBoardOperation;
import com.viv.dao.UserOperation;
import com.viv.entity.CallBoard;
import com.viv.entity.User;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public class UserService {

    public Integer insert(User user) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            UserOperation userOperation = session.getMapper(UserOperation.class);
            userOperation.insert(user);
            session.commit();
            return user.getId();
        }finally {
            session.close();
        }
    }

    public void delete(Integer id) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            UserOperation userOperation = session.getMapper(UserOperation.class);
            userOperation.delete(id);
            session.commit();
        }finally {
            session.close();
        }
    }

    public List<User> select(Map map) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        if (map.containsKey(Config.like) && map.containsKey(Config.user)) {
            User user = (User) map.get(Config.user);
            if (user.getName() != null) {
                user.setName("%" + user.getName() + "%");
            }
            if (user.getPassword() != null) {
                user.setPassword("%" + user.getPassword() + "%");
            }
        }

        try {
            UserOperation userOperation = session.getMapper(UserOperation.class);
            List<User> users = userOperation.select(map);
            session.commit();
            return users;
        }finally {
            session.close();
        }
    }

    public Integer update(User user) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            UserOperation userOperation = session.getMapper(UserOperation.class);
            int count = userOperation.update(user);
            session.commit();
            return count;
        }finally {
            session.close();
        }
    }



}

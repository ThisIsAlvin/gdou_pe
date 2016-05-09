package com.viv.service;

import com.viv.Config;
import com.viv.dao.CallBoardOperation;
import com.viv.entity.CallBoard;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public class CallBoardService {
    private static Reader reader;
    private static SqlSessionFactory sessionFactory;

    static {
        try {
            reader = Resources.getResourceAsReader(Config.resource);
            sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSessionFactory() {
        return sessionFactory;
    }


    public Integer insert(CallBoard callBoard) {
        SqlSession session = sessionFactory.openSession();
        try {
            CallBoardOperation callBoardOperation = session.getMapper(CallBoardOperation.class);
            Integer id = callBoardOperation.insert(callBoard);
            session.commit();
            return id;
        }finally {
            session.close();
        }
    }

    public void delete(Integer id) {
        SqlSession session = sessionFactory.openSession();
        try {
            CallBoardOperation callBoardOperation = session.getMapper(CallBoardOperation.class);
            callBoardOperation.delete(id);
            session.commit();
        }finally {
            session.close();
        }
    }

    public List<CallBoard> select(Map map) {
        SqlSession session = sessionFactory.openSession();
        if (map.containsKey(Config.like) && map.containsKey(Config.callBoard)) {
            CallBoard callBoard = (CallBoard) map.get(Config.callBoard);
            if (callBoard.getMessage() != null) {
                callBoard.setMessage("%" + callBoard.getMessage() + "%");
            }
        }

        try {
            CallBoardOperation callBoardOperation = session.getMapper(CallBoardOperation.class);
            List<CallBoard> callBoards = callBoardOperation.select(map);
            session.commit();
            return callBoards;
        }finally {
            session.close();
        }
    }

    public int update(CallBoard callBoard) {
        SqlSession session = sessionFactory.openSession();
        try {
            CallBoardOperation callBoardOperation = session.getMapper(CallBoardOperation.class);
            int count = callBoardOperation.update(callBoard);
            session.commit();
            return count;
        }finally {
            session.close();
        }
    }



}

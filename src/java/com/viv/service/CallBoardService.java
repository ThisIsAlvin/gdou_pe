package com.viv.service;

import com.viv.Config;
import com.viv.dao.CallBoardOperation;
import com.viv.entity.CallBoard;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public class CallBoardService {

    public Integer insert(CallBoard callBoard) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            CallBoardOperation callBoardOperation = session.getMapper(CallBoardOperation.class);
            callBoardOperation.insert(callBoard);
            session.commit();
            return callBoard.getId();
        }finally {
            session.close();
        }
    }

    public void delete(Integer id) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            CallBoardOperation callBoardOperation = session.getMapper(CallBoardOperation.class);
            callBoardOperation.delete(id);
            session.commit();
        }finally {
            session.close();
        }
    }

    public List<CallBoard> select(Map map) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
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
        SqlSession session = SessionFactory.getSessionFactory().openSession();
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

package com.viv.service;

import com.viv.Config;
import com.viv.dao.FieldOperation;
import com.viv.dao.MatchOperation;
import com.viv.entity.Field;
import com.viv.entity.Match;
import org.apache.ibatis.session.SqlSession;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public class MatchService {

    public Integer insert(Match match) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            if (match.getImgs() != null) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    match.setImg(mapper.writeValueAsString(match.getImgs()));
                    match.setImgs(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            MatchOperation matchOperation = session.getMapper(MatchOperation.class);
            matchOperation.insert(match);
            session.commit();
            return match.getId();
        }finally {
            session.close();
        }
    }

    public void delete(Integer id) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            MatchOperation matchOperation = session.getMapper(MatchOperation.class);
            matchOperation.delete(id);
            session.commit();
        }finally {
            session.close();
        }
    }

    public List<Match> select(Map map) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        if (map.containsKey(Config.like) && map.containsKey(Config.match)) {
            Match match = (Match) map.get(Config.match);
            if (match.getTitle() != null) {
                match.setTitle("%" + match.getTitle() + "%");
            }
            if (match.getMessage() != null) {
                match.setMessage("%" + match.getMessage() + "%");
            }
            if (match.getBook_message() != null) {
                match.setBook_message("%" + match.getBook_message() + "%");
            }
            if (match.getReply() != null) {
                match.setReply("%" + match.getReply() + "%");
            }
            if (match.getImg() != null) {
                match.setImg("%" + match.getImg() + "%");
            }
        }

        try {
            MatchOperation matchOperation = session.getMapper(MatchOperation.class);
            List<Match> matches = matchOperation.select(map);
            session.commit();

            if (!map.containsKey(Config.count)) {
                Iterator<Match> matchIterator = matches.iterator();
                ObjectMapper mapper = new ObjectMapper();
                while (matchIterator.hasNext()) {
                    Match m = matchIterator.next();
                    List imgs = null;
                    try {
                        imgs = mapper.readValue(m.getImg(), List.class);
                        m.setImgs(imgs);
                        m.setImg(null);
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            return matches;
        }finally {
            session.close();
        }
    }

    public int update(Match match) {
        SqlSession session = SessionFactory.getSessionFactory().openSession();
        try {
            MatchOperation matchOperation = session.getMapper(MatchOperation.class);
            int count = matchOperation.update(match);
            session.commit();
            return count;
        }finally {
            session.close();
        }
    }



}

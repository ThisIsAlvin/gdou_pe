package com.viv.dao;

import com.viv.entity.Match;

import java.util.List;
import java.util.Map;

/**
 * Created by viv on 16-5-9.
 */
public interface MatchOperation {
    public Integer insert(Match match);

    public void delete(Integer id);

    public int update(Match match);

    public List<Match> select(Map map);
}

package hpscore.service;


import hpscore.domain.Pingwei;
import hpscore.domain.PingweiScore;
import hpscore.domain.Score;

import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */
public interface PingweiScoreService {

    List<PingweiScore> selectAll();
    List<PingweiScore> selectByModel(String model);
    List<PingweiScore> selectByPidAndModel(String pid, String model);
    List<PingweiScore> selectByPidAndEditorAndModel(String pid,String editor, String model);
}

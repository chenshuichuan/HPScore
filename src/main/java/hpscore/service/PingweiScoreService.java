package hpscore.service;


import hpscore.domain.PingweiScore;

import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */
public interface PingweiScoreService {

    List<PingweiScore> selectAll(int year);
    List<PingweiScore> selectByModel(String model,int year);
    List<PingweiScore> selectByPidAndModelAndYear(String pid, String model,int year);
    List<PingweiScore> selectByPidAndEditorAndModelAndYear(String pid,String editor, String model,int year);
}

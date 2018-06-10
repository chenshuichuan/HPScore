package hpscore.service;


import hpscore.domain.Score;

import java.util.List;
import java.util.Map;

/**
 * Created by tengj on 2017/4/7.
 */
public interface ScoreService {
    int add(Score score);
    int update(Score score);
    int delete(Score score);

    List<Score> selectAll();
    List<Score> selectByEditorAndModel(String editor,String model);
    List<Score> selectByModel(String model);
    Score selectByPidAndProIdAndModel(String pid, String proId,String model);
}

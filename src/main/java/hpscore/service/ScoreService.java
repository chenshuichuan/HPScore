package hpscore.service;


import hpscore.domain.InnovationScore;
import hpscore.domain.RelativeScore;
import hpscore.domain.Score;

import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */
public interface ScoreService {
    int add(Score score);
    int update(Score score);
    int delete(Score score);

    List<Score> selectAll(int year);
    List<Score> selectByEditorAndModelAndYear(String editor,String model,int year);
    List<Score> selectByModelAndYear(String model,int year);
    Score selectByPidAndProIdAndModelAndYear(String pid, String proId,String model,int year);

    int checkIfAllTheSameTimes(String model,List<String> pingweiList,int year);
    int calculateRelativeScore(String model,List<String> pingweiList,int year);
    int calculateByCodeAndModel(String code,String model,int year);

    //计算相对分的平均分、最大分、最小分,
    List<RelativeScore>  calculteRelativeScoreAverageAndMaxAndMin(String model,int yaer);
    List<InnovationScore> calculateInnovationScore(String model,int year);
    List<InnovationScore> calculateUsefulScore(String model,int year);

}

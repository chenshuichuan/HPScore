package hpscore.service;


import hpscore.domain.*;
import hpscore.repository.PingweiRepository;
import org.springframework.beans.factory.annotation.Autowired;

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

    int checkIfAllTheSameTimes(String model,List<String> pingweiList);
    int calculateRelativeScore(String model,List<String> pingweiList);
    int calculateByCodeAndModel(String code,String model);

    //计算相对分的平均分、最大分、最小分,
    List<RelativeScore>  calculteRelativeScoreAverageAndMaxAndMin(String model);
    List<InnovationScore> calculateInnovationScore(String model);
    List<InnovationScore> calculateUsefulScore(String model);

}

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

    //根据作品id和model保存作品的相对评分
    int saveRelativeScoreByProIdAndModel(RelativeScore relativeScore);

    //根据评委id作品id和model，保存该评委对该作品的相对评分
    int saveRelativeScoreByPidAndProIdAndModel(String pid, String proId,String model,double pscore);

    //计算相对分的平均分、最大分、最小分,失败返回失败的作品proid，成功返回"0"
    String calculteRelativeScoreAverageAndMaxAndMin(String model);


    List<InnovationScore> calculateInnovationScore(String model);
    List<InnovationScore> calculateUsefulScore(String model);
    List<Works> selectFinalScoreRanking(String model);

    List<Works> getSumUpAward(String model);
    List<Works> getInnovationAward(String model);
    List<Works> getUsefulAward(String model);
}

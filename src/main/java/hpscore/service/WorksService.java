package hpscore.service;


import hpscore.domain.Award;
import hpscore.domain.InnovationScore;
import hpscore.domain.Works;

import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */
public interface WorksService {
    int add(Works works);
    int update(Works works);
    int delete(Works works);

    List<Works> selectAll(int year);
    List<String> selectAllName(int year);
    List<String> selectAllCodeByModelAndYear(String model,int year);

    List<Works> awardToWorks(List<Award> awardList);
    List<Works> selectFinalScoreRanking(String model,int year);
    List<Works> getSumUpAward(String model,int year);
    List<Works> getInnovationAward(String model,int year);
    List<Works> getUsefulAward(String model,int year);

    int saveAsAward(List<InnovationScore> innovationScoreList, String scoreType,int year);
}

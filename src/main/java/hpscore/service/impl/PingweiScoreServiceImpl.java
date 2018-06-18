package hpscore.service.impl;


import hpscore.domain.Pingwei;
import hpscore.domain.PingweiScore;
import hpscore.domain.Score;
import hpscore.domain.Works;
import hpscore.repository.PingweiRepository;
import hpscore.repository.ScoreRepository;
import hpscore.repository.WorksRepository;
import hpscore.service.PingweiScoreService;
import hpscore.service.ScoreService;
import hpscore.tools.ScoreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */
@Service
public class PingweiScoreServiceImpl implements PingweiScoreService {

    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    WorksRepository worksRepository;
    @Autowired
    PingweiRepository pingweiRepository;
    @Override
    public List<PingweiScore> selectAll() {
        List<Score> scoreList= scoreRepository.findAll();
        List<PingweiScore>pingweiScoreList =ScoreToPingweiScore( scoreList);
        return pingweiScoreList;
    }

    @Override
    public List<PingweiScore> selectByModel(String model) {
        List<Score> scoreList= scoreRepository.findByModel(model);
        List<PingweiScore>pingweiScoreList =ScoreToPingweiScore( scoreList);
        return pingweiScoreList;
    }

    @Override
    public List<PingweiScore> selectByPidAndModel(String pid, String model) {
        List<Score> scoreList= scoreRepository.findByPidAndModel(pid,model);
        List<PingweiScore>pingweiScoreList =ScoreToPingweiScore( scoreList);
        return pingweiScoreList;
    }

    private List<PingweiScore> ScoreToPingweiScore(List<Score> scoreList){
        List<PingweiScore>pingweiScoreList = new ArrayList<>();
        for (Score score:scoreList){
            try {
                PingweiScore pingweiScore = new PingweiScore(score.getId(),score.getPid(),score.getProId(),
                        score.getOption1(),score.getOption2(),score.getOption3(),
                        score.getOption4(),score.getOption5(),score.getOption6(),
                        score.getTotalScore(),score.getModel());
                //保留三位小数
                double decimalDouble = ScoreUtil.DecimalDouble(score.getFinalScore(),3);
                pingweiScore.setFinalScore(decimalDouble);

                Pingwei pingwei = pingweiRepository.findByCodeAndModel(score.getPid(),score.getModel());
                if (pingwei!=null)pingweiScore.setpName(pingwei.getName());
                else
                    throw new Exception("要查找的评委不存在！");
                Works works = worksRepository.findByCodeAndModel(score.getProId(),score.getModel());
                if(works!=null){
                    pingweiScore.setProName(works.getName());
                    pingweiScore.setBianHao(works.getBianHao());
                }
                else
                    throw new Exception("要查找的作品不存在！");
                pingweiScoreList.add(pingweiScore);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //按照作品编号排序
        Collections.sort(pingweiScoreList,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof PingweiScore && o2 instanceof PingweiScore){
                    PingweiScore e1 = (PingweiScore) o1;
                    PingweiScore e2 = (PingweiScore) o2;
                    return e1.getProId().compareTo(e2.getProId());
                }
                throw new ClassCastException("不能转换为PingweiScore类型");
            }
        });

        return pingweiScoreList;
    }
}

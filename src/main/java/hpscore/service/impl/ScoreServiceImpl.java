package hpscore.service.impl;


import hpscore.domain.*;
import hpscore.repository.PingweiRepository;
import hpscore.repository.RelativeScoreRepository;
import hpscore.repository.ScoreRepository;
import hpscore.repository.WorksRepository;
import hpscore.service.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tengj on 2017/4/7.
 */
@Service
public class ScoreServiceImpl implements ScoreService {
    private final static Logger logger = LoggerFactory.getLogger(ScoreServiceImpl.class);

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    PingweiRepository pingweiRepository;
    @Autowired
    WorksRepository worksRepository;

    @Autowired
    RelativeScoreRepository relativeScoreRepository;

    @Override
    public int add(Score score) {
        Score score1 = scoreRepository.save(score);
        if (null != score1) return 1;
        return 0;
    }

    @Override
    public int update(Score score) {
       Score score1 = scoreRepository.findOne(score.getId());
       if (null!=score1){
           scoreRepository.save(score);
           return 1;
       }
       return 0;
    }

    @Override
    public int delete(Score score) {
        Score score1 = scoreRepository.findOne(score.getId());
        if (null!=score1){
            scoreRepository.delete(score);
            return 1;
        }
        return 0;
    }

    @Override
    public List<Score> selectAll() {
        return scoreRepository.findAll();
    }

    //返回该editor编辑过的model类型的记录数据
    @Override
    public List<Score> selectByEditorAndModel(String editor, String model) {
        List<Score> scores1 = scoreRepository.findByEditor1AndModel(editor,model);
        List<Score> scores2 = scoreRepository.findByEditor2AndModel(editor,model);
        if(scores1!=null){
            if(scores2!=null)scores1.addAll(scores2);
        }
        return scores1;
    }

    @Override
    public List<Score> selectByModel(String model) {

        return scoreRepository.findByModel(model);
    }

    @Override
    public Score selectByPidAndProIdAndModel(String pid, String proId, String model) {
        return scoreRepository.findByPidAndProIdAndModel(pid,proId,model);
    }


    // //检查是否每个评委都对所有作品进行了相同数量的评分记录，如：每个评委都有24条评分记录
    @Override
    public int checkIfAllTheSameTimes(String model,List<String> pingweiList){

        int index=0;
        int times=0;
        for (String code:pingweiList){
            List<Score> scores = scoreRepository.findByPidAndModel(code,model);
            if(index>0){
                if(times!=scores.size()){
                    break;
                }
            }
            times = scores.size();
            index++;
        }
        return index;
    }
    // //根据传入的评委id（code）列表计算相对分
    @Override
    public int calculateRelativeScore(String model,List<String> pingweiList){
        //出错标志
        int index=0;
        int flag = 0;
        for (String code:pingweiList){
            flag = calculateByCodeAndModel( code, model);
            if(flag==0){
                logger.info("calculateRelativeScore error:"+code);
                break;
            }
            index++;
        }
        return index;
    }

    //根据评委的pid(对应code)和model计算该评委的所有评分的相对分，和
    @Override
    public int calculateByCodeAndModel(String code,String model){
        List<Score> scores = scoreRepository.findByPidAndModel(code,model);
        int maxScore=0;
        int minScore=100;
        //找到该评委的最大最小值分数
        for (Score score:scores){
            if(score.getTotalScore()<minScore)minScore = score.getTotalScore();
            if(score.getTotalScore()>maxScore)maxScore = score.getTotalScore();
        }
        //该评委的评分数据有问题
        if(maxScore <= 0||minScore<=0 ||maxScore>=100||minScore >= 100){
            logger.info("calculateRelativeScore error:"+code);
            System.out.println("calculateRelativeScore error:"+code);
            return 0;
        }
        if(maxScore==minScore){
            logger.info("maxScore==minScore:"+code);
            System.out.println("maxScore==minScore:"+code);
            return 0;
        }
        //保存查询到的最大最小分
        Pingwei pingwei = pingweiRepository.findByCodeAndModel(code,model);
        if (pingwei!=null){
            pingwei.setMaxScore(maxScore);
            pingwei.setMinScore(minScore);
            pingweiRepository.save(pingwei);
        }
        else{
            System.out.println("pingweiRepository.save(pingwei)code:"+code+"model:"+model+"error!");
            logger.info("pingweiRepository.save(pingwei)code:"+code+"model:"+model+"error!");
            return 0;
        }
        //计算相对分
        //当Pmax == Pmin时？？？？？？？？？？？？？？
        double maxToMin =(double)(maxScore-minScore);
        for (Score score:scores){
            //计算公式
            double relativeScore = (double)(score.getTotalScore()-minScore)/maxToMin*20+70;
            //保存到数据库中
            System.out.println("double relativeScore = "+relativeScore);
            score.setFinalScore(relativeScore);
            Score score1 = scoreRepository.save(score);
            if(score1!=null)System.out.println("评委"+code+"-作品"+score1.getProId()+",相对分保存成功！");
            else System.out.println("评委"+code+"-作品"+score.getProId()+",相对分保存失败！");

            //保存到相对分到评分汇总表
            saveRelativeScoreByPidAndProIdAndModel(code,score.getProId(), model, relativeScore);
        }
        return 1;
    }

    @Override
    public int saveRelativeScoreByProIdAndModel(RelativeScore relativeScore) {

        RelativeScore temp = relativeScoreRepository.findByProIdAndModel(
                relativeScore.getProId(),relativeScore.getModel());
        if(temp==null){
            RelativeScore relativeScore3 = relativeScoreRepository.save(relativeScore);
            logger.info("添加，RelativeScore id="+relativeScore3.getId());
        }
        else{
            logger.info("更新，RelativeScore id="+temp.getProId());
            relativeScore.setId(temp.getId());
            RelativeScore relativeScore1 = relativeScoreRepository.save(temp);
        }
        return 0;
    }

    @Override
    public int saveRelativeScoreByPidAndProIdAndModel(
            String pid, String proId, String model,double pscore) {

        RelativeScore temp = relativeScoreRepository.findByProIdAndModel(
                proId,model);
        //直接更新
        if(temp!=null){
        }
        //要先添加
        else{
            Works works = worksRepository.findByCodeAndModel(proId,model);
            if (works!=null){
                temp = new RelativeScore(works.getCode(),works.getName(),model);
            }
            else{
                logger.info("saveRelativeScoreByPidAndProIdAndModel 添加相对分失败！作品不存在");
                return -1;
            }
        }
        switch (pid){
            case "1":{
                temp.setpScore1(pscore);
            }break;
            case "2":{
                temp.setpScore2(pscore);
            }break;
            case "3":{
                temp.setpScore3(pscore);
            }break;
            case "4":{
                temp.setpScore4(pscore);
            }break;
            case "5":{
                temp.setpScore5(pscore);
            }break;
            case "6":{
                temp.setpScore6(pscore);
            }break;
            case "7":{
                temp.setpScore7(pscore);
            }break;
            case "8":{
                temp.setpScore8(pscore);
            }break;
            case "9":{
                temp.setpScore9(pscore);
            }break;
            case "10":{
                temp.setpScore10(pscore);
            }break;
            case "11":{
                temp.setpScore11(pscore);
            }break;
            default:
                logger.info("saveRelativeScoreByPidAndProIdAndModel: pid 出错！");
        }
        saveRelativeScoreByProIdAndModel(temp);
        return 0;
    }

    //计算相对分的平均分、最大分、最小分,失败返回失败的作品proid，成功返回"0"
    @Override
    public String calculteRelativeScoreAverageAndMaxAndMin(String model) {

        List<RelativeScore> relativeScoreList =  relativeScoreRepository.findByModel(model);

        //记录条数是否符合
        //        if(relativeScoreList.size()!=11){
        //        }
        String proId = "0";
        //所有的分数要在70-90分之间
        for (RelativeScore relativeScore:relativeScoreList){
            if( AllScoreIsOk(relativeScore)){
                //都符合，开始计算
                double maxScore = GetMaxRelativeScore(relativeScore);
                double minScore = GetMinRelativeScore(relativeScore);
                double average =  (GetTotalScore(relativeScore)-maxScore-minScore)/11.0;

                relativeScore.setMaxScore(maxScore);
                relativeScore.setMinScore(minScore);
                relativeScore.setAverage(average);
                saveRelativeScoreByProIdAndModel(relativeScore);
            }
            else{
                proId=relativeScore.getProId();
                break;
            }
        }
        return proId;
    }

    private boolean ScoreIsOk(double relativeScore){
        boolean result = true;
        //相对分应该在[70,90]之间
        if (relativeScore>=70.0&&relativeScore<=90.0)
            result = true;
        else
            result = false;
        return  result;
    }
    private boolean AllScoreIsOk(RelativeScore relativeScore){
        boolean result = false;
        //相对分应该在[70,90]之间
        if(ScoreIsOk(relativeScore.getpScore1())&&
                ScoreIsOk(relativeScore.getpScore2())&&
                ScoreIsOk(relativeScore.getpScore3())&&
                ScoreIsOk(relativeScore.getpScore4())&&
                ScoreIsOk(relativeScore.getpScore5())&&
                ScoreIsOk(relativeScore.getpScore6())
                ) {
            if (ScoreIsOk(relativeScore.getpScore7()) &&
                    ScoreIsOk(relativeScore.getpScore8()) &&
                    ScoreIsOk(relativeScore.getpScore9()) &&
                    ScoreIsOk(relativeScore.getpScore10()) &&
                    ScoreIsOk(relativeScore.getpScore11())
                    ) {
                result = true;
            }
        }
        return  result;
    }

    private double GetMaxRelativeScore(RelativeScore relativeScore){
        double maxScore = 0;
        //相对分应该在[70,90]之间

        if(relativeScore.getpScore1()>maxScore){
            maxScore = relativeScore.getpScore1();
        }
        if(relativeScore.getpScore2()>maxScore){
            maxScore = relativeScore.getpScore2();
        }
        if(relativeScore.getpScore3()>maxScore){
            maxScore = relativeScore.getpScore3();
        }
        if(relativeScore.getpScore4()>maxScore){
            maxScore = relativeScore.getpScore4();
        }
        if(relativeScore.getpScore5()>maxScore){
            maxScore = relativeScore.getpScore5();
        }

        if(relativeScore.getpScore6()>maxScore){
            maxScore = relativeScore.getpScore6();
        }
        if(relativeScore.getpScore7()>maxScore){
            maxScore = relativeScore.getpScore7();
        }
        if(relativeScore.getpScore8()>maxScore){
            maxScore = relativeScore.getpScore8();
        }
        if(relativeScore.getpScore9()>maxScore){
            maxScore = relativeScore.getpScore9();
        }
        if(relativeScore.getpScore10()>maxScore){
            maxScore = relativeScore.getpScore10();
        }
        if(relativeScore.getpScore11()>maxScore){
            maxScore = relativeScore.getpScore11();
        }
        if (maxScore==0.0)logger.info("maxScore==0.0");
        return  maxScore;
    }
    private double GetMinRelativeScore(RelativeScore relativeScore){
        double minScore = 100;
        //相对分应该在[70,90]之间

        if(relativeScore.getpScore1()<minScore){
            minScore = relativeScore.getpScore1();
        }
        if(relativeScore.getpScore2()<minScore){
            minScore = relativeScore.getpScore2();
        }
        if(relativeScore.getpScore3()<minScore){
            minScore = relativeScore.getpScore3();
        }
        if(relativeScore.getpScore4()<minScore){
            minScore = relativeScore.getpScore4();
        }
        if(relativeScore.getpScore5()<minScore){
            minScore = relativeScore.getpScore5();
        }

        if(relativeScore.getpScore6()<minScore){
            minScore = relativeScore.getpScore6();
        }
        if(relativeScore.getpScore7()<minScore){
            minScore = relativeScore.getpScore7();
        }
        if(relativeScore.getpScore8()<minScore){
            minScore = relativeScore.getpScore8();
        }
        if(relativeScore.getpScore9()<minScore){
            minScore = relativeScore.getpScore9();
        }
        if(relativeScore.getpScore10()<minScore){
            minScore = relativeScore.getpScore10();
        }
        if(relativeScore.getpScore11()<minScore){
            minScore = relativeScore.getpScore11();
        }
        if (minScore==100.0)logger.info("minScore==0.0");
        return  minScore;
    }
    private double GetTotalScore(RelativeScore relativeScore){
        double totalScore = 0;
        //相对分应该在[70,90]之间
        totalScore = relativeScore.getpScore1()+relativeScore.getpScore2()+
                relativeScore.getpScore3()+relativeScore.getpScore4()+
                relativeScore.getpScore5()+relativeScore.getpScore6()+
                relativeScore.getpScore7()+relativeScore.getpScore8()+
                relativeScore.getpScore9()+relativeScore.getpScore10()+
                relativeScore.getpScore11();
        return  totalScore;
    }

    //计算创新性分数
    public List<InnovationScore> calculateInnovationScore(String model){
        List<InnovationScore> innovationScoreList = new ArrayList<>();

        List<Pingwei> pingweiList = pingweiRepository.findByModel(model);
        List<Works> worksList = worksRepository.findByModel(model);
        for (Works works: worksList){
            InnovationScore innovationScore =
                    new InnovationScore(works.getCode(),works.getName(),model);
            for (Pingwei pingwei:pingweiList){
                Score score = scoreRepository.findByPidAndProIdAndModel(
                        pingwei.getCode(),works.getCode(),model);
                if(score!=null){

                }
            }
        }

        return innovationScoreList;
    }
    private void setInnovation(String pid,int score,InnovationScore innovationScore){

    }
    public List<InnovationScore> calculateUsefulScore(String model){
        List<Score> scoreList = scoreRepository.findByModel(model);
        List<InnovationScore> innovationScoreList = new ArrayList<>();

        for (Score score:scoreList){

        }
        return innovationScoreList;
    }

}

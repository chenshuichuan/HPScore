package hpscore.service.impl;


import hpscore.domain.*;
import hpscore.repository.PingweiRepository;
import hpscore.repository.ScoreRepository;
import hpscore.repository.WorksRepository;
import hpscore.service.ScoreService;
import hpscore.tools.ScoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<Score> selectAll(int year) {
        return scoreRepository.findByYear(year);
    }

    //返回该editor编辑过的model类型的记录数据
    @Override
    public List<Score> selectByEditorAndModelAndYear(String editor, String model,int year) {
        List<Score> scores1 = scoreRepository.findByEditor1AndModelAndYear(editor,model,year);
        List<Score> scores2 = scoreRepository.findByEditor2AndModelAndYear(editor,model,year);
        if(scores1!=null){
            if(scores2!=null)scores1.addAll(scores2);
        }
        return scores1;
    }

    @Override
    public List<Score> selectByModelAndYear(String model,int year) {

        return scoreRepository.findByModelAndYear(model,year);
    }

    @Override
    public Score selectByPidAndProIdAndModelAndYear(String pid, String proId, String model,int year) {
        return scoreRepository.findByPidAndProIdAndModelAndYear(pid,proId,model,year);
    }


    // //检查是否每个评委都对所有作品进行了相同数量的评分记录，如：每个评委都有24条评分记录
    @Override
    public int checkIfAllTheSameTimes(String model,List<String> pingweiList,int year){

        int index=0;
        int times=0;
        for (String code:pingweiList){
            List<Score> scores = scoreRepository.findByPidAndModelAndYear(code,model,year);
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
    //根据传入的评委code列表计算相对分
    @Override
    public int calculateRelativeScore(String model,List<String> pingweiList,int year){
        //出错标志
        int index=0;
        int flag = 0;
        for (String code:pingweiList){
            flag = calculateByCodeAndModel(code, model,year);
            if(flag==0){
                logger.info("calculateRelativeScore error:"+code);
                break;
            }
            index++;
        }
        return index;
    }

    //根据评委的pid(对应code)和model计算该评委的所有评分的相对分，
    @Override
    public int calculateByCodeAndModel(String code,String model,int year){
        List<Score> scores = scoreRepository.findByPidAndModelAndYear(code,model,year);
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
        Pingwei pingwei = pingweiRepository.findByCodeAndModelAndYear(code,model,year);
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

        }
        return 1;
    }

    //计算相对分的平均分、最大分、最小分,返回计算出的平均分表
    @Override
    public List<RelativeScore> calculteRelativeScoreAverageAndMaxAndMin(String model,int year) {

        //得到相关model的所有数据，要求此时score表的相对分已经计算完成，否则得出的分数无意义
        //获得本组所有作品
        List<Works> worksList = worksRepository.findByModelAndYear(model,year);
        //获得本组所有评委
        List<Pingwei> pingweiList = pingweiRepository.findByModelAndYear(model,year);
        if(pingweiList==null||pingweiList.size()==0)return null;
        int pingweiSize = pingweiList.size();

        List<RelativeScore> relativeScoreList = new ArrayList<>();
        //根据作品构造相对平均分、创新平均分，实用平均分等
        for(Works works: worksList){
            List<Score> scoreList = scoreRepository.findByProIdAndModelAndYear(works.getCode(),model,year);
            double[] maxScore ={0,0,0,0,0,0,0};//相对分最大分、6个单项的最大分
            double[] minScore ={100,100,100,100,100,100,100};
            double[] totalScore = {0,0,0,0,0,0,0};//总分
            double[] average ={0,0,0,0,0,0,0};
            RelativeScore relativeScore = new RelativeScore(
                    works.getCode(),works.getName(),model,pingweiSize);
            for (Score score: scoreList){
                double decimalDouble = ScoreUtil.DecimalDouble(score.getFinalScore()
                        ,3);
                score.setFinalScore(decimalDouble);//保留三位小数

                //设置某评委的相对分
                int pid = Integer.parseInt(score.getPid());
                if(pid>=1&&pid<=pingweiSize)relativeScore.getpScores()[pid-1]=score.getFinalScore();
                else {
                    logger.info("pid出错！请保证评委序号从1开始！请保证数据库数据存在！");
                }
                //
                CompareToMaxAndMin(maxScore,minScore,totalScore,score);
            }
            //计算相对平均分
            for (int i=0;i<7;i++){
                average[i] = (totalScore[i]-maxScore[i]-minScore[i])/(double)(pingweiSize-2);
                //保留三位小数
                average[i] = ScoreUtil.DecimalDouble(average[i],3);
            }

            relativeScore.setBianHao(works.getBianHao());
            relativeScore.setMinScore(minScore[0]);
            relativeScore.setMaxScore(maxScore[0]);
            relativeScore.setAverage(average[0]);
            for (int i=0;i<6;i++){
                relativeScore.getpAverage()[i]=average[i+1];
            }

            relativeScoreList.add(relativeScore);

            //相对分平均分写入作品表
            Works works1 = worksRepository.findByCodeAndModelAndYear(relativeScore.getProId(),model,year);
            if(works1!=null){
                works1.setFinalScore(average[0]);
                worksRepository.save(works1);
            }
            //出错
            else{
                logger.info("该作品序号不存在！请检查数据库！");
            }
        }
        return relativeScoreList;
    }
    private int CompareToMaxAndMin(double[]maxScore,double[] minScore,double[] totalScore,Score score){
        if (maxScore[0]<score.getFinalScore())
            maxScore[0]=score.getFinalScore();
        if (maxScore[1]<score.getOption1())
            maxScore[1]=score.getOption1();
        if (maxScore[2]<score.getOption2())
            maxScore[2]=score.getOption2();
        if (maxScore[3]<score.getOption3())
            maxScore[3]=score.getOption3();
        if (maxScore[4]<score.getOption4())
            maxScore[4]=score.getOption4();
        if (maxScore[5]<score.getOption5())
            maxScore[5]=score.getOption5();
        if (maxScore[6]<score.getOption6())
            maxScore[6]=score.getOption6();

        if (minScore[0]>score.getFinalScore())
            minScore[0]=score.getFinalScore();
        if (minScore[1]>score.getOption1())
            minScore[1]=score.getOption1();
        if (minScore[2]>score.getOption2())
            minScore[2]=score.getOption2();
        if (minScore[3]>score.getOption3())
            minScore[3]=score.getOption3();
        if (minScore[4]>score.getOption4())
            minScore[4]=score.getOption4();
        if (minScore[5]>score.getOption5())
            minScore[5]=score.getOption5();
        if (minScore[6]>score.getOption6())
            minScore[6]=score.getOption6();

        totalScore[0]+=score.getFinalScore();
        totalScore[1]+=score.getOption1();
        totalScore[2]+=score.getOption2();
        totalScore[3]+=score.getOption3();
        totalScore[4]+=score.getOption4();
        totalScore[5]+=score.getOption5();
        totalScore[6]+=score.getOption6();
        return 0;
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
//        //相对分应该在[70,90]之间
//        if(ScoreIsOk(relativeScore.getpScore1())&&
//                ScoreIsOk(relativeScore.getpScore2())&&
//                ScoreIsOk(relativeScore.getpScore3())&&
//                ScoreIsOk(relativeScore.getpScore4())&&
//                ScoreIsOk(relativeScore.getpScore5())&&
//                ScoreIsOk(relativeScore.getpScore6())
//                ) {
//            if (ScoreIsOk(relativeScore.getpScore7()) &&
//                    ScoreIsOk(relativeScore.getpScore8()) &&
//                    ScoreIsOk(relativeScore.getpScore9()) &&
//                    ScoreIsOk(relativeScore.getpScore10()) &&
//                    ScoreIsOk(relativeScore.getpScore11())
//                    ) {
//                result = true;
//            }
//        }
        return  result;
    }

    //计算创新性分数
    @Override
    public List<InnovationScore> calculateInnovationScore(String model,int year){
        List<InnovationScore> innovationScoreList = new ArrayList<>();

        List<Pingwei> pingweiList = pingweiRepository.findByModelAndYear(model,year);
        List<Works> worksList = worksRepository.findByModelAndYear(model,year);

        //以model下的所有作品为循环构建
        for (Works works: worksList){
            InnovationScore innovationScore =
                    new InnovationScore(works.getCode(),works.getName(),model,pingweiList.size());
            innovationScore.setBianHao(works.getBianHao());
            int maxScore = 0;
            int minScore = 100;
            int totalScore = 0;
            for (Pingwei pingwei:pingweiList){
                Score score = scoreRepository.findByPidAndProIdAndModelAndYear(
                        pingwei.getCode(),works.getCode(),model,year);
                if(score!=null){
                    innovationScore = setInnovation(pingwei.getCode(),score.getOption3(), innovationScore);
                    //计算该作品的最大最小分
                    if(score.getOption3()>maxScore)maxScore = score.getOption3();
                    if(score.getOption3()<minScore)minScore = score.getOption3();
                    totalScore+=score.getOption3();
                }
                //该评委对该评分的记录还没有录入，记为0
                else{
                    innovationScore = setInnovation(pingwei.getCode(),0, innovationScore);
                }
            }
            double average = (double)(totalScore-maxScore-minScore)/(double)(pingweiList.size()-2);
            //保留三位小数
            average = ScoreUtil.DecimalDouble(average,3);

            innovationScore.setMaxScore(maxScore);
            innovationScore.setMinScore(minScore);
            innovationScore.setAverage(average);
            innovationScoreList.add(innovationScore);
        }
        return innovationScoreList;
    }
    //根据评委序号和该评委分数，设置分数属于哪个评委，所以程序数据中，评委序号必须从1开始
    private InnovationScore setInnovation(String pid,int score,InnovationScore innovationScore){
        int pidNumber = Integer.parseInt(pid);
        if (pidNumber>innovationScore.getpScores().length||pidNumber<=0){
            logger.info("setInnovation: 评委编号不符合规则！请检查！");
            System.out.println("setInnovation:评委编号不符合规则！请检查！");
        }
        //评委编号从1开始
        else innovationScore.getpScores()[pidNumber-1]=score;
        return innovationScore;
    }

    //计算实用性分数
    @Override
    public List<InnovationScore> calculateUsefulScore(String model,int year){
        List<InnovationScore> innovationScoreList = new ArrayList<>();

        List<Pingwei> pingweiList = pingweiRepository.findByModelAndYear(model,year);
        List<Works> worksList = worksRepository.findByModelAndYear(model,year);

        //以model下的所有作品为循环构建
        for (Works works: worksList){
            InnovationScore innovationScore =
                    new InnovationScore(works.getCode(),works.getName(),model,pingweiList.size());
            innovationScore.setBianHao(works.getBianHao());
            int maxScore = 0;
            int minScore = 100;
            int totalScore = 0;
            for (Pingwei pingwei:pingweiList){
                Score score = scoreRepository.findByPidAndProIdAndModelAndYear(
                        pingwei.getCode(),works.getCode(),model,year);
                if(score!=null){
                    innovationScore = setInnovation(pingwei.getCode(),score.getOption5(), innovationScore);
                    //计算该作品的最大最小分
                    if(score.getOption5()>maxScore)maxScore = score.getOption5();
                    if(score.getOption5()<minScore)minScore = score.getOption5();
                    totalScore+=score.getOption5();
                }
                //该评委对该评分的记录还没有录入，记为0
                else{
                    innovationScore = setInnovation(pingwei.getCode(),0, innovationScore);
                }
            }
            double average = (double)(totalScore-maxScore-minScore)/(double)(pingweiList.size()-2);
            //保留三位小数
            average = ScoreUtil.DecimalDouble(average,3);

            logger.info("average="+average);
            innovationScore.setMaxScore(maxScore);
            innovationScore.setMinScore(minScore);
            innovationScore.setAverage(average);
            innovationScoreList.add(innovationScore);
        }

        return innovationScoreList;
    }

}

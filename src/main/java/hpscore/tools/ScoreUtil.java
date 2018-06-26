package hpscore.tools;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/9
 * Time: 22:40
 */

import hpscore.domain.InnovationScore;
import hpscore.domain.RelativeScore;
import hpscore.domain.Score;
import hpscore.domain.Works;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *@ClassName: ScoreUtil
 *@Description: score相关计算的实用函数
 *@Author: Ricardo
 *@Date: 2018/6/9 22:40
 **/
public class ScoreUtil {
    public static void main(String[] args){
        for (int i = 0; i < 100; i++) {
            System.out.println("GetRandomNumber = "+ScoreUtil.GetRandomNumber(0,10));
        }
        for (int i = 0; i < 50; i++) {
            System.out.println("GetRandomNumber = "+ScoreUtil.GetRandomNumber(5,20));
        }
        long startTime = System.currentTimeMillis();
        Date date = new Date(startTime);
        System.out.println(date.toString());
    }
    public static int GetRandomNumber(int up,int top){
        Random rand = new Random();
        return rand.nextInt(top-up) + up;
    }
    //将number 保留scale位小数
    public static double DecimalDouble(double number,int scale){
        BigDecimal b = new BigDecimal(number);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    //排序,且产生名次
    public static void sortWorks(List<Works>worksList){

        Collections.sort(worksList,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof Works && o2 instanceof Works){
                    Works e1 = (Works) o1;
                    Works e2 = (Works) o2;
                    return StringUtil.compareTwoDouble(e1.getFinalScore(),e2.getFinalScore());
                }
                throw new ClassCastException("不能转换为Works类型");
            }
        });
        int rangking = 0;
        for (int i = 0; i < worksList.size(); i++) {
            boolean shouldInc = true;
            if(i>0){
                double score1 = worksList.get(i-1).getFinalScore();
                double score2 = worksList.get(i).getFinalScore();
                //如果该分数和上一个分数相等，排名就不增加
                if (StringUtil.isEquals(score1,score2)){
                    shouldInc=false;
                }
                //否则当前排名从i重新开始排，并且会在下面的时候增1
                else{
                    rangking=i;
                }
            }
            if (shouldInc)rangking++;
            worksList.get(i).setRanking(rangking);
        }
    }
    //排序,且产生名次
    public static void sortRelativeScore(List<RelativeScore>relativeScoreList){
        Collections.sort(relativeScoreList,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof RelativeScore && o2 instanceof RelativeScore){
                    RelativeScore e1 = (RelativeScore) o1;
                    RelativeScore e2 = (RelativeScore) o2;
                    return StringUtil.compareTwoDouble(e1.getAverage(),e2.getAverage());
                }
                throw new ClassCastException("不能转换为Works类型");
            }
        });
        int rangking = 0;
        for (int i = 0; i < relativeScoreList.size(); i++) {
            boolean shouldInc = true;
            if(i>0){
                double score1 = relativeScoreList.get(i-1).getAverage();
                double score2 = relativeScoreList.get(i).getAverage();
                //如果该分数和上一个分数相等，排名就不增加
                if (StringUtil.isEquals(score1,score2)){
                    shouldInc=false;
                }
                //否则当前排名从i重新开始排，并且会在下面的时候增1
                else{
                    rangking=i;
                }
            }
            if (shouldInc)rangking++;
            relativeScoreList.get(i).setRanking(rangking);
        }
    }
    //排序,且产生名次
    public static void sortInnovationScore(List<InnovationScore>innovationScoreList){
        Collections.sort(innovationScoreList,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof InnovationScore && o2 instanceof InnovationScore){
                    InnovationScore e1 = (InnovationScore) o1;
                    InnovationScore e2 = (InnovationScore) o2;
                    return StringUtil.compareTwoDouble(e1.getAverage(),e2.getAverage());
                }
                throw new ClassCastException("不能转换为Works类型");
            }
        });
        int rangking = 0;
        for (int i = 0; i < innovationScoreList.size(); i++) {
            boolean shouldInc = true;
            if(i>0){
                double score1 = innovationScoreList.get(i-1).getAverage();
                double score2 = innovationScoreList.get(i).getAverage();
                //如果该分数和上一个分数相等，排名就不增加
                if (StringUtil.isEquals(score1,score2)){
                    shouldInc=false;
                }
                //否则当前排名从i重新开始排，并且会在下面的时候增1
                else{
                    rangking=i;
                }
            }
            if (shouldInc)rangking++;
            innovationScoreList.get(i).setRanking(rangking);
        }
    }
}

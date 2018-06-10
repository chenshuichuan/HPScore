package hpscore.tools;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/9
 * Time: 22:40
 */

import hpscore.domain.Score;

/**
 *@ClassName: ScoreUtil
 *@Description: score相关计算的实用函数
 *@Author: Ricardo
 *@Date: 2018/6/9 22:40
 **/
public class ScoreUtil {
    public static int calTotalScore(Score score){
        int totalScore = 0;
        totalScore+=score.getOption1();
        totalScore+=score.getOption1();
        totalScore+=score.getOption1();
        totalScore+=score.getOption1();
        totalScore+=score.getOption1();
        totalScore+=score.getOption1();
        return totalScore;
    }
    public static int calTotalScore(String option1Str,String option2Str,String option3Str,
                                    String option4Str, String option5Str,String option6Str){
        int option1 = Integer.parseInt(option1Str);
        int option2 = Integer.parseInt(option2Str);
        int option3 = Integer.parseInt(option3Str);
        int option4 = Integer.parseInt(option4Str);
        int option5 = Integer.parseInt(option5Str);
        int option6 = Integer.parseInt(option6Str);

        int totalScore = 0;
        totalScore+=option1;
        totalScore+=option2;
        totalScore+=option3;
        totalScore+=option4;
        totalScore+=option5;
        totalScore+=option6;
        return totalScore;
    }
}

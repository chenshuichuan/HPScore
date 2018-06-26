package hpscore.tools;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/9
 * Time: 22:40
 */

import hpscore.domain.Score;

import java.math.BigDecimal;
import java.sql.Date;
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
}

package hpscore.service;



/**
 * Created by tengj on 2017/4/7.
 */
public interface ExcelService {
    //打分审核表，评委原始打分表
    String reviewExcel(String model,int year);
    //打分转化表，评委原始打分表增加相对分列
    String reviewTransferExcel(String model,int year);

    //相对分统计表、相对分排名表，创新分、实用分
    String relativeScoreExcel(String model,int year);

    //最终作品相对分平均分排名表
    String finalScoreExcel(String model,int year);

    //评分统计表，各个子项的平均分
    String scoringSumUpExcel(String model,int year);
}

package hpscore.service;



/**
 * Created by tengj on 2017/4/7.
 */
public interface GenerateExcelThreadService {
    void executeAsyncTask(Integer i,String model,int year);
    void executeGenerateAward(String model,int year);
}

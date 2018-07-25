package hpscore.service.impl;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/21
 * Time: 22:08
 */

import hpscore.domain.InnovationScore;
import hpscore.service.ExcelService;
import hpscore.service.GenerateExcelThreadService;
import hpscore.service.ScoreService;
import hpscore.service.WorksService;
import hpscore.tools.ScoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

/**
 *@ClassName: GenerateExcelThreadServiceImpl
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/6/21 22:08
 **/
@Service
public class GenerateExcelThreadServiceImpl implements GenerateExcelThreadService {


    private final static Logger logger = LoggerFactory.getLogger(GenerateExcelThreadServiceImpl.class);
    @Autowired
    private ExcelService excelService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private WorksService worksService;
    /**
     * 异常调用返回Future
     *
     * @param i
     * @return
     * @throws InterruptedException
     */
    @Async
    public Future<String> asyncInvokeReturnFuture(int i) throws InterruptedException {
        System.out.println("input is " + i);
        Thread.sleep(1000 * 5);
        Future<String> future = new AsyncResult<String>("success:" + i);
        // Future接收返回值，这里是String类型，可以指明其他类型
        return future;
    }


    @Async
    @Override
    public void executeAsyncTask(Integer i,String model,int year) {
        System.out.println("执行异步任务：" + i);
        switch (i){
            //打分审核表，评委原始打分表
            case 0: excelService.reviewExcel(model,year);break;
            //打分转化表，评委原始打分表增加相对分列
            case 1: excelService.reviewTransferExcel(model,year);break;
            //相对分统计表、相对分排名表，创新分、实用分
            case 2: excelService.relativeScoreExcel(model,year);break;
            //评分统计表，各个子项的平均分
            case 3: excelService.scoringSumUpExcel(model,year);break;
            //case 4: excelService.finalScoreExcel(model);break;
            default:break;
        }

    }

    @Async
    @Override
    public void executeGenerateAward(String model,int year) {
        System.out.println("执行异步生成分数任务：executeGenerateAward");
        //计算创新平均分
        List<InnovationScore> innovationScoreList1 = scoreService.calculateInnovationScore(model,year);
        //按照平均分排序
        ScoreUtil.sortInnovationScore(innovationScoreList1);
        worksService.saveAsAward(innovationScoreList1,"创新分",year);
        //计算实用平均分
        List<InnovationScore> innovationScoreList2 = scoreService.calculateUsefulScore(model,year);
        //按照平均分排序
        ScoreUtil.sortInnovationScore(innovationScoreList2);
        //更新排名表中作品记录
        worksService.saveAsAward(innovationScoreList2,"实用分",year);
        System.out.println("executeGenerateAward 执行完成，下面执行：excelService.finalScoreExcel 生成作品获奖表");
        //生成作品获奖表
        excelService.finalScoreExcel(model,year);
        System.out.println("excelService.finalScoreExcel 执行完成");
    }
}

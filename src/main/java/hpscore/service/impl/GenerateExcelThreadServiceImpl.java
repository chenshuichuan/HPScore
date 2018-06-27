package hpscore.service.impl;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/21
 * Time: 22:08
 */

import hpscore.controller.ScoreController;
import hpscore.domain.InnovationScore;
import hpscore.repository.PingweiRepository;
import hpscore.repository.ScoreRepository;
import hpscore.repository.UserRepository;
import hpscore.service.*;
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
    public void executeAsyncTask(Integer i,String model) {
        System.out.println("执行异步任务：" + i);
        switch (i){
            case 0: excelService.reviewExcel(model);break;
            case 1: excelService.reviewTransferExcel(model);break;
            case 2: excelService.relativeScoreExcel(model);break;
            case 3: excelService.finalScoreExcel(model);break;
            //case 4: excelService.scoringSumUpExcel(model);break;
            default:break;
        }

    }

    @Async
    @Override
    public void executeGenerateAward(String model) {
        System.out.println("执行异步生成分数任务：executeGenerateAward");
        List<InnovationScore> innovationScoreList1 = scoreService.calculateInnovationScore(model);
        //按照平均分排序
        ScoreUtil.sortInnovationScore(innovationScoreList1);
        worksService.saveAsAward(innovationScoreList1,"创新分");

        List<InnovationScore> innovationScoreList2 = scoreService.calculateUsefulScore(model);
        ScoreUtil.sortInnovationScore(innovationScoreList2);
        worksService.saveAsAward(innovationScoreList2,"实用分");
        System.out.println("executeGenerateAward 执行完成，下面执行：excelService.finalScoreExcel 生成作品获奖表");
        excelService.finalScoreExcel(model);
        System.out.println("excelService.finalScoreExcel 执行完成");
    }
}

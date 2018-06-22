package hpscore.service.impl;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/21
 * Time: 22:08
 */

import hpscore.controller.ScoreController;
import hpscore.repository.PingweiRepository;
import hpscore.repository.ScoreRepository;
import hpscore.repository.UserRepository;
import hpscore.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

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
        switch (i){
            case 0: excelService.reviewExcel(model);break;
            case 1: excelService.reviewTransferExcel(model);break;
            case 2: excelService.relativeScoreExcel(model);break;
            case 3: excelService.finalScoreExcel(model);break;
            case 4: excelService.scoringSumUpExcel(model);break;
            default:break;
        }
        System.out.println("执行异步任务：" + i);
    }
}

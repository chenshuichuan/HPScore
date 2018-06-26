package hpscore.service.impl;


import hpscore.domain.LogInfo;
import hpscore.domain.User;
import hpscore.repository.LogInfoRepository;
import hpscore.service.LogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.text.SimpleDateFormat;

@Service
public class LogInfoServiceImpl implements LogInfoService {

    @Autowired
    LogInfoRepository logInfoRepository;
    @Override
    public int add(LogInfo logInfo) {
        LogInfo logInfo1 = logInfoRepository.save(logInfo);
        if (null != logInfo1) return 1;
        return 0;
    }

    @Override
    public int update(LogInfo logInfo) {
        LogInfo logInfo1 = logInfoRepository.findOne(logInfo.getId());
       if (null!=logInfo1){
           logInfoRepository.save(logInfo);
           return 1;
       }
       return 0;
    }

    @Override
    public int delete(LogInfo logInfo) {
        LogInfo logInfo1 = logInfoRepository.findOne(logInfo.getId());
        if (null!=logInfo1){
            logInfoRepository.delete(logInfo);
            return 1;
        }
        return 0;
    }

    @Override
    public int addLoginInfo(String userName,String ip,long startTime, String action,String model) {
//        String ip = request.getRemoteAddr();
//        long startTime = System.currentTimeMillis();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time =df.format(date);
        LogInfo logInfo = new LogInfo(userName,ip,time,action,model);
        add(logInfo);
        return 0;
    }


}

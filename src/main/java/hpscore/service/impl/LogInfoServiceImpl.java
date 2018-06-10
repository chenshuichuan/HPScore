package hpscore.service.impl;


import hpscore.domain.LogInfo;
import hpscore.repository.LogInfoRepository;
import hpscore.service.LogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}

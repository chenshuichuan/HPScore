package hpscore.service;


import hpscore.domain.LogInfo;

/**
 * Created by tengj on 2017/4/7.
 */
public interface LogInfoService {
    int add(LogInfo logInfo);
    int update(LogInfo logInfo);
    int delete(LogInfo logInfo);
}

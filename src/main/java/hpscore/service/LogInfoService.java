package hpscore.service;


import hpscore.domain.LogInfo;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * Created by tengj on 2017/4/7.
 */
public interface LogInfoService {
    int add(LogInfo logInfo);
    int update(LogInfo logInfo);
    int delete(LogInfo logInfo);

    int addLoginInfo(String userName, String ip,long startTime, String action,String model);
}

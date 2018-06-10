package hpscore.repository;

import hpscore.domain.LogInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/06
 * Time: 13:36
 */
/*
* Integer 是id 的类型*/
public interface LogInfoRepository extends JpaRepository<LogInfo,Integer>{

}

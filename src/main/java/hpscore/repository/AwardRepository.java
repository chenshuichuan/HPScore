package hpscore.repository;

import hpscore.domain.Award;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/06
 * Time: 13:36
 */
/*
* Integer 是id 的类型*/
public interface AwardRepository extends JpaRepository<Award,Integer>{
    List<Award> findByModelAndScoreTypeAndYear(String model,String scoreType,int year);
    //不需要再加一个model吧？
    Award findByWorksIdAndModelAndScoreType(int worksId, String model,String scoreType);
}

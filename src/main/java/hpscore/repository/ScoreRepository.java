package hpscore.repository;

import hpscore.domain.Score;
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
public interface ScoreRepository extends JpaRepository<Score,Integer>{

    List<Score> findByEditor1AndModel(String editor1, String model);
    List<Score> findByEditor2AndModel(String editor2, String model);


    List<Score> findByModel(String model);
    Score findByPidAndProIdAndModel(String pid, String proId,String model);
}

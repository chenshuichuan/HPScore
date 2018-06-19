package hpscore.repository;

import hpscore.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    List<Score> findByPidAndEditor1AndModel(String pid,String editor1, String model);
    List<Score> findByPidAndEditor2AndModel(String pid,String editor2, String model);


    List<Score> findByModel(String model);
    //查询评委的所有评分记录
    List<Score> findByPidAndModel(String pid,String model);
    //查询某作品的所有评委评分记录
    List<Score> findByProIdAndModel(String proId,String model);
    Score findByPidAndProIdAndModel(String pid, String proId,String model);

    //查找记录录入次数少于2次的记录
    @Query("from Score s where s.editTimes<:edit_times")
    List<Score> findScoreLessThanEditTimes(@Param("edit_times")int edit_times);
}

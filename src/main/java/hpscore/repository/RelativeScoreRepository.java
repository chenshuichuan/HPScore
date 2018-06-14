package hpscore.repository;

import hpscore.domain.Pingwei;
import hpscore.domain.RelativeScore;
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
public interface RelativeScoreRepository extends JpaRepository<RelativeScore,Integer>{
    //得到某model的所有相对评分
    List<RelativeScore> findByModel(String model);
    //查找某model的某作品的相对评分
    RelativeScore findByProIdAndModel(String code, String model);

//    //查找记录录入次数少于2次的记录
//    @Query("from RelativeScore r where r.editTimes<:edit_times")
//    List<Score> findScoreLessThanEditTimes(@Param("edit_times")int edit_times);
}

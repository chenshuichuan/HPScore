package hpscore.repository;

import hpscore.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    List<Score> findByYear(int year);

    List<Score> findByEditor1AndModelAndYear(String editor1, String model,int year);
    List<Score> findByEditor2AndModelAndYear(String editor2, String model,int year);
    List<Score> findByPidAndEditor1AndModelAndYear(String pid,String editor1, String model,int year);
    List<Score> findByPidAndEditor2AndModelAndYear(String pid,String editor2, String model,int year);


    List<Score> findByModelAndYear(String model,int year);
    //查询评委的所有评分记录
    List<Score> findByPidAndModelAndYear(String pid,String model,int year);
    //查询某作品的所有评委评分记录
    List<Score> findByProIdAndModelAndYear(String proId,String model,int year);
    Score findByPidAndProIdAndModelAndYear(String pid, String proId,String model,int year);

    /*不需增加year条件，往年不会有少于两次的记录*/
    //查找记录录入次数少于2次的记录
    @Query("from Score s where s.editTimes<:edit_times")
    List<Score> findScoreLessThanEditTimes(@Param("edit_times")int edit_times);


    /**
     * @Author haien
     * @Description 删除某组的全部分数
     * @Date 14:37 2018/7/25
     * @Param [model]
     * @return void
     **/
    //transaction要加在modify后面！
    @Modifying
    @Transactional
    @Query("delete from Score where model = ?1 and year = ?2")
    void deleteByModelAndYear(String model,int year);
}

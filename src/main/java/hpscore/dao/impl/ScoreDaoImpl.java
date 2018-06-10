package hpscore.dao.impl;


import hpscore.dao.ScoreDao;
import hpscore.domain.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



/**
 * Created by tengj on 2017/4/8.
 */
@Repository
public class ScoreDaoImpl implements ScoreDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Override
//    public int add(Score score) {
//        return jdbcTemplate.update("insert into learn_resource(author, title,url) values(?, ?, ?)",
//                learnResouce.getAuthor(),learnResouce.getTitle(),learnResouce.getUrl());
//    }
//
//    @Override
//    public int update(LearnResouce learnResouce) {
//        return jdbcTemplate.update("update learn_resource set author=?,title=?,url=? where id = ?",
//                new Object[]{learnResouce.getAuthor(),learnResouce.getTitle(),
//                        learnResouce.getUrl(),learnResouce.getId()});
//    }
//@Override
//public Page queryLearnResouceList(Map<String,Object> params) {
//    StringBuffer sql =new StringBuffer();
//    sql.append("select * from learn_resource where 1=1");
//    if(!StringUtil.isNull((String)params.get("author"))){
//        sql.append(" and author like '%").append((String)params.get("author")).append("%'");
//    }
//    if(!StringUtil.isNull((String)params.get("title"))){
//        sql.append(" and title like '%").append((String)params.get("title")).append("%'");
//    }
//    Page page = new Page(sql.toString(), Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("rows").toString()), jdbcTemplate);
//    return page;
//}

}

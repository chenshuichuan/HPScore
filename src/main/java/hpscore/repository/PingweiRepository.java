package hpscore.repository;

import hpscore.domain.Pingwei;
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
public interface PingweiRepository extends JpaRepository<Pingwei,Integer>{
    List<Pingwei> findByYear(int year);
    List<Pingwei> findByModelAndYear(String model,int year);
    Pingwei findByNameAndModelAndYear(String name,String model,int year);
    Pingwei findByCodeAndModelAndYear(String code,String model,int year);
}

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
    List<Pingwei> findByModel(String model);
    Pingwei findByNameAndModel(String name,String model);
    Pingwei findByCodeAndModel(String code,String model);
}

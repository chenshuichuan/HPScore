package hpscore.repository;

import hpscore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/06
 * Time: 13:36
 */
/*
* Integer 是id 的类型*/
public interface UserRepository extends JpaRepository<User,Integer>{
    User findByNameAndPassword(String name,String password);
    User findByName(String name);
}

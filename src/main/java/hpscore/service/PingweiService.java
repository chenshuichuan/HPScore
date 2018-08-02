package hpscore.service;


import hpscore.domain.Pingwei;

import java.util.List;

/**
 * @Author haien
 * @Description 对评委的增删查改
 * @Date 22:01 2018/7/20
 * @Param
 * @return
 **/
public interface PingweiService {
    int add(Pingwei pingwei);
    int update(Pingwei pingwei);
    int delete(Pingwei pingwei);

    List<Pingwei> selectAll(int year);
    List<String> selectAllName(int year);
    List<String> selectAllCodeByModelAndYear(String model,int year);
}

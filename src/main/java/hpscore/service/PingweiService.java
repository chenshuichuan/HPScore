package hpscore.service;


import hpscore.domain.Pingwei;

import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */
public interface PingweiService {
    int add(Pingwei pingwei);
    int update(Pingwei pingwei);
    int delete(Pingwei pingwei);

    List<Pingwei> selectAll();
    List<String> selectAllName();
    List<String> selectAllCodeByModel(String model);
}

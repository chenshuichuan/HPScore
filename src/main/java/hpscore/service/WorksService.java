package hpscore.service;


import hpscore.domain.Works;

import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */
public interface WorksService {
    int add(Works works);
    int update(Works works);
    int delete(Works works);

    List<Works> selectAll();
    List<String> selectAllName();
    List<String> selectAllCodeByModel(String model);
}

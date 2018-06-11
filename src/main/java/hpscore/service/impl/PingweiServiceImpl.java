package hpscore.service.impl;


import hpscore.domain.Pingwei;
import hpscore.repository.PingweiRepository;
import hpscore.service.PingweiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */
@Service
public class PingweiServiceImpl implements PingweiService {

    @Autowired
    PingweiRepository pingweiRepository;
    @Override
    public int add(Pingwei works) {
        Pingwei works1 = pingweiRepository.save(works);
        if (null != works1) return 1;
        return 0;
    }

    @Override
    public int update(Pingwei works) {
        Pingwei works1 = pingweiRepository.findOne(works.getId());
       if (null!=works1){
           pingweiRepository.save(works);
           return 1;
       }
       return 0;
    }

    @Override
    public int delete(Pingwei works) {
        Pingwei works1 = pingweiRepository.findOne(works.getId());
        if (null!=works1){
            pingweiRepository.delete(works);
            return 1;
        }
        return 0;
    }

    @Override
    public List<Pingwei> selectAll() {
        return pingweiRepository.findAll();
    }

    @Override
    public List<String> selectAllName() {
        List<String> stringList = new ArrayList<>();
        List<Pingwei> pingweiList = pingweiRepository.findAll();
        for (Pingwei pingwei: pingweiList){
            stringList.add(pingwei.getName());
        }
        return stringList;
    }
    @Override
    public List<String> selectAllCodeByModel(String model) {
        List<String> stringList = new ArrayList<>();
        List<Pingwei> pingweiList = pingweiRepository.findByModel(model);
        for (Pingwei pingwei: pingweiList){
            stringList.add(pingwei.getCode());
        }
        //按照作品编号排序
        Collections.sort(stringList);
        return stringList;
    }
}

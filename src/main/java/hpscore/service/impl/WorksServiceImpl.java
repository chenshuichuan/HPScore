package hpscore.service.impl;


import hpscore.domain.PingweiScore;
import hpscore.domain.Works;
import hpscore.repository.WorksRepository;
import hpscore.service.WorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */
@Service
public class WorksServiceImpl implements WorksService {

    @Autowired
    WorksRepository worksRepository;
    @Override
    public int add(Works works) {
        Works works1 = worksRepository.save(works);
        if (null != works1) return 1;
        return 0;
    }

    @Override
    public int update(Works works) {
        Works works1 = worksRepository.findOne(works.getId());
       if (null!=works1){
           worksRepository.save(works);
           return 1;
       }
       return 0;
    }

    @Override
    public int delete(Works works) {
        Works works1 = worksRepository.findOne(works.getId());
        if (null!=works1){
            worksRepository.delete(works);
            return 1;
        }
        return 0;
    }

    @Override
    public List<Works> selectAll() {
        return worksRepository.findAll();
    }

    @Override
    public List<String> selectAllName() {
        List<String> stringList = new ArrayList<>();
        List<Works> worksList = worksRepository.findAll();
        //按照作品编号排序
        Collections.sort(worksList,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof Works && o2 instanceof Works){
                    Works e1 = (Works) o1;
                    Works e2 = (Works) o2;
                    return e1.getCode().compareTo(e2.getCode());
                }
                throw new ClassCastException("不能转换为PingweiScore类型");
            }
        });
        for (Works works: worksList){
            stringList.add(works.getName());
        }

        return stringList;
    }

    @Override
    public List<String> selectAllCodeByModel(String model) {
        List<String> stringList = new ArrayList<>();
        List<Works> worksList = worksRepository.findByModel(model);
        for (Works works: worksList){
            stringList.add(works.getCode());
        }
        //按照作品编号排序
        Collections.sort(stringList);
        return stringList;
    }
}

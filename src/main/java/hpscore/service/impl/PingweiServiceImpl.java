package hpscore.service.impl;


import hpscore.domain.Pingwei;
import hpscore.repository.PingweiRepository;
import hpscore.service.PingweiService;
import hpscore.tools.StringUtil;
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
public class PingweiServiceImpl implements PingweiService {

    @Autowired
    PingweiRepository pingweiRepository;
    @Override
    public int add(Pingwei works) {
        Pingwei works1 = pingweiRepository.save(works);
        if (null != works1) {return 1;}
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
    public List<Pingwei> selectAll(int year) {
        return pingweiRepository.findByYear(year);
    }

    @Override
    public List<String> selectAllName(int year) {
        List<String> stringList = new ArrayList<>();
        List<Pingwei> pingweiList = pingweiRepository.findByYear(year);
        for (Pingwei pingwei: pingweiList){
            stringList.add(pingwei.getName());
        }
        return stringList;
    }
    /**
     * @Author haien
     * @Description 根据组别查找所有评委序号，并按从小到大排序
     * @Date 8:06 2018/7/21
     * @Param [model]
     * @return java.util.List<java.lang.String>
     **/
    @Override
    public List<String> selectAllCodeByModelAndYear(String model,int year) {
        List<String> stringList = new ArrayList<>();
        List<Pingwei> pingweiList = pingweiRepository.findByModelAndYear(model,year);
        for (Pingwei pingwei: pingweiList){
            stringList.add(pingwei.getCode());
        }
        //按照序号排序
        Collections.sort(stringList,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof String && o2 instanceof String){
                    String e1 = (String) o1;
                    String e2 = (String) o2;
                    return StringUtil.comparePidOrProId(e1,e2);
                }
                throw new ClassCastException("不能转换为String类型");
            }
        });
        return stringList;
    }
}

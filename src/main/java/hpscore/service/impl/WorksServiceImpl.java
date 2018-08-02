package hpscore.service.impl;


import hpscore.controller.CountController;
import hpscore.domain.Award;
import hpscore.domain.InnovationScore;
import hpscore.domain.Works;
import hpscore.repository.AwardRepository;
import hpscore.repository.WorksRepository;
import hpscore.service.WorksService;
import hpscore.tools.ScoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger logger = LoggerFactory.getLogger(CountController.class);

    @Autowired
    WorksRepository worksRepository;
    @Autowired
    AwardRepository awardRepository;

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
    public List<Works> selectAll(int year) {
        return worksRepository.findByYear(year);
    }

    @Override
    public List<String> selectAllName(int year) {
        List<String> stringList = new ArrayList<>();
        List<Works> worksList = worksRepository.findByYear(year);
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
    public List<String> selectAllCodeByModelAndYear(String model,int year) {
        List<String> stringList = new ArrayList<>();
        List<Works> worksList = worksRepository.findByModelAndYear(model,year);
        for (Works works: worksList){
            stringList.add(works.getCode());
        }
        //按照作品编号排序
        Collections.sort(stringList);
        return stringList;
    }

    //将award数据转化为works数据
    @Override
    public List<Works> awardToWorks(List<Award> awardList) {
        List<Works> worksList = new ArrayList<>();
        for (Award award: awardList){
            Works works = worksRepository.findOne(award.getWorksId());
            if(works!=null&&works.getModel().equals(award.getModel())&&works.getYear()==(award.getYear())){
                Works works1 = new Works(works);
                works1.setRanking(award.getRanking());
                works1.setFinalScore(award.getScore());
                worksList.add(works1);
            }
            else logger.error("awardToWorks error!");
        }
        return worksList;
    }

    //获取相对分的works作品表，
    @Override
    public List<Works> selectFinalScoreRanking(String model,int year){
        List<Works> worksList = worksRepository.findByModelAndYear(model,year);

        if (worksList!=null&&worksList.size()>=2){
            //按照平均分排序
            ScoreUtil.sortWorks(worksList);
        }
        return worksList;
    }
    //获得综合奖列表 按照相对分平均分排序
    @Override
    public List<Works> getSumUpAward(String model,int year){
        return selectFinalScoreRanking(model,year);
    }

    //获得创新奖列表 按照平均分排序
    @Override
    public List<Works> getInnovationAward(String model,int year){
        List<Award> awardList = awardRepository.findByModelAndScoreTypeAndYear(model,"创新分",year);
        List<Works> worksList =  awardToWorks(awardList);
        //按照平均分排序
        ScoreUtil.sortWorks(worksList);
        return worksList;
    }
    //获得实用奖列表 按照平均分排序
    @Override
    public List<Works> getUsefulAward(String model,int year){
        List<Award> awardList = awardRepository.findByModelAndScoreTypeAndYear(model,"实用分",year);
        List<Works> worksList =  awardToWorks(awardList);
        //按照平均分排序
        ScoreUtil.sortWorks(worksList);
        return worksList;
    }

    //更新作品排名
    @Override
    public int saveAsAward(List<InnovationScore> innovationScoreList, String scoreType,int year) {
        for (InnovationScore innovationScore: innovationScoreList){
            Works works = worksRepository.findByCodeAndModelAndYear(innovationScore.getProId(),
                    innovationScore.getModel(),year);
            if (works!=null){
                Award award = awardRepository.findByWorksIdAndModelAndScoreType(works.getId()
                ,works.getModel(),scoreType);
                if (award==null){
                    award = new Award(works.getId(),innovationScore.getModel(),innovationScore.getAverage(),
                            scoreType,works.getYear());
                }
                else {
                    award.setScore(innovationScore.getAverage());
                }
                award.setRanking(innovationScore.getRanking());
                //更新作品记录
                awardRepository.save(award);
            }
            else logger.error("saveAsAward:作品code="+innovationScore.getProId()+"不存在！");
        }
        return 0;
    }
}

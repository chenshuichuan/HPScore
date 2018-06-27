package hpscore.service.impl;


import hpscore.controller.CountController;
import hpscore.domain.Award;
import hpscore.domain.InnovationScore;
import hpscore.domain.PingweiScore;
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

    //将award数据转化为works数据
    @Override
    public List<Works> awardToWorks(List<Award> awardList) {
        List<Works> worksList = new ArrayList<>();
        for (Award award: awardList){
            Works works = worksRepository.findOne(award.getWorksId());
            if(works!=null&&works.getModel().equals(award.getModel())){
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
    public List<Works> selectFinalScoreRanking(String model){
        List<Works> worksList = worksRepository.findByModel(model);

        if (worksList!=null&&worksList.size()>=2){
            //按照平均分排序
            ScoreUtil.sortWorks(worksList);
        }
        return worksList;
    }
    //获得综合奖列表 按照相对分平均分排序
    @Override
    public List<Works> getSumUpAward(String model){
        return selectFinalScoreRanking(model);
    }

    //获得创新奖列表 按照平均分排序
    @Override
    public List<Works> getInnovationAward(String model){
        List<Award> awardList = awardRepository.findByModelAndScoreType(model,"创新分");
        List<Works> worksList =  awardToWorks(awardList);
        //按照平均分排序
        ScoreUtil.sortWorks(worksList);
        return worksList;
    }
    //获得实用奖列表 按照平均分排序
    @Override
    public List<Works> getUsefulAward(String model){
        List<Award> awardList = awardRepository.findByModelAndScoreType(model,"实用分");
        List<Works> worksList =  awardToWorks(awardList);
        //按照平均分排序
        ScoreUtil.sortWorks(worksList);
        return worksList;
    }

    @Override
    public int saveAsAward(List<InnovationScore> innovationScoreList, String scoreType) {
        for (InnovationScore innovationScore: innovationScoreList){
            Works works = worksRepository.findByCodeAndModel(innovationScore.getProId(),
                    innovationScore.getModel());
            if (works!=null){
                Award award = awardRepository.findByWorksIdAndModelAndScoreType(works.getId()
                ,works.getModel(),scoreType);
                if (award==null){
                    award = new Award(works.getId(),innovationScore.getModel(),innovationScore.getAverage(),
                            scoreType);
                }
                else {
                    award.setScore(innovationScore.getAverage());
                }
                award.setRanking(innovationScore.getRanking());
                awardRepository.save(award);
            }
            else logger.error("saveAsAward:作品code="+innovationScore.getProId()+"不存在！");
        }
        return 0;
    }
}

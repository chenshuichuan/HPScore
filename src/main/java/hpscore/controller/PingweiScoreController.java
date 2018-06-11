package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import hpscore.domain.PingweiScore;
import hpscore.domain.Score;
import hpscore.repository.UserRepository;
import hpscore.service.PingweiScoreService;
import hpscore.service.PingweiService;
import hpscore.service.ScoreService;
import hpscore.service.WorksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@ClassName: IndexController
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/22 21:29
 **/
@RestController
@RequestMapping("/pingweiScore")
public class PingweiScoreController {

    private final static Logger logger = LoggerFactory.getLogger(PingweiScoreController.class);

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorksService worksService;
    @Autowired
    private PingweiService pingweiService;

    @Autowired
    private PingweiScoreService pingweiScoreService;
    //根据评委和model查询该评委所有的评分记录，
    @RequestMapping(value = "/selectByPidAndModel",method = RequestMethod.GET)
    public Map<String,Object> selectByPidAndModel(
            @RequestParam("pid")String pid,
            @RequestParam("model")String model){

        Map<String,Object> map =new HashMap<String,Object>();
        List<PingweiScore> pingweiScoreList = pingweiScoreService.selectByPidAndModel(pid,model);
        //为查找到数据是score为null
        if (pingweiScoreList.size()>0) {
            System.out.println("selectByPidAndModel size="+pingweiScoreList.size());
            map.put("result",1);
            map.put("pingweiScoreList",pingweiScoreList);
            map.put("message","找到对应数据");
        }
        else{
            map.put("result",0);
            map.put("pingweiScoreList",null);
            map.put("message","未找到对应数据");
        }

        return map;
    }
}

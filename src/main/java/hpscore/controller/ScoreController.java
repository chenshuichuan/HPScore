package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import com.alibaba.fastjson.JSONObject;
import hpscore.domain.Score;
import hpscore.domain.User;
import hpscore.repository.UserRepository;
import hpscore.service.PingweiService;
import hpscore.service.ScoreService;
import hpscore.service.WorksService;
import hpscore.tools.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/score")
public class ScoreController {

    private final static Logger logger = LoggerFactory.getLogger(ScoreController.class);

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorksService worksService;
    @Autowired
    private PingweiService pingweiService;


    //根据评委以及作品和model查询该作品评分记录是否已经存在
    @RequestMapping(value = "/selectByPidAndProIdAndModel",method = RequestMethod.GET)
    public Score selectByPidAndProIdAndModel(
            @RequestParam("pid")String pid,
            @RequestParam("proId")String proId,
            @RequestParam("model")String model){
        Score score = scoreService.selectByPidAndProIdAndModel(pid,proId,model);
        if (score!=null)System.out.println("selectByPidAndProIdAndModel"+score.getPid());
        return score;
    }

    //更新数据
    /**
     *@Author: Ricardo
     *@Description:
     *@Date: 23:43 2018/6/9
     *@param:
     **/
    @RequestMapping(value = "/add_get")
    @ResponseBody
    public Map<String,Object> add(
            @RequestParam("pid")String pid,
            @RequestParam("proId")String proId,
            @RequestParam("option1")String option1Str,
            @RequestParam("option2")String option2Str,
            @RequestParam("option3")String option3Str,
            @RequestParam("option4")String option4Str,
            @RequestParam("option5")String option5Str,
            @RequestParam("option6")String option6Str,
            @RequestParam("model")String model,
            @RequestParam("editor")String editor){

        Map<String,Object> map =new HashMap<String,Object>();

        int option1 = Integer.parseInt(option1Str);
        int option2 = Integer.parseInt(option2Str);
        int option3 = Integer.parseInt(option3Str);
        int option4 = Integer.parseInt(option4Str);
        int option5 = Integer.parseInt(option5Str);
        int option6 = Integer.parseInt(option6Str);

        Score score = scoreService.selectByPidAndProIdAndModel(pid,proId,model);
        if(score ==null){
            logger.info("---添加评分数据，正在写入数据库-----");

            int editTimes = 0;
            Score temp = new Score(pid,proId,
                    option1, option2, option3, option4, option5, option6,
                    editTimes+1,model);
            temp.setEditor1(editor);

            scoreService.add(temp);
            map.put("result",1);
            map.put("message","成功更改评分记录！");
        }else{
            map.put("result",0);
            map.put("message","添加评分记录失败！请检查数据是否已存在！");
        }
        return map;
    }
}

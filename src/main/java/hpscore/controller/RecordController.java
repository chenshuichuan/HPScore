package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import hpscore.domain.Score;
import hpscore.domain.User;
import hpscore.repository.UserRepository;
import hpscore.service.PingweiService;
import hpscore.service.ScoreService;
import hpscore.service.WorksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 *@ClassName: IndexController
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/22 21:29
 **/
@Controller
@RequestMapping
public class RecordController {

    private final static Logger logger = LoggerFactory.getLogger(RecordController.class);

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorksService worksService;
    @Autowired
    private PingweiService pingweiService;

    //本科组评分录入
    @RequestMapping(value = "/record1")
    public ModelAndView record1(@RequestParam("editor")String editor,
                                @RequestParam("model")String model){
        ModelAndView modelAndView = new ModelAndView("record1");
//        User user = userRepository.findByName(editor);
//        if(user!=null){
//            List<Score> scores = null;
//            //管理员可以查看所有记录
//            if(user.getRole()==0){
//                scores = scoreService.selectAll();
//                modelAndView.addObject("scoreList", scores);
//            }
//            //否则仅可以查看自己相关的记录
//            else{
//                scores = scoreService.selectByEditorAndModel(editor,"本科组");
//                modelAndView.addObject("scoreList", scores);
//            }
//        }
        modelAndView.addObject("worksList", worksService.selectAllCodeByModel(model));
        modelAndView.addObject("pingweiList", pingweiService.selectAllCodeByModel(model));
        return modelAndView;
    }

    //高职高专组评分录入
    @RequestMapping("/record2")
    public ModelAndView record2(@RequestParam("editor")String editor,
                                @RequestParam("model")String model){
        //List<String> models =indexService.getModels();
        ModelAndView modelAndView = new ModelAndView("record2");

//        User user = userRepository.findByName(editor);
//        if(user!=null){
//            List<Score> scores = null;
//            //管理员可以查看所有记录
//            if(user.getRole()==0){
//                scores = scoreService.selectAll();
//                modelAndView.addObject("scoreList", scores);
//            }
//            //否则仅可以查看自己相关的记录
//            else{
//                scores = scoreService.selectByEditorAndModel(editor,"本科组");
//                modelAndView.addObject("scoreList", scores);
//            }
//        }
        modelAndView.addObject("worksList", worksService.selectAllCodeByModel(model));
        modelAndView.addObject("pingweiList", pingweiService.selectAllCodeByModel(model));
        return modelAndView;
    }

    //评委打分导出
    @RequestMapping(value = "/record3")
    public ModelAndView record3(@RequestParam("model")String model){

        ModelAndView modelAndView = new ModelAndView("record3");
        modelAndView.addObject("pingweiList", pingweiService.selectAllCodeByModel(model));
        return modelAndView;
    }

    //评委打分导出
    @RequestMapping(value = "/innovation")
    public ModelAndView innovation(@RequestParam("model")String model){

        ModelAndView modelAndView = new ModelAndView("innovation");

        modelAndView.addObject(
                "innovationScoreList",scoreService.calculateInnovationScore(model));
        modelAndView.addObject(
                "usefulScoreList",scoreService.calculateUsefulScore(model));
        return modelAndView;
    }

    //总分平均分排名
    @RequestMapping(value = "/total_final")
    public ModelAndView total_final(@RequestParam("model")String model){

        ModelAndView modelAndView = new ModelAndView("total_final");

        modelAndView.addObject(
                "worksList",scoreService.selectFinalScoreRanking(model));
        return modelAndView;
    }
}

package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import hpscore.domain.Score;
import hpscore.domain.User;
import hpscore.domain.Works;
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

    //总分平均分排名
    @RequestMapping(value = "/total_final")
    public ModelAndView total_final(@RequestParam("model")String model){

        ModelAndView modelAndView = new ModelAndView("total_final");

        List<Works> worksList = scoreService.getSumUpAward(model);
        modelAndView.addObject(
                "finalList",worksList);
        List<Works> innovationList = scoreService.getInnovationAward(model);
        modelAndView.addObject(
                "innovationList",innovationList);
        List<Works>usefulList = scoreService.getUsefulAward(model);
        modelAndView.addObject(
                "usefulList",usefulList);
        return modelAndView;
    }
}

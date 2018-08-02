package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import hpscore.domain.LogInfo;
import hpscore.domain.Pingwei;
import hpscore.domain.User;
import hpscore.domain.Works;
import hpscore.repository.LogInfoRepository;
import hpscore.repository.PingweiRepository;
import hpscore.repository.UserRepository;
import hpscore.repository.WorksRepository;
import hpscore.service.GenerateExcelThreadService;
import hpscore.service.PingweiService;
import hpscore.service.ScoreService;
import hpscore.service.WorksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private LogInfoRepository logInfoRepository;

    @Autowired
    private WorksRepository worksRepository;
    @Autowired
    private PingweiRepository pingweiRepository;

    @Autowired
    private GenerateExcelThreadService generateExcelThreadService;

    //本科组评分录入
    @RequestMapping(value = "/record1")
<<<<<<< HEAD
    public ModelAndView record1(@RequestParam("model")String model){
=======
    public ModelAndView record1(@RequestParam("editor")String editor,
                                @RequestParam("model")String model,
                                @RequestParam("year")String year1){

        int year=Integer.parseInt(year1);
>>>>>>> add_year
        ModelAndView modelAndView = new ModelAndView("record1");

        modelAndView.addObject("worksList", worksService.selectAllCodeByModelAndYear(model,year));
        modelAndView.addObject("pingweiList", pingweiService.selectAllCodeByModelAndYear(model,year));
        return modelAndView;
    }

    //评分审核页面
    @RequestMapping(value = "/record3")
    public ModelAndView record3(@RequestParam("model")String model,@RequestParam("year")String year1){

        int year=Integer.parseInt(year1);
        ModelAndView modelAndView = new ModelAndView("record3");
        modelAndView.addObject("pingweiList", pingweiService.selectAllCodeByModelAndYear(model,year));
        return modelAndView;
    }

    //作品获奖页面
    @RequestMapping(value = "/total_final")
    public ModelAndView total_final(){

        //generateExcelThreadService.executeGenerateAward();
        ModelAndView modelAndView = new ModelAndView("total_final");
        return modelAndView;
    }


    //日志管理
    @RequestMapping(value = "/log_infor.html")
    public ModelAndView log_infor(HttpServletRequest request, HttpServletResponse response){
        String model = (String)request.getSession().getAttribute("model");
        String year1 = (String)request.getSession().getAttribute("year");
        int year = Integer.parseInt(year1);
        ModelAndView modelAndView = new ModelAndView("log_infor");

        List<LogInfo> logInfoList = logInfoRepository.findByModel(model);
        modelAndView.addObject(
                "logInfoList",logInfoList);

        return modelAndView;
    }

    //账号管理
    @RequestMapping(value = "/user.html")
    public ModelAndView user(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("user");
        List<User>userList = userRepository.findAll();
        modelAndView.addObject(
                "userList",userList);
        return modelAndView;
    }

    //评委管理
    @RequestMapping(value = "/pingwei.html")
    public ModelAndView pingwei(HttpServletRequest request, HttpServletResponse response){
        String year1 = (String)request.getSession().getAttribute("year");
        int year = Integer.parseInt(year1);
        ModelAndView modelAndView = new ModelAndView("pingwei");
        List<Pingwei>pingweiList = pingweiRepository.findByYear(year);
        modelAndView.addObject(
                "pingweiList",pingweiList);
        return modelAndView;
    }

    //作品管理
    @RequestMapping(value = "/works.html")
    public ModelAndView works(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("works");
        List<Works>worksList = worksRepository.findAll();
        modelAndView.addObject(
                "worksList",worksList);
        return modelAndView;
    }
}

package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import hpscore.tools.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class IndexController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

//    @Autowired
//    private IndexService indexService;

    @RequestMapping("/index.html")
    public ModelAndView index(){
        //List<String> models =indexService.getModels();

        ModelAndView modelAndView = new ModelAndView("index");
        List<String> fileList = FileUtil.readfiles(".");
        System.out.println("controller -- index --- index.html = "+fileList.size());
        modelAndView.addObject("fileList", fileList);
        return modelAndView;
    }

    @RequestMapping("/test.html")
    public ModelAndView test(@RequestParam("name")String modelName){

        System.out.println("modelName="+modelName);
        List<String> learnList =new ArrayList<>();
        learnList.add("hello1");
        learnList.add("hello2");
        ModelAndView modelAndView = new ModelAndView("test");
        modelAndView.addObject("learnList", learnList);

        modelAndView.addObject("imgsrc", "./data/");
        return modelAndView;
    }
}

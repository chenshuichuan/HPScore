package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import hpscore.domain.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static hpscore.tools.FileUtil.readFileInfo;

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

        ModelAndView modelAndView = new ModelAndView("index");
        List<FileInfo> fileInfoList = null;
        try {
            fileInfoList = readFileInfo(".");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("controller -- index --- index.html = "+fileInfoList.size());
        modelAndView.addObject("fileInfoList", fileInfoList);
        return modelAndView;
    }

    /**
     * @Author haien
     * @Description 某个小功能，免说明
     * @Date 17:53 2018/7/21
     * @Param [modelName]
     * @return org.springframework.web.servlet.ModelAndView
     **/
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

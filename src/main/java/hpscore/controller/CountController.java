package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import hpscore.service.PingweiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *@ClassName: IndexController
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/22 21:29
 **/
@Controller
@RequestMapping
public class CountController {

    private final static Logger logger = LoggerFactory.getLogger(CountController.class);

    @Autowired
    private PingweiService pingweiService;

    @RequestMapping("/count")
    public ModelAndView count(@RequestParam("model")String model){
        //List<String> models =indexService.getModels();

        ModelAndView modelAndView = new ModelAndView("count");
        modelAndView.addObject("pingweiList", pingweiService.selectAllCodeByModel(model));

        return modelAndView;
    }
}

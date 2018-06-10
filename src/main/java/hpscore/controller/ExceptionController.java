package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *@ClassName: IndexController
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/22 21:29
 **/
@Controller
@RequestMapping
public class ExceptionController {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionController.class);

//    @Autowired
//    private IndexService indexService;

    @RequestMapping("/exception.html")
    public ModelAndView index(){
        //List<String> models =indexService.getModels();

        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("model", "本科组");
        System.out.println("controller -- index --- index.html");
        return modelAndView;
    }
}

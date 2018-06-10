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
import hpscore.tools.ScoreUtil;
import hpscore.tools.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName: IndexController
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/22 21:29
 **/
@Controller
@RequestMapping("/score")
public class ScoreController2 {

    private final static Logger logger = LoggerFactory.getLogger(ScoreController2.class);

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorksService worksService;
    @Autowired
    private PingweiService pingweiService;

    //更新数据
    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,Object> update(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        String pid = request.getParameter("pid");
        String proId = request.getParameter("proId");
        String option1Str = request.getParameter("option1");
        String option2Str = request.getParameter("option2");
        String option3Str = request.getParameter("option3");
        String option4Str = request.getParameter("option4");
        String option5Str = request.getParameter("option5");
        String option6Str = request.getParameter("option6");
        String model = request.getParameter("model");
        int option1 = Integer.parseInt(option1Str);
        int option2 = Integer.parseInt(option2Str);
        int option3 = Integer.parseInt(option3Str);
        int option4 = Integer.parseInt(option4Str);
        int option5 = Integer.parseInt(option5Str);
        int option6 = Integer.parseInt(option6Str);

        String editor = request.getParameter("editor");//编辑者

        Score score = scoreService.selectByPidAndProIdAndModel(pid,proId,model);
        if(score!=null){
            logger.info("---更新评分数据，正在写入数据库-----"+score.getEditor1());

            int editTimes = score.getEditTimes();
            Score temp = new Score(pid,proId,
                    option1, option2, option3, option4, option5, option6,
                    editTimes+1,model);
            temp.setId(score.getId());
            temp.setEditor1(score.getEditor1());
            temp.setEditor2(score.getEditor2());

            //本次editor是第一位editor
            if (editor.equals(score.getEditor1()))
                temp.setEditor1(editor);
            //本次editor是第二位editor
                //本次editor是第二位editor
            else temp.setEditor2(editor);

//            else if (score.getEditor2()==null||
//                    editor == score.getEditor2())
//                temp.setEditor2(editor);
//
//            //既不是第一位editor也不是第二位editor，即超过两次的都作为第二位editor
//            else if (editTimes>=2)
//                temp.setEditor2(editor);
//            else logger.info("editor 写入失败！editor="+score.getEditor1());

            scoreService.update(temp);
            map.put("result",1);
            map.put("message","成功更改评分记录！");
        }else{
            map.put("result",0);
            map.put("message","更改评分记录失败！请检查数据是否已存在！");
        }
        return map;
    }

    //更新数据
    /**
     *@Author: Ricardo
     *@Description: 
     *@Date: 23:43 2018/6/9
     *@param: 
     **/
    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String,Object> add(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        String pid = request.getParameter("pid");
        String proId = request.getParameter("proId");
        String option1Str = request.getParameter("option1");
        String option2Str = request.getParameter("option2");
        String option3Str = request.getParameter("option3");
        String option4Str = request.getParameter("option4");
        String option5Str = request.getParameter("option5");
        String option6Str = request.getParameter("option6");
        String model = request.getParameter("model");
        int option1 = Integer.parseInt(option1Str);
        int option2 = Integer.parseInt(option2Str);
        int option3 = Integer.parseInt(option3Str);
        int option4 = Integer.parseInt(option4Str);
        int option5 = Integer.parseInt(option5Str);
        int option6 = Integer.parseInt(option6Str);

        String editor = request.getParameter("editor");//编辑者

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

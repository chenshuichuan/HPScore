package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import hpscore.domain.Score;
import hpscore.domain.User;
import hpscore.repository.UserRepository;
import hpscore.service.LogInfoService;
import hpscore.service.PingweiService;
import hpscore.service.ScoreService;
import hpscore.service.WorksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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
    @Autowired
    private LogInfoService logInfoService;
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
        String year1 = request.getParameter("year");
        int option1 = Integer.parseInt(option1Str);
        int option2 = Integer.parseInt(option2Str);
        int option3 = Integer.parseInt(option3Str);
        int option4 = Integer.parseInt(option4Str);
        int option5 = Integer.parseInt(option5Str);
        int option6 = Integer.parseInt(option6Str);
        int year=Integer.parseInt(year1);

        String editor = request.getParameter("editor");//编辑者

        //日志
        User user1 = (User)request.getSession().getAttribute("user");
        String userName= user1.getName();
        String ip = (String)request.getSession().getAttribute("ip");
        long startTime = System.currentTimeMillis();
        String action =this.getClass().getName()+ ".update-参数：pid="+pid+",proId="+proId
                +",p1="+option1Str+",p2="+option2Str+",p3="+option3Str+",p4="+option4Str+
                ",p5="+option5Str+",p6="+option6Str+
                ",model ="+model;
        //获取当前评委对该作品分数
        Score score = scoreService.selectByPidAndProIdAndModelAndYear(pid,proId,model,year);
        if(score!=null){
            logger.info("---更新评分数据，正在写入数据库-----"+score.getEditor1());

            int editTimes = score.getEditTimes();
            Score temp = new Score(pid,proId,
                    option1, option2, option3, option4, option5, option6,
                    editTimes+1,model,year);
            temp.setId(score.getId());
            temp.setEditor1(score.getEditor1());
            temp.setEditor2(score.getEditor2());

            //本次editor是第一位editor
            if (editor.equals(score.getEditor1()))
                temp.setEditor1(editor);
            //本次editor是第二位editor
            else temp.setEditor2(editor);

            scoreService.update(temp);

            action+=",成功更改评分记录！";
            map.put("result",1);
            map.put("message","成功更改评分记录！");
        }else{
            action+=",更改评分记录失败！";
            map.put("result",0);
            map.put("message","更改评分记录失败！请检查数据是否已存在！");
        }
        logInfoService.addLoginInfo(userName,ip,startTime,action,model);
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
        String year1 = request.getParameter("year");

        int option1 = Integer.parseInt(option1Str);
        int option2 = Integer.parseInt(option2Str);
        int option3 = Integer.parseInt(option3Str);
        int option4 = Integer.parseInt(option4Str);
        int option5 = Integer.parseInt(option5Str);
        int option6 = Integer.parseInt(option6Str);
        int year = Integer.parseInt(year1);

        String editor = request.getParameter("editor");//编辑者

        //日志
        User user1 = (User)request.getSession().getAttribute("user");
        String userName= user1.getName();
        String ip = (String)request.getSession().getAttribute("ip");
        long startTime = System.currentTimeMillis();
        String action =this.getClass().getName()+ ".add-参数：pid="+pid+",proId="+proId
                +",p1="+option1Str+",p2="+option2Str+",p3="+option3Str+",p4="+option4Str+
                ",p5="+option5Str+",p6="+option6Str+
                ",model ="+model;

        Score score = scoreService.selectByPidAndProIdAndModelAndYear(pid,proId,model,year);
        if(score ==null){
            logger.info("---添加评分数据，正在写入数据库-----");

            int editTimes = 0;
            Score temp = new Score(pid,proId,
                    option1, option2, option3, option4, option5, option6,
                    editTimes+1,model,year);
            temp.setEditor1(editor);
            scoreService.add(temp);

            action+=",成功添加评分记录！";
            map.put("result",1);
            map.put("message","成功添加评分记录！");
        }else{
            action+=",添加评分记录失败！";
            map.put("result",0);
            map.put("message","添加评分记录失败！请检查数据是否已存在！");
        }
        logInfoService.addLoginInfo(userName,ip,startTime,action,model);
        return map;
    }

    /**
     * @Author haien
     * @Description 前端请求下载Excel表格，返回一个输出流
     * @Date 11:34 2018/7/22
     * @Param [request, response]
     * @return void
     **/
    @RequestMapping(value = "/getExcel1")
    @ResponseBody
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //获得请求文件名
        String encoding = System.getProperty("file.encoding");
        String filename = request.getParameter("fileName");
        String enFileName = URLEncoder.encode(filename,"utf-8");
        System.out.println("/getExcel1="+filename);

        //设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename="+enFileName);
        //读取目标文件，通过response将目标文件写到客户端
        //读取文件
        String fileName = new String(filename.getBytes("UTF-8"),encoding);
        InputStream in = new FileInputStream(fileName);
        OutputStream out = response.getOutputStream();
        //写文件
        int b;
        while((b=in.read())!= -1) {
            out.write(b);
        }
        in.close();
        out.close();
    }
}

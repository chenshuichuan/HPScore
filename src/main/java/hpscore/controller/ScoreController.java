package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import com.alibaba.fastjson.JSONObject;
import hpscore.domain.*;
import hpscore.repository.PingweiRepository;
import hpscore.repository.ScoreRepository;
import hpscore.repository.UserRepository;
import hpscore.service.*;
import hpscore.service.impl.GenerateExcelThreadServiceImpl;
import hpscore.tools.ServletUtil;
import hpscore.tools.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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

    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private PingweiRepository pingweiRepository;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private GenerateExcelThreadService generateExcelThreadService;

    @Autowired
    private LogInfoService logInfoService;

    //根据评委以及作品和model查询该作品评分记录是否已经存在，并返回
    @RequestMapping(value = "/selectByPidAndProIdAndModel",method = RequestMethod.GET)
    public Map<String,Object> selectByPidAndProIdAndModel(
            @RequestParam("pid")String pid,
            @RequestParam("proId")String proId,
            @RequestParam("model")String model){

        Map<String,Object> map =new HashMap<String,Object>();
        Score score = scoreService.selectByPidAndProIdAndModel(pid,proId,model);
        //为查找到数据是score为null
        if (score!=null) {
            System.out.println("selectByPidAndProIdAndModel"+score.getPid());
            logger.info("selectByPidAndProIdAndModel"+score.getPid());
            map.put("result",1);
            map.put("score",score);
            map.put("message","找到对应数据");
        }
        else{
            map.put("result",0);
            map.put("score",null);
            map.put("message","未找到对应数据");
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
            map.put("message","成功添加评分记录！");
        }else{
            map.put("result",0);
            map.put("message","添加评分记录失败！请检查数据是否已存在！");
        }
        return map;
    }


    //计算相对分请求
    /**
     *@Author: Ricardo
     *@Description:
     *@Date: 23:43 2018/6/9
     *@param:
     **/
    @RequestMapping(value = "/countScore")
    @ResponseBody
    public Map<String,Object> countScore(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("model")String model,
            @RequestParam("editor")String editor){

        Map<String,Object> map =new HashMap<String,Object>();
        User user = userRepository.findByName(editor);



        //日志
        User user1 = (User)request.getSession().getAttribute("user");
        String userName= user1.getName();
        String ip = (String)request.getSession().getAttribute("ip");

        long startTime = System.currentTimeMillis();
        String action =this.getClass().getName()+ ".countScore-参数：model="+model+",editor="+editor;
        //查询该编辑是否有权限进行计算操作
        //有权限进行计算
        if(user!=null&&user.getRole()==0){
            //检查所有评分记录是否都录入了两次以上
            List<Score> temps = scoreRepository.findScoreLessThanEditTimes(2);
            if (temps.size()>0){
                action+=",存在评分记录录入次数未达到两次！";
                map.put("result",0);
                map.put("message","存在评分记录录入次数未达到两次！");
            }
            else{
                //检查是否每个评委都对所有作品进行了相同数量的评分记录，如：每个评委都有24条评分记录
                List<String> pingweiList = pingweiService.selectAllCodeByModel(model);

                int index = scoreService.checkIfAllTheSameTimes(model,pingweiList);
                //index,中途退出，存在对评分记录录入不完整
                if(index !=pingweiList.size()){
                    action+=",位的评委评分记录不统一！";
                    map.put("result",0);
                    map.put("message","第"+pingweiList.get(index)+"位的评委评分记录不统一！");
                }
                //满足计算条件，开始计算
                else{
                   index = scoreService.calculateRelativeScore(model,pingweiList);
                   if(index!=pingweiList.size()){
                       action+=",评委评分记录不统一！";
                       map.put("result",0);
                       map.put("message","第"+pingweiList.get(index)+"位的委评相对分计算出错！");
                   }
                   else{
                       for (int i=0;i<5;i++)
                           generateExcelThreadService.executeAsyncTask(i,model);

                       action+=",相对分计算成功！";
                       map.put("result",1);
                       map.put("message","相对分计算成功！");
                    }
                }
            }
        }
        else{
            map.put("result",0);
            map.put("message","您没有计算相对分的权限！");
        }
        logInfoService.addLoginInfo(userName,ip,startTime,action,model);
        return map;
    }


    //根据model查询作品的所有相对评分记录，并返回
    @RequestMapping(value = "/selectRelativeScoreByModel",method = RequestMethod.GET)
    public Map<String,Object> selectRelativeScoreByModel(
            @RequestParam("model")String model){

        Map<String,Object> map =new HashMap<String,Object>();
        List<RelativeScore> relativeScores = null;
                //relativeScoreRepository.findByModel(model);
        //为查找到数据是score为null
        if (relativeScores!=null) {

            //按照作品编号排序
            Collections.sort(relativeScores,new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    if(o1 instanceof RelativeScore && o2 instanceof RelativeScore){
                        RelativeScore e1 = (RelativeScore) o1;
                        RelativeScore e2 = (RelativeScore) o2;
                        return e1.getProId().compareTo(e2.getProId());
                    }
                    throw new ClassCastException("不能转换为RelativeScore类型");
                }
            });

            map.put("result",1);
            map.put("relativeScores",relativeScores);
            map.put("message","找到相对评分数据");
        }
        else{
            map.put("result",0);
            map.put("score",null);
            map.put("message","未找到相对评分数据");
        }

        return map;
    }


    //计算相对分平均分
    @RequestMapping(value = "/calculteRelativeScoreAverageAndMaxAndMin",method = RequestMethod.GET)
    public Map<String,Object> calculteRelativeScoreAverageAndMaxAndMin(
            @RequestParam("model")String model){

        Map<String,Object> map =new HashMap<String,Object>();

        List<RelativeScore> relativeScoreList = scoreService.calculteRelativeScoreAverageAndMaxAndMin(model);
        //计算成功
        if (relativeScoreList!=null) {
            map.put("result",1);
            map.put("scoreList",relativeScoreList);
            map.put("message","相对分平均分计算成功！");
        }
        else{
            map.put("result",0);
            map.put("scoreList",null);
            map.put("message","平均分计算失败！");
        }
        return map;
    }

    //返回创新性分数排名
    @RequestMapping(value = "/selectInnovationScoreByModel",method = RequestMethod.GET)
    public Map<String,Object> selectInnovationScore(
            @RequestParam("model")String model){

        Map<String,Object> map =new HashMap<String,Object>();
        List<InnovationScore> innovationScoreList = scoreService.calculateInnovationScore(model);
        //按照平均分排序
        Collections.sort(innovationScoreList,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof InnovationScore && o2 instanceof InnovationScore){
                    InnovationScore e1 = (InnovationScore) o1;
                    InnovationScore e2 = (InnovationScore) o2;
                    return StringUtil.compareTwoDouble(e1.getAverage(),e2.getAverage());
                }
                throw new ClassCastException("不能转换为InnovationScore类型");
            }
        });
        //计算成功
        map.put("result",1);
        map.put("scoreList",innovationScoreList);
        map.put("message","获取创新性分数成功！");
        return map;
    }
    //返回实用性分数排名
    @RequestMapping(value = "/selectUsefulScoreByModel",method = RequestMethod.GET)
    public Map<String,Object> selectUsefulScoreByModel(
            @RequestParam("model")String model){

        Map<String,Object> map =new HashMap<String,Object>();
        List<InnovationScore> innovationScoreList = scoreService.calculateUsefulScore(model);
        //按照平均分排序
        Collections.sort(innovationScoreList,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof InnovationScore && o2 instanceof InnovationScore){
                    InnovationScore e1 = (InnovationScore) o1;
                    InnovationScore e2 = (InnovationScore) o2;
                    return StringUtil.compareTwoDouble(e1.getAverage(),e2.getAverage());
                }
                throw new ClassCastException("不能转换为InnovationScore类型");
            }
        });
        //计算成功
        map.put("result",1);
        map.put("scoreList",innovationScoreList);
        map.put("message","获取实用性分数成功！");
        return map;
    }


    //根据model和要生成的Excel表格（打分审核表/相对分统计表/得分汇总表）,生成Excel文件，并返回Excel文件地址
    @RequestMapping(value = "/generateExcelByFileAndModel",method = RequestMethod.GET)
    public Map<String,Object> generateExcelByFileAndModel(
            @RequestParam("file")String file,@RequestParam("model")String model){

        Map<String,Object> map =new HashMap<String,Object>();
        String fileName = "";
        if(file.equals("打分审核表")){
            fileName = excelService.reviewExcel(model);
        }
        if(file.equals("打分转换表")){
            fileName = excelService.reviewTransferExcel(model);
        }
        if(file.equals("打分统计表")){
            fileName = excelService.scoringSumUpExcel(model);
        }
        if(file.equals("平均分统计表")){
            fileName = excelService.relativeScoreExcel(model);
        }
        if(file.equals("作品获奖表")){
            fileName = excelService.finalScoreExcel(model);
        }
        if(fileName!=null){
            map.put("result",1);
            map.put("fileName",fileName);
            map.put("message","获取表格成功！");
        }
        else{
            map.put("result",0);
            map.put("message","获取表格失败！数据存在问题，请检查！");
        }
        return map;
    }
}
